package ru.itmo.is.entity.bid;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.storage.FileRecord;

@Entity
@Getter
@Setter
public class BidFile {
    @Id
    private String key;
    @ManyToOne
    @JoinColumn(name = "bid_id")
    private Bid bid;
    private String name;

    public FileRecord record() {
        return new FileRecord(key, name);
    }
}
