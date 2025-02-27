package ru.itmo.is.dto.response.bid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.is.entity.bid.DepartureBid;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DepartureBidResponse extends BidResponse {

    public DepartureBidResponse(DepartureBid bid) {
        super(bid);
        this.dayFrom = bid.getDayFrom();
        this.dayTo = bid.getDayTo();
    }

    private LocalDate dayFrom;
    private LocalDate dayTo;
}
