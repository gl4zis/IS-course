package ru.itmo.is.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.dorm.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
}
