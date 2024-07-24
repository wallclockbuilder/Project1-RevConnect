package com.revature.Project1.Services;


import com.revature.Project1.Models.Like;
import com.revature.Project1.Models.Post;
import com.revature.Project1.Repositories.LikeRepository;
import com.revature.Project1.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    public Optional<Like> getLikeById(Long likeId) {
        return likeRepository.findById(likeId);
    }

    public List<Like> getLikesByPostId(Long postId) {
        return likeRepository.findByPostPostId(postId);
    }

    public Like createLike(Like like) {
        Like newLike = likeRepository.save(like);
        Optional<Post> postOptional = postRepository.findById(newLike.getPost().getPostId());
        postOptional.ifPresent(postService::createPostActivityNotification);
        return newLike;
    }

    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}