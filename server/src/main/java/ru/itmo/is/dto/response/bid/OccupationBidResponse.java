package ru.itmo.is.dto.response.bid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.is.entity.bid.OccupationBid;

@Getter
@Setter
@NoArgsConstructor
public class OccupationBidResponse extends BidResponse{

    public OccupationBidResponse(OccupationBid occupationBid) {
        super(occupationBid);
        this.universityId = occupationBid.getUniversity().getId();
        this.dormitoryId = occupationBid.getDormitory().getId();
    }

    private Integer universityId;
    private Integer dormitoryId;
}
