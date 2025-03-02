package ru.itmo.is.dto.response.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.itmo.is.entity.user.User;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffResponse extends UserResponse {
    StaffRole role;

    public StaffResponse(User user) {
        super(user);
        role = StaffRole.fromUserRole(user.getRole());
    }

    private enum StaffRole {
        GUARD,
        MANAGER;

        private static StaffRole fromUserRole(User.Role role) {
            return switch (role) {
                case MANAGER -> MANAGER;
                case GUARD -> GUARD;
                default -> throw new IllegalArgumentException("Invalid role");
            };
        }
    }
}
