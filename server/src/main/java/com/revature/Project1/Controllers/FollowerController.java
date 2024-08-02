package com.revature.Project1.Controllers;

import com.revature.Project1.DTO.FollowDto;
import com.revature.Project1.DTO.UserDto;
import com.revature.Project1.Models.User;
import com.revature.Project1.Services.UserService;
import com.revature.Project1.Services.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/users/{userId}")
public class FollowerController {
    private final FollowerService followerService;
    private final UserService userService;

    @Autowired
    public FollowerController(FollowerService followerService, UserService userService) {
        this.followerService = followerService;
        this.userService = userService;
    }

    @GetMapping("/users-to-follow")
    public ResponseEntity<Set<UserDto>> getUsersToFollow(@PathVariable Long userId) {
        Set<UserDto> usersToFollow = followerService.getUsersToFollow(userId);
        return ResponseEntity.ok(usersToFollow);
    }

    @GetMapping("/followers")
    public ResponseEntity<Set<UserDto>> getAllFollowersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(followerService.getFollowersOfUser(userId));
    }

    @GetMapping("/followees")
    public ResponseEntity<Set<UserDto>> getUsersFollowedByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(followerService.getUsersFollowedByUser(userId));
    }

    @PostMapping("/followers")
    public ResponseEntity<Void> createFollower(@RequestBody FollowDto followDto) {
        System.out.println("FollowDTO Incoming: " + followDto +
                " followerUserID: " + followDto.getFollowerUserId() +
                " followeeUserId: " + followDto.getFolloweeUserId());

        User userFollower = userService.getUserById(followDto.getFollowerUserId())
                .orElseThrow(() -> new RuntimeException("Follower user not found"));
        User userToFollow = userService.getUserById(followDto.getFolloweeUserId())
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        userFollower.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(userFollower);

        userService.updateUser(userFollower);
        userService.updateUser(userToFollow);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unfollow/{followeeId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long userId, @PathVariable Long followeeId) {
        followerService.unfollowUser(userId, followeeId);
        return ResponseEntity.ok().build();
    }


    // Uncomment and implement if required in future
    /*
    @GetMapping("/{id}")
    public ResponseEntity<FollowDto> getFollowerById(@PathVariable Long id) {
        Optional<Follower> follower = followerService.getFollowerById(id);
        return follower.map(value -> ResponseEntity.ok(DtoConverter.toFollowDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/follower/{followerUserId}")
    public List<FollowDto> getFollowersByFollowerUserId(@PathVariable Long followerUserId) {
        return followerService.getUsersFollowedByUser(followerUserId).stream()
                .map(DtoConverter::toFollowDto)
                .toList();
    }

    @GetMapping("/following/{followingUserId}")
    public List<FollowDto> getFollowersByFollowingUserId(@PathVariable Long followingUserId) {
        return followerService.getFollowersOfUser(followingUserId).stream()
                .map(DtoConverter::toFollowDto)
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FollowDto> updateFollower(@PathVariable Long id, @RequestBody Follower followerDetails) {
        Optional<Follower> follower = followerService.getFollowerById(id);
        if (follower.isPresent()) {
            followerDetails.setFollowerId(id);
            Follower updatedFollower = followerService.updateFollower(followerDetails);
            return ResponseEntity.ok(DtoConverter.toFollowDto(updatedFollower));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/followers")
    public ResponseEntity<Void> deleteFollower(@PathVariable Long id) {
        followerService.deleteFollower(id);
        return ResponseEntity.noContent().build();
    }
    */
}
