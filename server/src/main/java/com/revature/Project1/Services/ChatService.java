package com.revature.Project1.Services;

import com.revature.Project1.Models.Chat;
import com.revature.Project1.Models.Notification;
import com.revature.Project1.Models.User;
import com.revature.Project1.Repositories.ChatRepository;
import com.revature.Project1.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {


    private final ChatRepository chatRepository;


    private final UserRepository userRepository;


    private final NotificationService notificationService;
    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository, NotificationService notificationService) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public List<Chat> getMessagesBetweenUsers(Long user1Id, Long user2Id) {
        return chatRepository.findAllBySenderAndReceiver(user1Id, user2Id);
    }


    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Optional<Chat> getChatById(Long chatId) {
        return chatRepository.findById(chatId);
    }

    public List<Chat> getChatsBySenderId(Long senderId) {
        return chatRepository.findBySenderUserId(senderId);
    }

    public List<Chat> getChatsByReceiverId(Long receiverId) {
        return chatRepository.findByReceiverUserId(receiverId);
    }

    public Chat createChat(Chat chat) {
        Chat newChat = chatRepository.save(chat);
        notifyReceiver(newChat);
        return newChat;
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    private void notifyReceiver(Chat chat) {
        Optional<User> receiverOptional = userRepository.findById(chat.getReceiver().getUserId());
        if (receiverOptional.isPresent()) {
            User receiver = receiverOptional.get();
            String content = "You have a new chat message from " + chat.getSender() + ": " + chat.getMessage();
            notificationService.createNotification(receiver, Notification.NotificationType.CHAT, content);
        }
    }
}