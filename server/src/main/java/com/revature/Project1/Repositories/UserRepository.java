package com.revature.Project1.Repositories;

import com.revature.Project1.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.userId NOT IN :followedUserIds")
    List<User> findUsersToFollow(Set<Long> followedUserIds);
}