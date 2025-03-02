package ru.itmo.is.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.dto.response.user.ResidentResponse;
import ru.itmo.is.dto.response.user.StaffResponse;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.security.RolesAllowed;
import ru.itmo.is.service.UserService;

import java.util.List;

@RolesAllowed(User.Role.MANAGER)
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/staff")
    public List<StaffResponse> getStaff() {
        return userService.getStaff();
    }

    @DeleteMapping("/fire")
    public void fire(@RequestParam("login") String login) {
        userService.fire(login);
    }

    @GetMapping("/residents")
    public List<ResidentResponse> getResidents() {
        return userService.getResidents();
    }
}
