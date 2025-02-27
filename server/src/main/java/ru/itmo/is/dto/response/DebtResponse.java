package ru.itmo.is.dto.response;

import java.time.LocalDateTime;

public record DebtResponse(Integer debt, Integer roomCost, LocalDateTime lastPaymentTime) {
}
