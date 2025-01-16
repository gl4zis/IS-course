package ru.itmo.is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.dto.response.GuardHistory;
import ru.itmo.is.entity.Event;
import ru.itmo.is.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuardService {
    private final EventRepository eventRepository;
    private final UserService userService;

    public void entry(String login) {
        guardEvent(login, Event.Type.IN);
    }

    public void exit(String login) {
        guardEvent(login, Event.Type.OUT);
    }

    public List<GuardHistory> getHistory(String login) {
        List<Event> events = eventRepository.getByTypeInAndResidentLoginOrderByTimestamp(
                List.of(Event.Type.IN, Event.Type.OUT), login
        );
        return events.stream().map(this::mapGuardEvent).toList();
    }

    private void guardEvent(String login, Event.Type type) {
        var event = new Event();
        event.setResident(userService.getResidentByLogin(login));
        event.setType(type);
        eventRepository.save(event);
    }

    private GuardHistory mapGuardEvent(Event event) {
        return GuardHistory.builder()
                .login(event.getResident().getLogin())
                .type(GuardHistory.Type.fromEventType(event.getType()))
                .timestamp(event.getTimestamp())
                .build();
    }
}
