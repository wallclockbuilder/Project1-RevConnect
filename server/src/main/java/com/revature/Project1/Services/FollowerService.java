package com.revature.Project1.Services;

import com.revature.Project1.DTO.DtoConverter;
import com.revature.Project1.DTO.UserDto;
import com.revature.Project1.Models.User;
import com.revature.Project1.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FollowerService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public FollowerService(UserRepository userRepository, UserService userService) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Transactional
    public Set<UserDto> getUsersFollowedByUser(Long followerUserId) {
        User user = userService.getUserById(followerUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFollowing()
                .stream()
                .map(DtoConverter::toUserDto)
                .collect(Collectors.toSet());
    }
    @Transactional(readOnly=true)
    public Set<UserDto> getFollowersOfUser(Long followeeUserId) {
        User user = userService.getUserById(followeeUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFollowers()
                .stream()
                .map(DtoConverter::toUserDto)
                .collect(Collectors.toSet());
    }
    @Transactional
    public void createFollower(User follower, User followee) {
        follower.getFollowing().add(followee);
        followee.getFollowers().add(follower);
        userService.updateUser(follower);
        userService.updateUser(followee);
    }

    public User updateFollower(User follower) {
        return userService.updateUser(follower);
    }

    public void deleteFollower(Long followerId) {
        userRepository.deleteById(followerId);
    }

    public boolean isFollowerTablePopulated() {
        return userRepository.count() > 7;
    }

    @Transactional
    public void populateSampleFollowers() {
        List<User> users = userService.getAllUsers();
        if (users.size() < 3) {
            System.out.println("Not enough users to populate sample followers.");
            return;
        }
        for (User user : users) {
            User randomUserToFollow = selectRandomUserToFollow(users, user);
            createFollower(user, randomUserToFollow);
        }
    }

    private User selectRandomUserToFollow(List<User> users, User currentUser) {
        Random random = new Random();
        List<User> potentialFollowees = users.stream()
                .filter(user -> !user.getUserId().equals(currentUser.getUserId()))
                .toList();
        return potentialFollowees.get(random.nextInt(potentialFollowees.size()));
    }

    public Set<UserDto> getUsersToFollow(Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Long> followedUserIds = user.getFollowing().stream()
                .map(User::getUserId)
                .collect(Collectors.toSet());
        followedUserIds.add(userId); // Exclude the user themselves

        List<User> usersToFollow = userRepository.findUsersToFollow(followedUserIds);

        return usersToFollow.stream()
                .map(DtoConverter::toUserDto)
                .collect(Collectors.toSet());
    }

    public void unfollowUser(Long userId, Long followeeId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User followee = userService.getUserById(followeeId)
                .orElseThrow(() -> new RuntimeException("Followee not found"));

        user.getFollowing().remove(followee);
        followee.getFollowers().remove(user);

        userService.updateUser(user);
        userService.updateUser(followee);
    }
}
