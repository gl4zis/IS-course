package ru.itmo.is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.dto.response.user.ResidentResponse;
import ru.itmo.is.dto.response.user.StaffResponse;
import ru.itmo.is.entity.user.Resident;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.exception.BadRequestException;
import ru.itmo.is.exception.ForbiddenException;
import ru.itmo.is.exception.NotFoundException;
import ru.itmo.is.repository.UserRepository;
import ru.itmo.is.security.SecurityContext;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SecurityContext securityContext;

    public Resident getResidentByLogin(String login) {
        Optional<User> userO = userRepository.findById(login);
        if (userO.isPresent() && userO.get() instanceof Resident res) {
            return res;
        }
        throw new NotFoundException("Resident not found");
    }

    public Resident getCurrentResidentOrThrow() {
        return getResidentByLogin(getCurrentUserOrThrow().getLogin());
    }

    public User getCurrentUserOrThrow() {
        String login = securityContext.getUsername();
        if (login == null) {
            throw new ForbiddenException("You are not logged in");
        }
        return userRepository.findById(login).orElseThrow(() -> new ForbiddenException("You are not logged in"));
    }

    public List<StaffResponse> getStaff() {
        List<User> users = userRepository.getUsersByRoleIn(List.of(User.Role.GUARD, User.Role.MANAGER));
        return users.stream().map(StaffResponse::new).toList();
    }

    // TODO realize toEviction flag
    public List<ResidentResponse> getResidents() {
        List<User> users = userRepository.getUsersByRoleIn(List.of(User.Role.RESIDENT));
        return users.stream()
                .map(user -> (Resident) user)
                .map(resident -> new ResidentResponse(resident, false))
                .toList();
    }

    public void fire(String login) {
        Optional<User> userO = userRepository.findById(login);
        if (userO.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        if (userO.get().getRole() != User.Role.GUARD) {
            throw new BadRequestException("Wrong role");
        }
        userRepository.delete(userO.get());
    }
}
