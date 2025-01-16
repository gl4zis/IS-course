package ru.itmo.is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.dto.response.NotificationResponse;
import ru.itmo.is.entity.notification.Notification;
import ru.itmo.is.entity.user.User;
import ru.itmo.is.repository.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public List<NotificationResponse> getNewNotifications() {
        User receiver = userService.getCurrentUserOrThrow();
        List<Notification> newNotifications = notificationRepository
                .getByReceiverLoginAndStatus(receiver.getLogin(), Notification.Status.CREATED);
        notificationRepository.setSentStatus(newNotifications.stream().map(Notification::getId).toList());
        return newNotifications.stream().map(this::map).toList();
    }

    private NotificationResponse map(Notification entity) {
        return new NotificationResponse(entity.getBid().getId(), entity.getText());
    }
}
