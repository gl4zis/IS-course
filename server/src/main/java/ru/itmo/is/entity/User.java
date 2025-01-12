package ru.itmo.is.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
