package com.example.touristpark.view;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.touristpark.databinding.FragmentSignUpBinding;
import com.example.touristpark.repository.model.User;
import com.example.touristpark.viewmodel.TouristParkViewModel;

public class SignUpFragment extends Fragment{
     private FragmentSignUpBinding binding;
     private User user;
    private TouristParkViewModel touristParkViewModel;
    private static final int IMAGE_REQUEST = 1;
    Uri imguri;
    boolean isImageSelected = false;

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
                if(checkValidation()){
                    User user = new User(binding.getUser().getName(),binding.getUser().getEmail(),binding.getUser().getPhone(),
                            binding.getUser().getPassword(),"");
                    touristParkViewModel.createNewUser(user,requireActivity(),imguri);
                }
            }
        });
        binding.chooseImageId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChooseFile();
            }
        });
    }

   private boolean checkValidation(){
        if(binding.nameId.getText().toString().isEmpty()){
            binding.nameId.setError("This can't be empty!");
            binding.nameId.requestFocus();
            return false;
        }else if(binding.emailId.getText().toString().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(binding.emailId.getText().toString()).matches()){
            binding.emailId.setError("Invalid email!");
            binding.emailId.requestFocus();
            return false;
        } else if(binding.phoneId.getText().toString().isEmpty()){
            binding.phoneId.setError("This can't be empty!");
            binding.phoneId.requestFocus();
            return false;
        }else if(binding.passwordId.getText().toString().isEmpty()){
            binding.passwordId.setError("This can't be empty!");
            binding.passwordId.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(binding.confirmpasswordId.getText())){
            binding.confirmpasswordId.setError("This can't be empty!");
            binding.confirmpasswordId.requestFocus();
            return false;
        }else if(binding.passwordId.getText().toString().length() < 4){
            binding.passwordId.setError("Password must be greater than 4 character!");
            binding.passwordId.requestFocus();
            return false;
        }else if(!binding.passwordId.getText().toString().equals(binding.confirmpasswordId.getText().toString())){
            Toast.makeText(getContext(), "Password doesn't match!", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!isImageSelected){
            Toast.makeText(getContext(), "Please select a profile picture!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void openChooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST && resultCode == RESULT_OK && data!=null&& data.getData()!=null) {
            imguri = data.getData();
            Glide.with(this).load(imguri).into(binding.profilepicId);
            isImageSelected = true;
        }
    }
}