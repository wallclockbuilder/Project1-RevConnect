package com.revature.Project1.Controllers;


import com.revature.Project1.DTO.DtoConverter;
import com.revature.Project1.DTO.PostDto;
import com.revature.Project1.Models.Post;
import com.revature.Project1.Models.User;
import com.revature.Project1.Services.PostService;
import com.revature.Project1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostController {


    private final PostService postService;


    private final UserService userService;
    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts().stream()
                .map(DtoConverter::toPostDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(value -> ResponseEntity.ok(DtoConverter.toPostDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Long userId) {
        List<PostDto> postDtos = postService.getPostsByUserId(userId).stream()
                .map(DtoConverter::toPostDto)
                .toList();
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public PostDto createPost(@RequestBody Post post) {
        Post savedPost = postService.createPost(post);
        return DtoConverter.toPostDto(savedPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            postDetails.setPostId(id);
            Post updatedPost = postService.updatePost(postDetails);
            return ResponseEntity.ok(DtoConverter.toPostDto(updatedPost));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (postService.getPostById(id).isPresent()) {
            User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            postService.deletePost(id, user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}