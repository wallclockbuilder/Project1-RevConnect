package com.revature.Project1.Repositories;


import com.revature.Project1.Models.Chat;
import com.revature.Project1.Models.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    List<Connection> findByRequesterUserId(Long userId);
    List<Connection> findByReceiverUserId(Long userId);

    @Query("SELECT c FROM Connection c WHERE (c.requester.userId = :user1Id AND c.receiver.userId = :user2Id) OR (c.requester.userId = :user2Id AND c.receiver.userId = :user1Id) ORDER BY c.createdAt")
    Connection findAllByRequesterAndReceiver(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);
}