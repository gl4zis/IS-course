package ru.itmo.is.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.dorm.Room;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
    List<Integer> getIdsByType(Room.Type type);
    @Query(value = "SELECT NOT is_room_filled(:roomId)", nativeQuery = true)
    boolean isRoomFree(Integer roomId);
}
