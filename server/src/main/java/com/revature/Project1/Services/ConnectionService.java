package com.revature.Project1.Services;


import com.revature.Project1.Models.Connection;
import com.revature.Project1.Models.Notification;
import com.revature.Project1.Models.User;
import com.revature.Project1.Repositories.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    public List<Connection> getAllConnections() {
        return connectionRepository.findAll();
    }

    public Optional<Connection> getConnectionById(Long connectionId) {
        return connectionRepository.findById(connectionId);
    }

    public List<Connection> getConnectionsByRequesterId(Long requesterId) {
        return connectionRepository.findByRequesterUserId(requesterId);
    }

    public List<Connection> getConnectionsByReceiverId(Long receiverId) {
        return connectionRepository.findByReceiverUserId(receiverId);
    }

    public Connection createConnection(Connection connection) {
        connection.setStatus(Connection.Status.PENDING);
        Connection newConnection = connectionRepository.save(connection);
        User receiver = userService.getUserById(connection.getReceiver().getUserId()).orElseThrow();
        notificationService.createNotification(receiver, Notification.NotificationType.CONNECT_REQUEST, "You have a new connect request.");
        return newConnection;
    }

    public Connection updateConnection(Long connectionId, Connection.Status status) {
        Connection connection = connectionRepository.findById(connectionId).orElseThrow(() -> new IllegalArgumentException("Connection not found"));
        connection.setStatus(status);
        connection.setUpdatedAt(LocalDateTime.now());
        Connection updatedConnection = connectionRepository.save(connection);
        notificationService.createNotification(connection.getRequester(), Notification.NotificationType.POST_ACTIVITY, "Your connection request has been " + status.name().toLowerCase() + ".");
        return updatedConnection;
    }

    public void deleteConnection(Long connectionId) {
        connectionRepository.deleteById(connectionId);
    }
}