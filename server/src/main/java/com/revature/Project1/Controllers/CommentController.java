package com.revature.Project1.Controllers;

import com.revature.Project1.DTO.CommentDto;
import com.revature.Project1.DTO.DtoConverter;
import com.revature.Project1.Models.Comment;
import com.revature.Project1.Models.User;
import com.revature.Project1.Services.CommentService;
import com.revature.Project1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {


    private final CommentService commentService;


    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping
    public List<CommentDto> getAllComments() {
        return commentService.getAllComments().stream()
                .map(DtoConverter::toCommentDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        return comment.map(value -> ResponseEntity.ok(DtoConverter.toCommentDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/post/{postId}")
    public List<CommentDto> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId).stream()
                .map(DtoConverter::toCommentDto)
                .toList();
    }

    @PostMapping
    public CommentDto createComment(@RequestBody Comment comment) {
        Comment savedComment = commentService.createComment(comment);
        return DtoConverter.toCommentDto(savedComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            commentDetails.setCommentId(id);
            Comment updatedComment = commentService.updateComment(commentDetails);
            return ResponseEntity.ok(DtoConverter.toCommentDto(updatedComment));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        if (commentService.getCommentById(id).isPresent()) {
            User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            commentService.deleteComment(id, user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}