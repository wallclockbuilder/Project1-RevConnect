package com.revature.Project1.Repositories;


import com.revature.Project1.Models.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findByFollowerUserId(Long userId);
    List<Follower> findByFollowingUserId(Long userId);
}