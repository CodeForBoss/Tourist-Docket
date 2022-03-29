package com.example.touristpark.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.touristpark.repository.Repository;
import com.example.touristpark.repository.model.User;

public class TouristParkViewModel extends AndroidViewModel {
    public TouristParkViewModel(@NonNull Application application) {
        super(application);
    }
    private Repository repository = new Repository();

    public void createNewUser(User user, Activity activity, Uri imageUri){
        repository.createNewUser(user,activity,imageUri);
    }

}
