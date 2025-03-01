package ru.itmo.is.dto.response.util;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BaseResponse<T> {
    @NotNull
    private final T data;
}
