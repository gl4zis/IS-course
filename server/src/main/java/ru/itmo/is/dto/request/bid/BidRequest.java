package ru.itmo.is.dto.request.bid;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidRequest {
    @NotBlank
    private String text;
}
