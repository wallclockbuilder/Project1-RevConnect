package com.revature.Project1.Controllers;

import com.revature.Project1.DTO.ChatDto;
import com.revature.Project1.DTO.DtoConverter;
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


    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public List<ChatDto> getAllChats() {
        return chatService.getAllChats().stream()
                .map(DtoConverter::toChatDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDto> getChatById(@PathVariable Long id) {
        Optional<Chat> chat = chatService.getChatById(id);
        return chat.map(value -> ResponseEntity.ok(DtoConverter.toChatDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<ChatDto>> getChatsBySenderId(@PathVariable Long senderId) {
        List<ChatDto> chatDtos = chatService.getChatsBySenderId(senderId).stream()
                .map(DtoConverter::toChatDto)
                .toList();
        return ResponseEntity.ok(chatDtos);
    }

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<ChatDto>> getChatsByReceiverId(@PathVariable Long receiverId) {
        List<ChatDto> chatDtos = chatService.getChatsByReceiverId(receiverId).stream()
                .map(DtoConverter::toChatDto)
                .toList();
        return ResponseEntity.ok(chatDtos);
    }
    @GetMapping("/users/{user1Id}/{user2Id}")
    public List<ChatDto> getMessagesBetweenUsers(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        return chatService.getMessagesBetweenUsers(user1Id, user2Id).stream()
                .map(DtoConverter::toChatDto)
                .toList();
    }

    @PostMapping
    public ChatDto createChat(@RequestBody Chat chat) {
        Chat savedChat = chatService.createChat(chat);
        return DtoConverter.toChatDto(savedChat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.noContent().build();
    }
}