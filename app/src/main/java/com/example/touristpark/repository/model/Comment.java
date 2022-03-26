package com.example.touristpark.repository.model;

public class Comment {
    private User user;
    private String userComment;

    public Comment(User user, String userComment) {
        this.user = user;
        this.userComment = userComment;
    }

    public Comment() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}
