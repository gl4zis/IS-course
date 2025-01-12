package ru.itmo.is.dto.response;

import ru.itmo.is.entity.User;

public record JwtResponse(String token, User.Role role) {
}
