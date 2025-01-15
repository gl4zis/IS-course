package ru.itmo.is.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.entity.bid.Bid;

import java.util.List;

@Entity
@Table(name = "usr")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    private String login;
    private String password;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Bid> sendBids;
    @OneToMany(mappedBy = "accepter", fetch = FetchType.LAZY)
    private List<Bid> acceptedBids;

    public enum Role {
        NON_RESIDENT,
        RESIDENT,
        MANAGER,
        SECURITY
    }
}
