package ru.itmo.is.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.entity.dorm.Room;
import ru.itmo.is.entity.user.Resident;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Type type;
    private LocalDateTime timestamp = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "resident")
    private Resident resident;

    public enum Type {
        PAYMENT,
        IN,
        OUT,
        OCCUPATION,
        EVICTION,
        ROOM_CHANGE
    }
}
