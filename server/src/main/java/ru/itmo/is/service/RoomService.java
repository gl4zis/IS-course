package ru.itmo.is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.dto.request.RoomRequest;
import ru.itmo.is.dto.response.RoomResponse;
import ru.itmo.is.entity.dorm.Dormitory;
import ru.itmo.is.entity.dorm.Room;
import ru.itmo.is.entity.user.Resident;
import ru.itmo.is.exception.BadRequestException;
import ru.itmo.is.exception.NotFoundException;
import ru.itmo.is.repository.DormitoryRepository;
import ru.itmo.is.repository.RoomRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final UserService userService;
    private final RoomRepository roomRepository;
    private final DormitoryRepository dormitoryRepository;

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAllByOrderById().stream().map(RoomResponse::new).toList();
    }

    public List<RoomResponse> getForResident(String login) {
        Resident resident = userService.getResidentByLogin(login);
        return roomRepository.getAvailableInDormitory(resident.getRoom().getDormitory().getId()).stream()
                .filter(r -> !Objects.equals(r.getId(), resident.getRoom().getId()))
                .map(RoomResponse::new)
                .toList();
    }

    public RoomResponse getRoom(int id) {
        return roomRepository.findById(id)
                .map(RoomResponse::new)
                .orElseThrow(() -> new NotFoundException("No such room"));
    }

    public void addRoom(RoomRequest req) {
        if (roomRepository.getByDormitoryIdAndNumber(req.getDormitoryId(), req.getNumber()).isPresent()) {
            throw new BadRequestException("Such room already exists");
        }

        Optional<Dormitory> dormO = dormitoryRepository.findById(req.getDormitoryId());
        if (dormO.isEmpty()) {
            throw new NotFoundException("No such dormitory");
        }

        Room room = new Room();
        room.setDormitory(dormO.get());
        room.setNumber(req.getNumber());
        room.setType(req.getType());
        room.setCapacity(req.getCapacity());
        room.setFloor(req.getFloor());
        room.setCost(req.getCost());
        roomRepository.save(room);
    }

    public void deleteRoom(int id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such room"));
        if (!room.getResidents().isEmpty()) {
            throw new BadRequestException("Room has residents");
        }
        roomRepository.delete(room);
    }
}
