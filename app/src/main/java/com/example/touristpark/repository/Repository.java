package com.example.touristpark.repository;

import android.app.Activity;

import com.example.touristpark.repository.model.User;

public class Repository {
    private final FirebaseOperation firebaseOperation = new FirebaseOperation();

    public void createNewUser(User user, Activity activity){
        firebaseOperation.createNewUser(user,activity);
    }
}
