package com.revature.Project1.Services;

import com.revature.Project1.Models.Follower;
import com.revature.Project1.Models.Notification;
import com.revature.Project1.Models.User;
import com.revature.Project1.Repositories.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {


    private final FollowerRepository followerRepository;

    private final UserService userService;


    private final NotificationService notificationService;

    @Autowired
    public FollowerService(FollowerRepository followerRepository, UserService userService, NotificationService notificationService) {
        this.followerRepository = followerRepository;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public List<Follower> getAllFollowers() {
        return followerRepository.findAll();
    }

    public Optional<Follower> getFollowerById(Long followerId) {
        return followerRepository.findById(followerId);
    }


    // Method to get all users that a user is following
    public List<Follower> getUsersFollowedByUser(Long followerUserId) {
        return followerRepository.findByFollowerUserId(followerUserId);
    }


    // Method to get all followers of a user (who are following the user)
    public List<Follower> getFollowersOfUser(Long followingUserId) {
        return followerRepository.findByFollowingUserId(followingUserId);
    }
    public Follower createFollower(Follower follower) {
        Follower newFollower = followerRepository.save(follower);
        User followingUser = userService.getUserById(follower.getFollowing().getUserId()).orElseThrow();
        notificationService.createNotification(followingUser, Notification.NotificationType.NEW_FOLLOWER, "You have a new follower.");
        return newFollower;
    }

    public Follower updateFollower(Follower follower) {
        return followerRepository.save(follower);
    }

    public void deleteFollower(Long followerId) {
        followerRepository.deleteById(followerId);
    }
}