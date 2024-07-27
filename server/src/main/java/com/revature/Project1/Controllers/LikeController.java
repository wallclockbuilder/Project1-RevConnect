package com.revature.Project1.Controllers;

import com.revature.Project1.DTO.DtoConverter;
import com.revature.Project1.DTO.LikeDto;
import com.revature.Project1.Models.Like;
import com.revature.Project1.Services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin
public class LikeController {


    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeDto> getAllLikes() {
        return likeService.getAllLikes().stream()
                .map(DtoConverter::toLikeDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LikeDto> getLikeById(@PathVariable Long id) {
        Optional<Like> like = likeService.getLikeById(id);
        return like.map(value -> ResponseEntity.ok(DtoConverter.toLikeDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/post/{postId}")
    public List<LikeDto> getLikesByPostId(@PathVariable Long postId) {
        return likeService.getLikesByPostId(postId).stream()
                .map(DtoConverter::toLikeDto)
                .toList();
    }

    @PostMapping
    public LikeDto createLike(@RequestBody Like like) {
        Like savedLike = likeService.createLike(like);
        return DtoConverter.toLikeDto(savedLike);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        if (likeService.getLikeById(id).isPresent()) {
            likeService.deleteLike(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}