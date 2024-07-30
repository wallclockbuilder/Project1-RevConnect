package com.revature.Project1.Controllers;

import com.revature.Project1.DTO.FollowDto;
import com.revature.Project1.Models.Follower;
import com.revature.Project1.Models.User;
import com.revature.Project1.Services.FollowerService;
import com.revature.Project1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.revature.Project1.DTO.DtoConverter;

@RestController
@RequestMapping("/api/users/{userId}/followers")
@CrossOrigin
public class FollowerController {


    private final FollowerService followerService;
    private final UserService userService;

    @Autowired
    public FollowerController(FollowerService followerService, UserService userService) {

        this.followerService = followerService;
        this.userService = userService;
    }

    @GetMapping
    public List<FollowDto> getAllFollowers() {
        return followerService.getAllFollowers().stream()
                .map(DtoConverter::toFollowDto)
                .toList();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<FollowDto> getFollowerById(@PathVariable Long id) {
//        Optional<Follower> follower = followerService.getFollowerById(id);
//        return follower.map(value -> ResponseEntity.ok(DtoConverter.toFollowDto(value)))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

//    @GetMapping("/follower/{followerUserId}")
//    public List<FollowDto> getFollowersByFollowerUserId(@PathVariable Long followerUserId) {
//        return followerService.getUsersFollowedByUser(followerUserId).stream()
//                .map(DtoConverter::toFollowDto)
//                .toList();
//    }

//    @GetMapping("/following/{followingUserId}")
//    public List<FollowDto> getFollowersByFollowingUserId(@PathVariable Long followingUserId) {
//        return followerService.getFollowersOfUser(followingUserId).stream()
//                .map(DtoConverter::toFollowDto)
//                .toList();
//    }


    @PostMapping
//    public FollowDto createFollower(@RequestBody Follower follower) {
        public FollowDto createFollower(@RequestBody FollowDto followDto){
        User userFollower = new User(), userToFollow = new User();
        Optional<User> user = userService.getUserById(followDto.getFollowerUserId());
        if(user.isPresent()){
            userFollower = user.get();
        }
        Optional<User> otherUser = userService.getUserById(followDto.getFolloweeUserId());
        if(otherUser.isPresent()) {
            userToFollow = otherUser.get();
        }
        Follower follower = new Follower(userFollower, userToFollow);
        Follower savedFollower = followerService.createFollower(follower);
        return DtoConverter.toFollowDto(savedFollower);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<FollowDto> updateFollower(@PathVariable Long id, @RequestBody Follower followerDetails) {
//        Optional<Follower> follower = followerService.getFollowerById(id);
//        if (follower.isPresent()) {
//            followerDetails.setFollowerId(id);
//            Follower updatedFollower = followerService.updateFollower(followerDetails);
//            return ResponseEntity.ok(DtoConverter.toFollowDto(updatedFollower));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteFollower(@PathVariable Long id) {
//        followerService.deleteFollower(id);
//        return ResponseEntity.noContent().build();
//    }
}