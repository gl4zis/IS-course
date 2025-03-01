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
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Bid> managedBids;

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return login.equals(((User) o).login);
    }

    public enum Role {
        NON_RESIDENT,
        RESIDENT,
        MANAGER,
        GUARD
    }
}
