package ru.itmo.is.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.itmo.is.entity.dorm.Room;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoomResponse {
    int id;
    int dormitoryId;
    int number;
    Room.Type type;
    int capacity;
    int floor;
    int cost;
    int residentsNumber;

    public RoomResponse(Room room) {
        this.id = room.getId();
        this.dormitoryId = room.getDormitory().getId();
        this.number = room.getNumber();
        this.type = room.getType();
        this.capacity = room.getCapacity();
        this.floor = room.getFloor();
        this.cost = room.getCost();
        this.residentsNumber = room.getResidents().size();
    }
}
