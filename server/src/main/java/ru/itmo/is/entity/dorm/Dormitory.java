package ru.itmo.is.entity.dorm;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Dormitory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String address;

    @ManyToMany
    @JoinTable(
            name = "university_dormitory",
            joinColumns = @JoinColumn(name = "dormitory_id"),
            inverseJoinColumns = @JoinColumn(name = "university_id")
    )
    private List<University> universities;

    @OneToMany(mappedBy = "dormitory")
    private List<Room> rooms;
}
