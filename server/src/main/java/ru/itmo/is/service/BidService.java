package ru.itmo.is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.dto.request.bid.BidRequest;
import ru.itmo.is.dto.request.bid.DepartureRequest;
import ru.itmo.is.dto.request.bid.OccupationRequest;
import ru.itmo.is.dto.request.bid.RoomChangeRequest;
import ru.itmo.is.dto.response.bid.BidResponse;
import ru.itmo.is.dto.response.bid.DepartureBidResponse;
import ru.itmo.is.dto.response.bid.OccupationBidResponse;
import ru.itmo.is.dto.response.bid.RoomChangeResponse;
import ru.itmo.is.entity.Event;
import ru.itmo.is.entity.bid.Bid;
import ru.itmo.is.entity.bid.DepartureBid;
import ru.itmo.is.entity.bid.OccupationBid;
import ru.itmo.is.entity.bid.RoomChangeBid;
import ru.itmo.is.entity.dorm.Dormitory;
import ru.itmo.is.entity.dorm.Room;
import ru.itmo.is.entity.dorm.University;
import ru.itmo.is.entity.user.Resident;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.exception.BadRequestException;
import ru.itmo.is.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidService {
    private final UserService userService;
    private final UniversityRepository universityRepository;
    private final RoomRepository roomRepository;
    private final BidRepository bidRepository;
    private final BidFileRepository bidFileRepository;
    private final EventRepository eventRepository;
    private final ResidentRepository residentRepository;

    public List<BidResponse> getNewBids() {
        List<Bid> newBids = bidRepository.getByStatus(Bid.Status.IN_PROCESS);
        return newBids.stream().map(this::map).toList();
    }

    public void createOccupationBid(OccupationRequest req) {
        Optional<University> universityO = universityRepository.findById(req.getUniversityId());
        if (universityO.isEmpty()) {
            throw new BadRequestException("No such university");
        }
        University university = universityO.get();
        Optional<Dormitory> dormitoryO = university.getDormitories().stream()
                .filter(dorm -> dorm.getId().equals(req.getDormitoryId())).findFirst();
        if (dormitoryO.isEmpty()) {
            throw new BadRequestException("No such dormitory or this dormitory is not linked with the university");
        }

        var bid = new OccupationBid();
        bid.setFiles(bidFileRepository.getByKeyIn(req.getAttachments()));
        bid.setSender(currentUser());
        bid.setText(req.getText());
        bid.setUniversity(university);
        bid.setDormitory(dormitoryO.get());
        bidRepository.save(bid);
    }

    public void createEvictionBid(BidRequest req) {
        var bid = new Bid();
        bid.setFiles(bidFileRepository.getByKeyIn(req.getAttachments()));
        bid.setSender(currentUser());
        bid.setText(req.getText());
        bid.setType(Bid.Type.EVICTION);
        bidRepository.save(bid);
    }

    public void createDepartureBid(DepartureRequest req) {
        var bid = new DepartureBid();
        bid.setFiles(bidFileRepository.getByKeyIn(req.getAttachments()));
        bid.setSender(currentUser());
        bid.setText(req.getText());
        bid.setDayFrom(req.getDayFrom());
        bid.setDayTo(req.getDayTo());
        bidRepository.save(bid);
    }

    public void createRoomChangeBid(RoomChangeRequest req) {
        Optional<Room> roomO = Optional.empty();
        if (req.getRoomToId() != null) {
            roomO = roomRepository.findById(req.getRoomToId());
            if (roomO.isEmpty()) {
                throw new BadRequestException("No such room");
            }
        }

        var bid = new RoomChangeBid();
        bid.setFiles(bidFileRepository.getByKeyIn(req.getAttachments()));
        bid.setSender(currentUser());
        bid.setText(req.getText());
        bid.setRoomTo(roomO.orElse(null));
        bid.setRoomPreferType(req.getRoomPreferType());
        bidRepository.save(bid);
    }

    public void denyBid(Long id) {
        Optional<Bid> bidO = bidRepository.findById(id);
        if (bidO.isEmpty()) {
            throw new BadRequestException("No such bid");
        }
        Bid bid = bidO.get();
        bid.setStatus(Bid.Status.DENIED);
        bid.setManager(currentUser());
        bidRepository.save(bid);
    }

    public void acceptBid(Long id) {
        Optional<Bid> bidO = bidRepository.findById(id);
        if (bidO.isEmpty()) {
            throw new BadRequestException("No such bid");
        }
        Bid bid = bidO.get();
        if (bid.getStatus() != Bid.Status.IN_PROCESS) {
            throw new BadRequestException("Bid was already processed");
        }

        bid.setStatus(Bid.Status.ACCEPTED);
        bid.setManager(currentUser());
        switch (bid.getType()) {
            case OCCUPATION -> acceptOccupationBid((OccupationBid) bid);
            case EVICTION -> acceptEvictionBid(bid);
            case DEPARTURE -> acceptDepartureBid((DepartureBid) bid);
            case ROOM_CHANGE -> acceptRoomChangeBid((RoomChangeBid) bid);
        }
        bidRepository.save(bid);
    }

    private void acceptOccupationBid(OccupationBid bid) {
        List<Integer> blockRoomIds = roomRepository.getIdsByType(Room.Type.BLOCK);
        Optional<Integer> roomIdO = blockRoomIds.stream().filter(roomRepository::isRoomFilled).findFirst();
        if (roomIdO.isEmpty()) {
            List<Integer> aisleRoomIds = roomRepository.getIdsByType(Room.Type.AISLE);
            roomIdO = aisleRoomIds.stream().filter(roomRepository::isRoomFilled).findFirst();
        }
        if (roomIdO.isEmpty()) {
            throw new BadRequestException("No free room");
        }

        Room room = roomRepository.findById(roomIdO.get()).get();
        Resident resident = Resident.of(bid.getSender());
        resident.setUniversity(bid.getUniversity());
        resident.setRoom(room);
        residentRepository.save(resident);

        var event = new Event();
        event.setType(Event.Type.OCCUPATION);
        event.setRoom(room);
        event.setResident(resident);
        eventRepository.save(event);
    }

    private void acceptEvictionBid(Bid bid) {
        Resident resident = residentRepository.findById(bid.getSender().getLogin()).get();

        resident.setEvicted(true);
        resident.setRoom(null);
        residentRepository.save(resident);

        var event = new Event();
        event.setType(Event.Type.EVICTION);
        event.setResident(resident);
        eventRepository.save(event);
    }

    private void acceptDepartureBid(DepartureBid bid) {
        // pass just save bid with ACCEPTED status
    }

    private void acceptRoomChangeBid(RoomChangeBid bid) {
        Room room;
        if (bid.getRoomTo() != null) {
            room = bid.getRoomTo();
        } else {
            List<Integer> roomIds = roomRepository.getIdsByType(bid.getRoomPreferType());
            Optional<Integer> roomIdO = roomIds.stream().filter(roomRepository::isRoomFilled).findFirst();
            if (roomIdO.isEmpty()) {
                throw new BadRequestException("No free room");
            }
            room = roomRepository.findById(roomIdO.get()).get();
        }

        Resident resident = residentRepository.findById(bid.getSender().getLogin()).get();
        resident.setRoom(room);
        residentRepository.save(resident);

        var event = new Event();
        event.setType(Event.Type.ROOM_CHANGE);
        event.setRoom(room);
        event.setResident(resident);
        eventRepository.save(event);
    }

    private BidResponse map(Bid bid) {
        return switch (bid) {
            case DepartureBid db -> new DepartureBidResponse(db);
            case OccupationBid ob -> new OccupationBidResponse(ob);
            case RoomChangeBid rcb -> new RoomChangeResponse(rcb);
            default -> new BidResponse(bid);
        };
    }

    private User currentUser() {
        return userService.getCurrentUserOrThrow();
    }
}
