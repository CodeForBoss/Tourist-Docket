package com.example.touristpark.repository.model;

import java.util.ArrayList;

public class Place {
    private ArrayList<Comment> allComments;
    private String descriptions, location, userEmail;
    private ArrayList<String> imageUri;
    private float rating;

    public Place(ArrayList<Comment> allComments, String descriptions, String location, ArrayList<String> imageUri,String userEmail, float rating) {
        this.allComments = allComments;
        this.descriptions = descriptions;
        this.location = location;
        this.imageUri = imageUri;
        this.rating = rating;
        this.userEmail = userEmail;
    }
    public Place() {

    }

    public ArrayList<Comment> getAllComments() {
        return allComments;
    }

    public void setAllComments(ArrayList<Comment> allComments) {
        this.allComments = allComments;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ArrayList<String> getImageUri() {
        return imageUri;
    }

    public void setImageUri(ArrayList<String> imageUri) {
        this.imageUri = imageUri;
    }
}
