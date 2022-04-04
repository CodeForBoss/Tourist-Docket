package com.example.touristpark.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Place implements Parcelable {
    private ArrayList<Comment> allComments;
    private String descriptions, location, userEmail;
    private ArrayList<String> imageUri;

    public Place(ArrayList<Comment> allComments, String descriptions, String location, ArrayList<String> imageUri,String userEmail) {
        this.allComments = allComments;
        this.descriptions = descriptions;
        this.location = location;
        this.imageUri = imageUri;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
