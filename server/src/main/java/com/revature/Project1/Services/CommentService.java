package com.revature.Project1.Services;


import com.revature.Project1.Models.Comment;
import com.revature.Project1.Models.Notification;
import com.revature.Project1.Models.Post;
import com.revature.Project1.Models.User;
import com.revature.Project1.Repositories.CommentRepository;
import com.revature.Project1.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {


    private final CommentRepository commentRepository;


    private final PostRepository postRepository;

    private final PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.postService = postService;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostPostId(postId);
    }

    public Comment createComment(Comment comment) {
        Comment newComment = commentRepository.save(comment);
        Optional<Post> postOptional = postRepository.findById(newComment.getPost().getPostId());
        postOptional.ifPresent(postService::createPostActivityNotification);
        return newComment;
    }

    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }


    public void deleteComment(Long commentId, User user) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            if (user.getAdmin() || comment.getUser().getUserId().equals(user.getUserId())) {
                commentRepository.deleteById(commentId);
            } else {
                throw new SecurityException("User is not authorized to delete this comment");
            }
        }
    }
}