package ru.itmo.is.entity.notification;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class NotificationKey implements Serializable {
    private Long bidId;
    private String receiver;
}
