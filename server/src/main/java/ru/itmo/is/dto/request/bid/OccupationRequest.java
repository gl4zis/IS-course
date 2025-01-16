package ru.itmo.is.dto.request.bid;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.entity.bid.BidFile;

import java.util.List;

@Getter
@Setter
public class OccupationRequest extends BidRequest {
    @NotNull
    private Integer universityId;
    @NotNull
    private Integer dormitoryId;
    @NotNull
    private List<BidFile> files;
}
