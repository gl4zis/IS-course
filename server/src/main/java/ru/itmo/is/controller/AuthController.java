package ru.itmo.is.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.is.dto.request.LoginRequest;
import ru.itmo.is.dto.request.PasswordChangeRequest;
import ru.itmo.is.dto.request.RegisterRequest;
import ru.itmo.is.dto.response.JwtResponse;
import ru.itmo.is.entity.User;
import ru.itmo.is.security.RolesAllowed;
import ru.itmo.is.security.Unauthorized;
import ru.itmo.is.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Unauthorized
    @PostMapping("/register")
    public JwtResponse register(RegisterRequest req) {
        return authService.register(req);
    }

    @Unauthorized
    @PostMapping("/login")
    public JwtResponse login(LoginRequest req) {
        return authService.login(req);
    }

    @RolesAllowed({User.Role.MANAGER})
    @PostMapping("/register-other")
    public void registerOther(RegisterRequest req) {
        authService.registerOther(req);
    }

    @PostMapping("/change-pass")
    public void changePassword(PasswordChangeRequest req) {
        authService.changePassword(req);
    }
}
