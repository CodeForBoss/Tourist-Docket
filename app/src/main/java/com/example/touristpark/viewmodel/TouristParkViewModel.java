package com.example.touristpark.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.navigation.Navigation;

import com.example.touristpark.R;
import com.example.touristpark.repository.Repository;
import com.example.touristpark.repository.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TouristParkViewModel extends AndroidViewModel {
    public TouristParkViewModel(@NonNull Application application) {
        super(application);
    }
    private Repository repository = new Repository();

    public void createNewUser(User user, Activity activity){
        repository.createNewUser(user,activity);
    }

}
