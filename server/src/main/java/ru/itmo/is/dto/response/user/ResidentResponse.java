package ru.itmo.is.dto.response.user;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.itmo.is.dto.response.EvictionResponse;
import ru.itmo.is.entity.user.Resident;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResidentResponse extends UserResponse {
    String university;
    String dormitory;
    int roomNumber;
    int debt;
    @Nullable
    LocalDateTime lastCameOut;
    @Nullable
    EvictionResponse.Reason evictionReason;

    public ResidentResponse(
            Resident resident,
            int debt,
            @Nullable LocalDateTime lastCameOut,
            @Nullable EvictionResponse.Reason evictionReason
    ) {
        super(resident);
        this.university = resident.getUniversity().getName();
        this.dormitory = resident.getRoom().getDormitory().getAddress();
        this.roomNumber = resident.getRoom().getNumber();
        this.debt = debt;
        this.lastCameOut = lastCameOut;
        this.evictionReason = evictionReason;
    }
}
