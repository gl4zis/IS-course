package ru.itmo.is.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usr")
@Getter
@Setter
public class User {
    @Id
    private String login;
    private String password;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        NON_RESIDENT,
        RESIDENT,
        MANAGER,
        SECURITY
    }
}
