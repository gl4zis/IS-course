package ru.itmo.is.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.Event;

import java.util.Collection;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> getByTypeInAndResidentLoginOrderByTimestamp(Collection<Event.Type> type, String resident);
}
