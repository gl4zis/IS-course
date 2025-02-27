package ru.itmo.is.dto.response.bid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.is.entity.bid.RoomChangeBid;
import ru.itmo.is.entity.dorm.Room;

@Getter
@Setter
@NoArgsConstructor
public class RoomChangeResponse extends BidResponse {

    public RoomChangeResponse(RoomChangeBid bid) {
        super(bid);
        Room roomTo = bid.getRoomTo();
        this.roomToId = roomTo == null ? null : roomTo.getId();
        this.roomPreferType = bid.getRoomPreferType();
    }

    private Integer roomToId;
    private Room.Type roomPreferType;
}
