package com.example.touristpark.repository;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.touristpark.R;
import com.example.touristpark.repository.model.Place;
import com.example.touristpark.repository.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

public class FirebaseOperation {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private StorageReference storageReference;
    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;
    MutableLiveData<ArrayList<Place>> allPlaces = new MutableLiveData<>();

    public void createNewUser(User user, Activity activity, Uri imageUri) {
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Creating new user....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("user");
        mDatabaseReference = mDatabase.getReference("user").push();
        StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(activity, imageUri));
        ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUrl = uriTask.getResult();
                String imageLink = downloadUrl.toString();
                user.setProfileImageUri(imageLink);
                mDatabaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            builder.setMessage("User created successfully!");
                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.signup_frag_to_place_frag);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(false);
                        } else {
                            progressDialog.dismiss();
                            builder.setMessage("User created Failed! try again");
                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.signup_frag_to_home_frag);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(false);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        builder.setMessage("User created Failed! try again");
                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.signup_frag_to_home_frag);
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                    }
                });
            }
        });

    }
    private String getFileExtention(Activity activity,Uri imageUrl){
        ContentResolver contentResolver = activity.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUrl));
    }

    public void registerNewPlace(Place place, Activity activity, ArrayList<Uri> imageUrl){
        ArrayList<String> allImageLink = new ArrayList<>();
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Register new place....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("place");
        mDatabaseReference = mDatabase.getReference("place").push();
        for(int i = 0; i< imageUrl.size(); i++){
            StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(activity, imageUrl.get(i)));
            ref.putFile(imageUrl.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadUrl = uriTask.getResult();
                    String imageLink = downloadUrl.toString();
                    allImageLink.add(imageLink);
                    if(allImageLink.size() == imageUrl.size()){
                        place.setImageUri(allImageLink);
                        mDatabaseReference.setValue(place).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    builder.setMessage("Register place successfully!");
                                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.signup_frag_to_place_frag);
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                } else {
                                    progressDialog.dismiss();
                                    builder.setMessage("Register failed! try again");
                                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.signup_frag_to_home_frag);
                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                builder.setMessage("Register Failed! try again");
                                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(R.id.signup_frag_to_home_frag);
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                alertDialog.setCanceledOnTouchOutside(false);
                            }
                        });
                    }
                }
            });
        }
    }

}
