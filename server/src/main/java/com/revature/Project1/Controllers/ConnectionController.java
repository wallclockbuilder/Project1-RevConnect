package com.revature.Project1.Controllers;

import com.revature.Project1.Models.Connection;
import com.revature.Project1.Services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/connections")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @GetMapping
    public List<Connection> getAllConnections() {
        return connectionService.getAllConnections();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Connection> getConnectionById(@PathVariable Long id) {
        Optional<Connection> connection = connectionService.getConnectionById(id);
        return connection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/requester/{requesterId}")
    public List<Connection> getConnectionsByRequesterId(@PathVariable Long requesterId) {
        return connectionService.getConnectionsByRequesterId(requesterId);
    }

    @GetMapping("/receiver/{receiverId}")
    public List<Connection> getConnectionsByReceiverId(@PathVariable Long receiverId) {
        return connectionService.getConnectionsByReceiverId(receiverId);
    }

    @PostMapping
    public Connection createConnection(@RequestBody Connection connection) {
        return connectionService.createConnection(connection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Connection> updateConnection(@PathVariable Long id, @RequestBody Connection connection) {
        return ResponseEntity.ok(connectionService.updateConnection(id, connection.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConnection(@PathVariable Long id) {
        if (connectionService.getConnectionById(id).isPresent()) {
            connectionService.deleteConnection(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}