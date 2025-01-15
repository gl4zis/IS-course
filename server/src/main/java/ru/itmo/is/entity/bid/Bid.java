package ru.itmo.is.entity.bid;

import jakarta.persistence.*;
import ru.itmo.is.entity.Event;
import ru.itmo.is.entity.user.User;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String text;
    @ManyToOne
    @JoinColumn(name = "sender")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "accepter")
    private User accepter;
    @OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    public enum Type {
        OCCUPATION,
        DEPARTURE,
        ROOM_CHANGE,
        EVICTION
    }

    public enum Status {
        CREATED,
        IN_PROCESS,
        ACCEPTED,
        DENIED
    }
}
