package ru.itmo.is.dto.response;

import ru.itmo.is.entity.user.User;

public record JwtResponse(String token, User.Role role) {
}
