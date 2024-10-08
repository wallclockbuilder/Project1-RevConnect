package com.revature.Project1.Repositories;

import com.revature.Project1.Models.Notification;
import com.revature.Project1.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser_userId(Long userId);
    List<Notification> findByRead(Boolean read);
}