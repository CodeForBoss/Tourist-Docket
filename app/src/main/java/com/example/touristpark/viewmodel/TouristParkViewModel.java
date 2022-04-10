package com.example.touristpark.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.touristpark.repository.Repository;
import com.example.touristpark.repository.model.Place;
import com.example.touristpark.repository.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TouristParkViewModel extends AndroidViewModel {
    public MutableLiveData<ArrayList<Place>> allPlaces = new MutableLiveData<>();
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    public TouristParkViewModel(@NonNull Application application) {
        super(application);
        loadAllPlaces();
    }

    private final Repository repository = new Repository();

    public void createNewUser(User user, Activity activity, Uri imageUri){
        repository.createNewUser(user,activity,imageUri);
    }

    public void registerNewPlace(Place place, Activity activity, ArrayList<Uri> imageUri){
        repository.registerNewPlace(place,activity, imageUri);
    }

    public void addCommentToPlace(Place place){
        repository.addUserComment(place);
    }

    public void loadAllPlaces(){
        ArrayList<Place> getPlaces = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("place");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getPlaces.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Place place = dataSnapshot.getValue(Place.class);
                    getPlaces.add(place);
                }
                allPlaces.setValue(getPlaces);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
