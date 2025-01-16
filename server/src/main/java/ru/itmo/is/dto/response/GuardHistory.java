package ru.itmo.is.dto.response;

import lombok.Builder;
import lombok.Getter;
import ru.itmo.is.entity.Event;

import java.time.LocalDateTime;

@Builder
@Getter
public class GuardHistory {
    private String login;
    private Type type;
    private LocalDateTime timestamp;

    public enum Type {
        IN,
        OUT;

        public static Type fromEventType(Event.Type eventType) {
            return switch (eventType) {
                case Event.Type.IN -> IN;
                case Event.Type.OUT -> OUT;
                default -> throw new IllegalArgumentException("Invalid event type");
            };
        }
    }
}
