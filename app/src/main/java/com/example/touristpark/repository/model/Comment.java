package com.example.touristpark.repository.model;

public class Comment {
    private User user;
    private String userComment;
    private float rating;

    public Comment(User user, String userComment, float rating) {
        this.user = user;
        this.userComment = userComment;
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
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
