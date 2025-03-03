package ru.itmo.is.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itmo.is.entity.dorm.Dormitory;

public interface DormitoryRepository extends CrudRepository<Dormitory, Integer> {
}
