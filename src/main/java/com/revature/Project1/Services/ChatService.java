package com.revature.Project1.Services;

import com.revature.Project1.Models.Chat;
import com.revature.Project1.Repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

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
        return chatRepository.save(chat);
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }
}