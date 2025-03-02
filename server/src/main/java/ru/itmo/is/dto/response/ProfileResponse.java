package ru.itmo.is.dto.response;

import jakarta.annotation.Nullable;
import ru.itmo.is.entity.dorm.Room;
import ru.itmo.is.entity.user.User;

public record ProfileResponse(
        String name,
        String surname,
        User.Role role,
        @Nullable String university,
        @Nullable RoomResponse room
) {
   public record RoomResponse(
            String dormitory,
            int number,
            Room.Type type,
            int capacity,
            int floor,
            int cost
    ) {}
}
