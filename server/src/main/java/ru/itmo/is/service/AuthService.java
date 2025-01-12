package ru.itmo.is.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.dto.request.LoginRequest;
import ru.itmo.is.dto.request.PasswordChangeRequest;
import ru.itmo.is.dto.request.RegisterRequest;
import ru.itmo.is.dto.response.JwtResponse;
import ru.itmo.is.entity.User;
import ru.itmo.is.exception.BadRequestException;
import ru.itmo.is.exception.ConflictException;
import ru.itmo.is.exception.UnauthorizedException;
import ru.itmo.is.mapper.UserMapper;
import ru.itmo.is.repository.UserRepository;
import ru.itmo.is.security.JwtManager;
import ru.itmo.is.security.PasswordManager;
import ru.itmo.is.security.SecurityContext;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtManager jwtManager;
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final SecurityContext securityContext;

    public JwtResponse register(RegisterRequest req) {
        return switch (req.getRole()) {
            case MANAGER -> registerManager(mapper.toUser(req));
            case NON_RESIDENT -> saveAndGetToken(mapper.toUser(req));
            default -> throw new BadRequestException("Invalid role");
        };
    }

    public JwtResponse login(LoginRequest req) {
        try {
            User user = userRepository.getReferenceById(req.getLogin());
            if (PasswordManager.matches(req.getPassword(), user.getPassword())) {
                return new JwtResponse(jwtManager.createToken(user), user.getRole());
            }
            throw new UnauthorizedException("Invalid credentials");
        } catch (EntityNotFoundException e) {
            throw new UnauthorizedException("Invalid credentials");
        }
    }

    public void registerOther(RegisterRequest req) {
        saveAndGetToken(mapper.toUser(req));
    }

    public void changePassword(PasswordChangeRequest req) {
        User user = userRepository.getReferenceById(securityContext.getUsername());
        if (!PasswordManager.matches(req.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid old password");
        }
        user.setPassword(req.getNewPassword());
        userRepository.save(user);
    }

    private JwtResponse registerManager(User user) {
        if (isManagerExists()) {
            throw new UnauthorizedException("Invalid role");
        }
        return saveAndGetToken(user);
    }

    private JwtResponse saveAndGetToken(User user) {
        if (isLoginBusy(user.getLogin())) {
            throw new ConflictException("User already exists");
        }
        userRepository.save(user);
        return new JwtResponse(jwtManager.createToken(user), user.getRole());
    }

    private boolean isManagerExists() {
        return userRepository.countByRole(User.Role.MANAGER) > 0;
    }

    private boolean isLoginBusy(String login) {
        try {
            userRepository.getReferenceById(login);
            return false;
        } catch (EntityNotFoundException e) {
            return true;
        }
    }
}
