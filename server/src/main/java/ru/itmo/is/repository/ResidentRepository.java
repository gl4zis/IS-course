package ru.itmo.is.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.user.Resident;

@Repository
public interface ResidentRepository extends CrudRepository<Resident, String> {
}
