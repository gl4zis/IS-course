package ru.itmo.is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.dto.request.PaymentRequest;
import ru.itmo.is.dto.response.DebtResponse;
import ru.itmo.is.dto.response.EvictionResponse;
import ru.itmo.is.entity.Event;
import ru.itmo.is.entity.user.Resident;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.exception.BadRequestException;
import ru.itmo.is.exception.ForbiddenException;
import ru.itmo.is.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserService userService;
    private final EventRepository eventRepository;

    public DebtResponse getCurrentUserDebt() {
        User user = userService.getCurrentUserOrThrow();
        if (user instanceof Resident resident) {
            Integer debt = eventRepository.calculateResidentDebt(resident.getLogin());
            LocalDateTime lastPaymentTime = eventRepository.getLastPaymentTime(resident.getLogin());
            return new DebtResponse(debt, resident.getRoom().getCost(), lastPaymentTime);
        }
        throw new ForbiddenException("Allows only residents");
    }

    public void currentUserPay(PaymentRequest req) {
        User user = userService.getCurrentUserOrThrow();
        if (user instanceof Resident resident) {
            Integer debt = eventRepository.calculateResidentDebt(user.getLogin());
            if (req.getSum() != debt) {
                throw new BadRequestException("You can pay not equals to your debt sum");
            }
            var event = new Event();
            event.setType(Event.Type.PAYMENT);
            event.setResident(resident);
            eventRepository.save(event);
        }
        throw new ForbiddenException("Allows only residents");
    }

    public List<EvictionResponse> getEvictionsByPayment() {
        List<String> residents = eventRepository.getResidentsToEvictionByDebt();
        return residents.stream().map(r -> new EvictionResponse(r, EvictionResponse.Reason.NON_PAYMENT)).toList();
    }
}
