package ru.itmo.is.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.dorm.University;

@Repository
public interface UniversityRepository extends CrudRepository<University, Integer> {
}
