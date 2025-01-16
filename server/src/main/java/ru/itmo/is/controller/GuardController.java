package ru.itmo.is.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.dto.response.GuardHistory;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.security.RolesAllowed;
import ru.itmo.is.service.GuardService;

import java.util.List;

@RolesAllowed(User.Role.SECURITY)
@RestController
@RequestMapping("/guard")
@RequiredArgsConstructor
public class GuardController {
    private final GuardService guardService;

    @PostMapping("/entry")
    public void entry(@RequestParam String login) {
        guardService.entry(login);
    }

    @PostMapping("/exit")
    public void exit(@RequestParam String login) {
        guardService.exit(login);
    }

    @RolesAllowed(User.Role.MANAGER)
    @GetMapping("/history")
    public List<GuardHistory> getHistory(@RequestParam String login) {
        return guardService.getHistory(login);
    }
}
