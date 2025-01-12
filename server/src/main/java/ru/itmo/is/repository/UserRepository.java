package ru.itmo.is.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.is.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    long countByRole(User.Role role);
}
