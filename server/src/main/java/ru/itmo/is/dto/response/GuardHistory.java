package ru.itmo.is.dto.response;

import lombok.Builder;
import lombok.Getter;
import ru.itmo.is.entity.Event;

import java.time.LocalDateTime;

@Builder
@Getter
public class GuardHistory {
    private String login;
    private Event.Type type;
    private LocalDateTime timestamp;
}
