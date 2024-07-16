package com.revature.Project1.Controllers;

import com.revature.Project1.Models.Follower;
import com.revature.Project1.Services.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/followers")
public class FollowerController {

    @Autowired
    private FollowerService followerService;

    @GetMapping
    public List<Follower> getAllFollowers() {
        return followerService.getAllFollowers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Follower> getFollowerById(@PathVariable Long id) {
        Optional<Follower> follower = followerService.getFollowerById(id);
        return follower.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/follower/{followerUserId}")
    public List<Follower> getFollowersByFollowerUserId(@PathVariable Long followerUserId) {
        return followerService.getFollowersByFollowerUserId(followerUserId);
    }

    @GetMapping("/following/{followingUserId}")
    public List<Follower> getFollowersByFollowingUserId(@PathVariable Long followingUserId) {
        return followerService.getFollowersByFollowingUserId(followingUserId);
    }

    @PostMapping
    public Follower createFollower(@RequestBody Follower follower) {
        return followerService.createFollower(follower);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFollower(@PathVariable Long id) {
        followerService.deleteFollower(id);
        return ResponseEntity.noContent().build();
    }
}