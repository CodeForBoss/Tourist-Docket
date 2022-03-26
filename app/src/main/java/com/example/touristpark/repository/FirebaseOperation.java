package com.example.touristpark.repository;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.touristpark.R;
import com.example.touristpark.repository.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseOperation {
    private FirebaseDatabase mDatabse;
    private DatabaseReference mDatabaseReference;
    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;

    public void createNewUser(User user, Activity activity){
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Creating new user....");
        progressDialog.show();
        mDatabse = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabse.getReference().child("user").push();
        mDatabaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
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
}
