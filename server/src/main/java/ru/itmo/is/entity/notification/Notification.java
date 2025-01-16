package ru.itmo.is.entity.notification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.entity.bid.Bid;
import ru.itmo.is.entity.user.User;

@Entity
@Getter
@Setter
public class Notification {
    @EmbeddedId
    private NotificationKey id;
    @ManyToOne
    @MapsId("bidId")
    @JoinColumn(name = "bid_id")
    private Bid bid;
    @ManyToOne
    @MapsId("receiver")
    @JoinColumn(name = "receiver")
    private User receiver;

    private String text;
    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    public enum Status {
        CREATED,
        SENT
    }
}
