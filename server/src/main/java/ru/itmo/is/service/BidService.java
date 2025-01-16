package ru.itmo.is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.dto.request.bid.BidRequest;
import ru.itmo.is.dto.request.bid.DepartureRequest;
import ru.itmo.is.dto.request.bid.OccupationRequest;
import ru.itmo.is.dto.request.bid.RoomChangeRequest;
import ru.itmo.is.entity.bid.Bid;
import ru.itmo.is.entity.bid.DepartureBid;
import ru.itmo.is.entity.bid.OccupationBid;
import ru.itmo.is.entity.bid.RoomChangeBid;
import ru.itmo.is.entity.dorm.Dormitory;
import ru.itmo.is.entity.dorm.Room;
import ru.itmo.is.entity.dorm.University;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.exception.BadRequestException;
import ru.itmo.is.repository.BidRepository;
import ru.itmo.is.repository.RoomRepository;
import ru.itmo.is.repository.UniversityRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidService {
    private final UserService userService;
    private final BidRepository bidRepository;
    private final UniversityRepository universityRepository;
    private final RoomRepository roomRepository;

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
        bid.setText(req.getText());
        bid.setUniversity(university);
        bid.setDormitory(dormitoryO.get());
        bid.setFiles(req.getFiles());
        bid.setSender(sender());
        bidRepository.save(bid);
    }

    public void createEvictionBid(BidRequest req) {
        var bid = new Bid();
        bid.setText(req.getText());
        bid.setSender(sender());
        bid.setType(Bid.Type.EVICTION);
        bidRepository.save(bid);
    }

    public void createDepartureBid(DepartureRequest req) {
        var bid = new DepartureBid();
        bid.setDayFrom(req.getDayFrom());
        bid.setDayTo(req.getDayTo());
        bid.setText(req.getText());
        bid.setSender(sender());
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
        bid.setRoomTo(roomO.orElse(null));
        bid.setRoomPreferType(req.getRoomPreferType());
        bid.setText(req.getText());
        bid.setSender(sender());
        bidRepository.save(bid);
    }

    private User sender() {
        return userService.getCurrentUserOrThrow();
    }
}
