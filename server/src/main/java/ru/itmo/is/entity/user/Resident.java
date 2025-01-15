package ru.itmo.is.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.entity.Event;
import ru.itmo.is.entity.dorm.Room;
import ru.itmo.is.entity.dorm.University;

import java.util.List;

@Entity
@Getter
@Setter
public class Resident extends User {
    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @OneToMany(mappedBy = "resident", fetch = FetchType.LAZY)
    private List<Event> events;
}
