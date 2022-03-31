package com.example.touristpark.repository;

import android.app.Activity;
import android.net.Uri;

import com.example.touristpark.repository.model.Place;
import com.example.touristpark.repository.model.User;

import java.util.ArrayList;

public class Repository {
    private final FirebaseOperation firebaseOperation = new FirebaseOperation();

    public void createNewUser(User user, Activity activity, Uri imageUri){
        firebaseOperation.createNewUser(user,activity,imageUri);
    }
    public void registerNewPlace(Place place, Activity activity, ArrayList<Uri> imageUri){
        firebaseOperation.registerNewPlace(place,activity,imageUri);
    }
}
