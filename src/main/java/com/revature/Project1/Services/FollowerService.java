package com.revature.Project1.Services;

import com.revature.Project1.Models.Follower;
import com.revature.Project1.Repositories.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {

    @Autowired
    private FollowerRepository followerRepository;

    public List<Follower> getAllFollowers() {
        return followerRepository.findAll();
    }

    public Optional<Follower> getFollowerById(Long followerId) {
        return followerRepository.findById(followerId);
    }

    public List<Follower> getFollowersByFollowerUserId(Long followerUserId) {
        return followerRepository.findByFollowerUserId(followerUserId);
    }

    public List<Follower> getFollowersByFollowingUserId(Long followingUserId) {
        return followerRepository.findByFollowingUserId(followingUserId);
    }

    public Follower createFollower(Follower follower) {
        return followerRepository.save(follower);
    }

    public void deleteFollower(Long followerId) {
        followerRepository.deleteById(followerId);
    }
}