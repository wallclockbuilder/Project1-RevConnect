package com.revature.Project1.Controllers;

import com.revature.Project1.DTO.FollowDto;
import com.revature.Project1.Models.Follower;
import com.revature.Project1.Models.User;
import com.revature.Project1.Services.FollowerService;
import com.revature.Project1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/followers")
    public List<User> getAllFollowersByUserId(@PathVariable Long userId) {
        return followerService.getFollowersOfUser(userId).stream()
                .map(Follower::getFollower)
                .collect(Collectors.toList());
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
    @GetMapping("/followees")
    public List<User> getUsersFollowedByUserId(@PathVariable Long userId){
        return followerService.getUsersFollowedByUser(userId).stream()
                .map(Follower::getFollower)
                .toList();
    }

    @PostMapping("/followers")
//    public FollowDto createFollower(@RequestBody Follower follower) {
        public User createFollower(@RequestBody FollowDto followDto){
        System.out.println("-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=--==--==-=-=-=-=-" +
                "FollowDTO Incoming: " + followDto +
                "followerUserID: " + followDto.getFollowerUserId() +
                 "followeeUserId: " + followDto.getFolloweeUserId());
        User userFollower = userService.getUserById(followDto.getFollowerUserId())
                .orElseThrow(() -> new RuntimeException("Follower user not found"));
        User userToFollow = userService.getUserById(followDto.getFolloweeUserId())
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        Follower follower = new Follower(userFollower, userToFollow);
        return followerService.createFollower(follower).getFollower();

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

//    @DeleteMapping("/followers")
//    public ResponseEntity<Void> deleteFollower(@PathVariable Long id) {
//        followerService.deleteFollower(id);
//        return ResponseEntity.noContent().build();
//    }
}