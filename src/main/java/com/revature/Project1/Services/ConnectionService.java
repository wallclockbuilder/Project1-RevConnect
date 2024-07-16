package com.revature.Project1.Services;


import com.revature.Project1.Models.Connection;
import com.revature.Project1.Repositories.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

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
        return connectionRepository.save(connection);
    }

    public Connection updateConnection(Connection connection) {
        return connectionRepository.save(connection);
    }

    public void deleteConnection(Long connectionId) {
        connectionRepository.deleteById(connectionId);
    }
}