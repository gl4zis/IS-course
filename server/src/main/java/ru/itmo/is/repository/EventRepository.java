package ru.itmo.is.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> getByTypeInAndResidentLoginOrderByTimestamp(Collection<Event.Type> type, String resident);
    @Query(value = "SELECT * FROM calculate_resident_debt(:resident)", nativeQuery = true)
    Integer calculateResidentDebt(String resident);
    @Query(value = "SELECT * FROM get_last_payment_time(:resident)", nativeQuery = true)
    LocalDateTime getLastPaymentTime(String resident);
    @Query(value = "SELECT * FROM get_residents_to_eviction_by_debt()", nativeQuery = true)
    List<String> getResidentsToEvictionByDebt();
}
