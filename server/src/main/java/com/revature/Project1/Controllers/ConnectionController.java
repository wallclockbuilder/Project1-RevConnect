package com.revature.Project1.Controllers;

import com.revature.Project1.DTO.ConnectionDto;
import com.revature.Project1.DTO.DtoConverter;
import com.revature.Project1.Models.Connection;
import com.revature.Project1.Services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/connections")
@CrossOrigin
public class ConnectionController {


    private final ConnectionService connectionService;

    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping
    public List<ConnectionDto> getAllConnections() {
        return connectionService.getAllConnections().stream()
                .map(DtoConverter::toConnectionDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectionDto> getConnectionById(@PathVariable Long id) {
        Optional<Connection> connection = connectionService.getConnectionById(id);
        return connection.map(value -> ResponseEntity.ok(DtoConverter.toConnectionDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/requester/{requesterId}")
    public List<ConnectionDto> getConnectionsByRequesterId(@PathVariable Long requesterId) {
        return connectionService.getConnectionsByRequesterId(requesterId).stream()
                .map(DtoConverter::toConnectionDto)
                .toList();
    }

    @GetMapping("/receiver/{receiverId}")
    public List<ConnectionDto> getConnectionsByReceiverId(@PathVariable Long receiverId) {
        return connectionService.getConnectionsByReceiverId(receiverId).stream()
                .map(DtoConverter::toConnectionDto)
                .toList();
    }
    @GetMapping("/{user1Id}/{user2Id}")
    public ResponseEntity<ConnectionDto> findAllByRequesterAndReceiver(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        Connection connection = connectionService.findAllByRequesterAndReceiver(user1Id, user2Id);
        if (connection == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(DtoConverter.toConnectionDto(connection));
        }
    }
    @PostMapping
    public ConnectionDto createConnection(@RequestBody Connection connection) {
        Connection savedConnection = connectionService.createConnection(connection);
        return DtoConverter.toConnectionDto(savedConnection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConnectionDto> updateConnection(@PathVariable Long id, @RequestBody Connection connection) {
        Connection updatedConnection = connectionService.updateConnection(id, connection.getStatus());
        return ResponseEntity.ok(DtoConverter.toConnectionDto(updatedConnection));
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