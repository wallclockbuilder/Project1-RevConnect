package com.revature.Project1.DTO;

import com.revature.Project1.Models.*;


public class DtoConverter {

    public static UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setBio(user.getBio());
        dto.setAdmin(user.getAdmin());
        dto.setActive(user.getActive());
        return dto;
    }

    public static PostDto toPostDto(Post post) {
        PostDto dto = new PostDto();
        dto.setPostId(post.getPostId());
        dto.setUserId(post.getUser().getUserId());
        dto.setUsername(post.getUser().getUsername());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }

    public static CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setCommentId(comment.getCommentId());
        dto.setPostId(comment.getPost().getPostId());
        dto.setUserId(comment.getUser().getUserId());
        dto.setUsername(comment.getUser().getUsername());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }

    public static LikeDto toLikeDto(Like like) {
        LikeDto dto = new LikeDto();
        dto.setLikeId(like.getLikeId());
        dto.setPostId(like.getPost().getPostId());
        dto.setUserId(like.getUser().getUserId());
        dto.setCreatedAt(like.getCreatedAt());
        return dto;
    }

    public static ConnectionDto toConnectionDto(Connection connection) {
        ConnectionDto dto = new ConnectionDto();
        dto.setConnectionId(connection.getConnectionId());
        dto.setRequesterId(connection.getRequester().getUserId());
        dto.setRequesterUsername(connection.getRequester().getUsername());
        dto.setReceiverId(connection.getReceiver().getUserId());
        dto.setReceiverUsername(connection.getReceiver().getUsername());
        dto.setStatus(connection.getStatus().toString());
        dto.setCreatedAt(connection.getCreatedAt());
        dto.setUpdatedAt(connection.getUpdatedAt());
        return dto;
    }
    public static FollowDto toFollowDto(Follower follow) {
        FollowDto dto = new FollowDto();
        dto.setFollowerId(follow.getFollowerId());
        dto.setFollowerUserId(follow.getFollower().getUserId());
        dto.setFolloweeUserId(follow.getFollowing().getUserId());
        dto.setCreatedAt(follow.getCreatedAt());
        return dto;
    }
    public static ChatDto toChatDto(Chat chat) {
        ChatDto dto = new ChatDto();
        dto.setChatId(chat.getChatId());
        dto.setSenderId(chat.getSender().getUserId());
        dto.setReceiverId(chat.getReceiver().getUserId());
        dto.setSenderUsername(chat.getSender().getUsername());
        dto.setReceiverUsername(chat.getReceiver().getUsername());
        dto.setMessage(chat.getMessage());
        dto.setCreatedAt(chat.getCreatedAt());
        return dto;
    }
    public static NotificationDto toNotificationDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setNotificationId(notification.getNotificationId());
        dto.setUserId(notification.getUser().getUserId());
        dto.setType(notification.getType().toString());
        dto.setContent(notification.getContent());
        dto.setRead(notification.getRead());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;

    }

}
