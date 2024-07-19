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

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;



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