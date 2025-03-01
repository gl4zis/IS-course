package ru.itmo.is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.dto.request.LoginRequest;
import ru.itmo.is.dto.request.PasswordChangeRequest;
import ru.itmo.is.dto.request.RegisterRequest;
import ru.itmo.is.dto.response.util.BaseResponse;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.exception.BadRequestException;
import ru.itmo.is.exception.ConflictException;
import ru.itmo.is.exception.UnauthorizedException;
import ru.itmo.is.mapper.UserMapper;
import ru.itmo.is.repository.UserRepository;
import ru.itmo.is.security.JwtManager;
import ru.itmo.is.security.PasswordManager;
import ru.itmo.is.security.SecurityContext;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtManager jwtManager;
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final SecurityContext securityContext;

    public BaseResponse<String> register(RegisterRequest req) {
        return switch (req.getRole()) {
            case MANAGER -> registerManager(mapper.toUser(req));
            case NON_RESIDENT -> saveAndGetToken(mapper.toUser(req));
            default -> throw new BadRequestException("Invalid role");
        };
    }

    public BaseResponse<String> login(LoginRequest req) {
        Optional<User> userO = userRepository.findById(req.getLogin());
        if (userO.isEmpty() || !PasswordManager.matches(req.getPassword(), userO.get().getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        return new BaseResponse<>(jwtManager.createToken(userO.get()));
    }

    public void registerOther(RegisterRequest req) {
        saveAndGetToken(mapper.toUser(req));
    }

    public void changePassword(PasswordChangeRequest req) {
        Optional<User> userO = userRepository.findById(securityContext.getUsername());
        if (userO.isEmpty()) {
            throw new UnauthorizedException("Invalid auth token");
        }
        User user = userO.get();
        if (!PasswordManager.matches(req.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid old password");
        }
        user.setPassword(req.getNewPassword());
        userRepository.save(user);
    }

    private BaseResponse<String> registerManager(User user) {
        if (isManagerExists()) {
            throw new UnauthorizedException("Invalid role");
        }
        return saveAndGetToken(user);
    }

    private BaseResponse<String> saveAndGetToken(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new ConflictException("User already exists");
        }
        userRepository.save(user);
        return new BaseResponse<>(jwtManager.createToken(user));
    }

    private boolean isManagerExists() {
        return userRepository.countByRole(User.Role.MANAGER) > 0;
    }
}
