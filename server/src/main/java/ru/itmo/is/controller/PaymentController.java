package ru.itmo.is.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.is.dto.request.PaymentRequest;
import ru.itmo.is.dto.response.DebtResponse;
import ru.itmo.is.dto.response.EvictionResponse;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.security.RolesAllowed;
import ru.itmo.is.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @RolesAllowed(User.Role.RESIDENT)
    @GetMapping("/debt")
    public DebtResponse getDebt() {
        return paymentService.getCurrentUserDebt();
    }

    @RolesAllowed(User.Role.RESIDENT)
    @PostMapping("/pay")
    public void pay(PaymentRequest req) {
        paymentService.currentUserPay(req);
    }

    @RolesAllowed(User.Role.MANAGER)
    @GetMapping("/eviction")
    public List<EvictionResponse> getEvictionsByPayment() {
        return paymentService.getEvictionsByPayment();
    }
}
