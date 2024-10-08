package com.revature.Project1.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String bio;

    @Column(nullable = false)
    private Boolean isAdmin = false;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL)
    private Set<Connection> sentConnections = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private Set<Connection> receivedConnections = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "following_user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_user_id")
    )
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers", fetch = FetchType.LAZY)
    private Set<User> following = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Chat> sentChats = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private Set<Chat> receivedChats = new HashSet<>();

    public User() {}

    public User(String username, String email, String password, String firstName, String lastName, String bio, Boolean isAdmin, Boolean isActive) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
        this.createdAt = LocalDateTime.now();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<Connection> getSentConnections() {
        return sentConnections;
    }

    public void setSentConnections(Set<Connection> sentConnections) {
        this.sentConnections = sentConnections;
    }

    public Set<Connection> getReceivedConnections() {
        return receivedConnections;
    }

    public void setReceivedConnections(Set<Connection> receivedConnections) {
        this.receivedConnections = receivedConnections;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<Chat> getSentChats() {
        return sentChats;
    }

    public void setSentChats(Set<Chat> sentChats) {
        this.sentChats = sentChats;
    }

    public Set<Chat> getReceivedChats() {
        return receivedChats;
    }

    public void setReceivedChats(Set<Chat> receivedChats) {
        this.receivedChats = receivedChats;
    }
}
