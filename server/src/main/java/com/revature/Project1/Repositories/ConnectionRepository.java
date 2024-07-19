package com.revature.Project1.Repositories;


import com.revature.Project1.Models.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    List<Connection> findByRequesterUserId(Long userId);
    List<Connection> findByReceiverUserId(Long userId);
}