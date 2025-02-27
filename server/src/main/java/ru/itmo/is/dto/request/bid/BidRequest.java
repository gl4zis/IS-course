package ru.itmo.is.dto.request.bid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BidRequest {
    @NotBlank
    private String text;
    @NotNull
    private List<String> attachments;
}
