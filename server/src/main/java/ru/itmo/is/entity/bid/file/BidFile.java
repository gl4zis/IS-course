package ru.itmo.is.entity.bid.file;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.entity.bid.Bid;

@Entity
@Getter
@Setter
public class BidFile {
    @EmbeddedId
    private BidFileKey id;
    @ManyToOne
    @MapsId("bidId")
    @JoinColumn(name = "bid_id")
    private Bid bid;

    private String url;
}
