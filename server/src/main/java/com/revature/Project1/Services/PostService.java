package com.revature.Project1.Services;


import com.revature.Project1.Models.Follower;
import com.revature.Project1.Models.Notification;
import com.revature.Project1.Models.Post;
import com.revature.Project1.Models.User;
import com.revature.Project1.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FollowerService followerService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailService emailService;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserUserId(userId);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }


    public void deletePost(Long postId, User user) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (user.getAdmin() || post.getUser().getUserId().equals(user.getUserId())) {
                postRepository.deleteById(postId);
            } else {
                throw new SecurityException("User is not authorized to delete this post");
            }
        }
    }


    public void createPostActivityNotification(Post post) {
        User postOwner = post.getUser();
        notificationService.createNotification(postOwner, Notification.NotificationType.POST_ACTIVITY, "There is new activity on your post.");
    }
}