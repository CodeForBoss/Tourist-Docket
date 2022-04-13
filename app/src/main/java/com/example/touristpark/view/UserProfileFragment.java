package com.example.touristpark.view;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.touristpark.databinding.FragmentUserProfileBinding;
import com.example.touristpark.repository.model.User;
import com.example.touristpark.viewmodel.TouristParkViewModel;

public class UserProfileFragment extends Fragment {
    private FragmentUserProfileBinding binding;
    private TouristParkViewModel touristParkViewModel;
    private User user = new User();
    Uri imguri = null;
    boolean isImageSelected = false;
    private static final int IMAGE_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        touristParkViewModel = new ViewModelProvider(requireActivity()).get(TouristParkViewModel.class);
        checkBundle();
        binding.setUsr(user);
        listeners();
    }

    private void listeners() {
        binding.chooseImageId7.setOnClickListener(view -> openChooseFile());
        binding.updateBtnId7.setOnClickListener(view -> {
            if(checkValidation()){
                touristParkViewModel.updateUser(requireActivity(),user,imguri);
            }
        });
    }

    private void checkBundle() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            user = bundle.getParcelable("userProfile");
            Glide.with(getContext()).load(user.getProfileImageUri()).into(binding.profilepicId7);
        }
    }

    private boolean checkValidation(){
        if(binding.nameId7.getText().toString().isEmpty()){
            binding.nameId7.setError("This can't be empty!");
            binding.nameId7.requestFocus();
            return false;
        } else if(binding.phoneId7.getText().toString().isEmpty()){
            binding.phoneId7.setError("This can't be empty!");
            binding.phoneId7.requestFocus();
            return false;
        }else if(binding.passwordId7.getText().toString().isEmpty()){
            binding.passwordId7.setError("This can't be empty!");
            binding.passwordId7.requestFocus();
            return false;
        }else if(binding.passwordId7.getText().toString().length() < 4) {
            binding.passwordId7.setError("Password must be greater than 4 character!");
            binding.passwordId7.requestFocus();
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
            Glide.with(this).load(imguri).into(binding.profilepicId7);
            isImageSelected = true;
        }
    }
}