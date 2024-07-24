package com.revature.Project1.Controllers;

import com.revature.Project1.Models.User;
import com.revature.Project1.Services.EmailService;
import com.revature.Project1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userDetails.setUserId(id);
            return ResponseEntity.ok(userService.updateUser(userDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ban")
    public ResponseEntity<Void> banUser(@PathVariable Long id) {
        userService.banUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/unban")
    public ResponseEntity<Void> unbanUser(@PathVariable Long id) {
        userService.unbanUser(id);
        return ResponseEntity.ok().build();
    }


}