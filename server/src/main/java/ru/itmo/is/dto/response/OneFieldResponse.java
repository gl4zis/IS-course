package ru.itmo.is.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OneFieldResponse<T> {
    @NotNull
    private final T data;
}
