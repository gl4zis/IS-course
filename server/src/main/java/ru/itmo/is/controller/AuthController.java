package ru.itmo.is.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.is.dto.request.LoginRequest;
import ru.itmo.is.dto.request.PasswordChangeRequest;
import ru.itmo.is.dto.request.RegisterRequest;
import ru.itmo.is.dto.response.JwtResponse;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.security.RolesAllowed;
import ru.itmo.is.security.Anonymous;
import ru.itmo.is.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Anonymous
    @PostMapping("/register")
    public JwtResponse register(@RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    @Anonymous
    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @RolesAllowed({User.Role.MANAGER})
    @PostMapping("/register-other")
    public void registerOther(@RequestBody RegisterRequest req) {
        authService.registerOther(req);
    }

    @PostMapping("/change-pass")
    public void changePassword(@RequestBody PasswordChangeRequest req) {
        authService.changePassword(req);
    }
}
