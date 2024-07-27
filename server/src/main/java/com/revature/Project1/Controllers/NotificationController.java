package com.revature.Project1.Controllers;

import com.revature.Project1.DTO.DtoConverter;
import com.revature.Project1.DTO.NotificationDto;
import com.revature.Project1.Models.Notification;
import com.revature.Project1.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin
public class NotificationController {


    private final NotificationService notificationService;
    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications().stream()
                .map(DtoConverter::toNotificationDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        return notification.map(value -> ResponseEntity.ok(DtoConverter.toNotificationDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<NotificationDto> getNotificationsByUserId(@PathVariable Long userId) {
        return notificationService.getNotificationsByUserId(userId).stream()
                .map(DtoConverter::toNotificationDto)
                .toList();
    }

    @GetMapping("/read/{read}")
    public List<NotificationDto> getNotificationsByRead(@PathVariable Boolean read) {
        return notificationService.getNotificationsByRead(read).stream()
                .map(DtoConverter::toNotificationDto)
                .toList();
    }

    @PostMapping
    public NotificationDto createNotification(@RequestBody Notification notification) {
        Notification savedNotification = notificationService.createNotification(notification);
        return DtoConverter.toNotificationDto(savedNotification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable Long id, @RequestBody Notification notificationDetails) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        if (notification.isPresent()) {
            notificationDetails.setNotificationId(id);
            Notification updatedNotification = notificationService.updateNotification(notificationDetails);
            return ResponseEntity.ok(DtoConverter.toNotificationDto(updatedNotification));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}