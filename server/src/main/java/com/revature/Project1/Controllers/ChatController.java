package com.revature.Project1.Controllers;

import com.revature.Project1.Models.Chat;
import com.revature.Project1.Services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public List<Chat> getAllChats() {
        return chatService.getAllChats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable Long id) {
        Optional<Chat> chat = chatService.getChatById(id);
        return chat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/sender/{senderId}")
    public List<Chat> getChatsBySenderId(@PathVariable Long senderId) {
        return chatService.getChatsBySenderId(senderId);
    }

    @GetMapping("/receiver/{receiverId}")
    public List<Chat> getChatsByReceiverId(@PathVariable Long receiverId) {
        return chatService.getChatsByReceiverId(receiverId);
    }

    @PostMapping
    public Chat createChat(@RequestBody Chat chat) {
        return chatService.createChat(chat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.noContent().build();
    }
}