package com.example.touristpark.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentSignInBinding;
import com.example.touristpark.databinding.FragmentSignUpBinding;
import com.example.touristpark.repository.model.User;
import com.example.touristpark.viewmodel.TouristParkViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpFragment extends Fragment{
     private FragmentSignUpBinding binding;
     private User user;
    private TouristParkViewModel touristParkViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater,container,false);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = new User();
        touristParkViewModel = new ViewModelProvider(requireActivity()).get(TouristParkViewModel.class);
        binding.setUser(user);
        listeners();
    }

    private void listeners(){
        binding.submitBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
                User user = new User(binding.getUser().getName(),binding.getUser().getEmail(),binding.getUser().getPhone(),
                        binding.getUser().getPassword());
                touristParkViewModel.createNewUser(user,requireActivity());
            }
        });
    }

   private void checkValidation(){
        if(binding.getUser().getName().isEmpty()){
            binding.nameId.setError("This can't be empty!");
            return;
        }
        if(binding.getUser().getEmail().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(binding.getUser().getEmail()).matches()){
            binding.emailId.setError("Invalid email!");
            return;
        }
        if(binding.getUser().getPhone().isEmpty()){
            binding.phoneId.setError("This can't be empty!");
            return;
        }
        if(binding.getUser().getPassword().isEmpty()){
            binding.passwordId.setError("This can't be empty!");
            return;
        }
    }
}