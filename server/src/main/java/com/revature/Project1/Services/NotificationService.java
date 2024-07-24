package com.revature.Project1.Services;


import com.revature.Project1.Models.Notification;
import com.revature.Project1.Models.User;
import com.revature.Project1.Repositories.NotificationRepository;
import com.revature.Project1.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId);
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUser_userId(userId);
    }

    public List<Notification> getNotificationsByRead(Boolean read) {
        return notificationRepository.findByRead(read);
    }

    public void sendEmailNotification(Notification notification) {
        String subject = "New Notification: " + notification.getType().name();
        String content = notification.getContent();
        emailService.sendSimpleMessage(notification.getUser().getEmail(), subject, content);
    }

    public Notification createNotification(Notification notification) {
        Notification savedNotification = notificationRepository.save(notification);
        sendEmailNotification(savedNotification);
        return savedNotification;
    }

    public Notification createNotification(User user, Notification.NotificationType type, String content) {
        Notification notification = new Notification(user, type, content);
        return createNotification(notification);
    }

    public Notification updateNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}