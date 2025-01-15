package ru.itmo.is.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    boolean existsByLogin(String login);
    long countByRole(User.Role role);
}
