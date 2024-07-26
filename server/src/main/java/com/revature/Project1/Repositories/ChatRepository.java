package com.revature.Project1.Repositories;


import com.revature.Project1.Models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findBySenderUserId(Long senderId);
    List<Chat> findByReceiverUserId(Long receiverId);

    @Query("SELECT c FROM Chat c WHERE (c.sender.userId = :user1Id AND c.receiver.userId = :user2Id) OR (c.sender.userId = :user2Id AND c.receiver.userId = :user1Id) ORDER BY c.createdAt")
    List<Chat> findAllBySenderAndReceiver(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);

}