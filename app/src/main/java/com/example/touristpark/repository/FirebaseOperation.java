package com.example.touristpark.repository;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.touristpark.R;
import com.example.touristpark.repository.model.Place;
import com.example.touristpark.repository.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseOperation {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private StorageReference storageReference;
    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;
    private Bundle bundle;

    public void createNewUser(User user, Activity activity, Uri imageUri) {
        bundle = new Bundle();
        bundle.putParcelable("userParcel",user);
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Creating new user....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("user");
        mDatabaseReference = mDatabase.getReference("user").push();
        StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(activity, imageUri));
        ref.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isSuccessful()) ;
            Uri downloadUrl = uriTask.getResult();
            String imageLink = downloadUrl.toString();
            user.setProfileImageUri(imageLink);
            mDatabaseReference.setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    builder.setMessage("User created successfully!");
                    builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.signup_frag_to_homeuser_frag, bundle));
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                } else {
                    progressDialog.dismiss();
                    builder.setMessage("User created Failed! try again");
                    builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.signup_frag_to_home_frag));
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                }
            }).addOnFailureListener(e -> {
                progressDialog.dismiss();
                builder.setMessage("User created Failed! try again");
                builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.signup_frag_to_home_frag));
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
            });
        });

    }
    private String getFileExtention(Activity activity,Uri imageUrl){
        ContentResolver contentResolver = activity.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUrl));
    }

    public void registerNewPlace(User user,Place place, Activity activity, ArrayList<Uri> imageUrl){
        bundle = new Bundle();
        bundle.putParcelable("userParcel",user);
        ArrayList<String> allImageLink = new ArrayList<>();
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Register new place....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("place");
        mDatabaseReference = mDatabase.getReference("place").push();
        for(int i = 0; i < imageUrl.size(); i++){
            StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(activity, imageUrl.get(i)));
            ref.putFile(imageUrl.get(i)).addOnSuccessListener(taskSnapshot -> {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUrl = uriTask.getResult();
                String imageLink = downloadUrl.toString();
                allImageLink.add(imageLink);
                if(allImageLink.size() == imageUrl.size()){
                    place.setImageUri(allImageLink);
                    mDatabaseReference.setValue(place).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            builder.setMessage("Register place successfully!");
                            builder.setPositiveButton(android.R.string.ok, (dialogInterface, i1) ->
                                    Navigation.findNavController(activity, R.id.nav_host_fragment).
                                            navigate(R.id.place_frag_to_homeuser_frag,bundle));
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(false);
                        } else {
                            progressDialog.dismiss();
                            builder.setMessage("Register failed! try again");
                            builder.setPositiveButton(android.R.string.ok, (dialogInterface, i1) ->
                                    Navigation.findNavController(activity, R.id.nav_host_fragment).
                                            navigate(R.id.place_frag_to_homeuser_frag,bundle));
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(false);
                        }
                    }).addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        builder.setMessage("Register Failed! try again");
                        builder.setPositiveButton(android.R.string.ok, (dialogInterface, i1) -> Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.place_frag_to_homeuser_frag));
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                    });
                }
            });
        }
    }

    public void addCommentToPlace(Place place){
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("place");
        mDatabaseReference.orderByChild("placeId").equalTo(place.getPlaceId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Map newMap = new HashMap();
                        newMap.put("allComments",place.getAllComments());
                        ds.getRef().updateChildren(newMap);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateUserProfile(Activity activity,User user, Uri imageUri){
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Updating profile....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        if(imageUri != null){
            storageReference = FirebaseStorage.getInstance().getReference("user");
            StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(activity, imageUri));
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadUrl = uriTask.getResult();
                    String imageLink = downloadUrl.toString();
                    user.setProfileImageUri(imageLink);
                    updateProfile(user);
                }
            });
        } else {
            updateProfile(user);
        }

    }

    private void updateProfile(User user){
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("user");
        mDatabaseReference.orderByChild("email").equalTo(user.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Map newMap = new HashMap();
                        newMap.put("name",user.getName());
                        newMap.put("phone",user.getPhone());
                        newMap.put("password",user.getPassword());
                        newMap.put("profileImageUri",user.getProfileImageUri());
                        ds.getRef().updateChildren(newMap).addOnCompleteListener(task -> progressDialog.dismiss());
                       break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
