package ru.itmo.is.dto.response.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.itmo.is.entity.user.Resident;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResidentResponse extends UserResponse {
    String university;
    String dormitory;
    int roomNumber;
    boolean toEviction;

    public ResidentResponse(Resident resident, boolean toEviction) {
        super(resident);
        this.university = resident.getUniversity().getName();
        this.dormitory = resident.getRoom().getDormitory().getAddress();
        this.roomNumber = resident.getRoom().getNumber();
        this.toEviction = toEviction;
    }
}
