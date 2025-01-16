package ru.itmo.is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.entity.user.Resident;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.exception.ForbiddenException;
import ru.itmo.is.exception.NotFoundException;
import ru.itmo.is.repository.UserRepository;
import ru.itmo.is.security.SecurityContext;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SecurityContext securityContext;

    public Resident getResidentByLogin(String login) {
        Optional<User> userO = userRepository.findById(login);
        if (userO.isPresent() && userO.get().getRole() == User.Role.RESIDENT && userO.get() instanceof Resident res) {
            return res;
        }
        throw new NotFoundException("Resident not found");
    }

    public Optional<User> getCurrentUser() {
        String login = securityContext.getUsername();
        return login == null ? Optional.empty() : userRepository.findById(login);
    }

    public User getCurrentUserOrThrow() {
        Optional<User> user = getCurrentUser();
        if (user.isPresent()) {
            return user.get();
        }
        throw new ForbiddenException("You are not logged in");
    }
}
