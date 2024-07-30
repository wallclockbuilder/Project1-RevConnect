package com.revature.Project1.DTO;

import java.time.LocalDateTime;

public class FollowDto {
    private Long followerId;
    private Long followerUserId;
    private Long followeeUserId;
    private LocalDateTime createdAt;

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowerUserId() {
        return followerUserId;
    }

    public void setFollowerUserId(Long followerUserId) {
        this.followerUserId = followerUserId;
    }

    public Long getFolloweeUserId() {
        return followeeUserId;
    }

    public void setFolloweeUserId(Long followeeUserId) {
        this.followeeUserId = followeeUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
