package ru.itmo.is.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.dorm.Dormitory;

@Repository
public interface DormitoryRepository extends CrudRepository<Dormitory, Integer> {
}
