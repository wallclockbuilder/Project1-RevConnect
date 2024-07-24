package com.revature.Project1.Repositories;


import com.revature.Project1.Models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findBySenderUserId(Long senderId);
    List<Chat> findByReceiverUserId(Long receiverId);
}