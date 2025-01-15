package ru.itmo.is.entity.bid.file;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class BidFileKey implements Serializable {
    private Long bidId;
    private String name;
}
