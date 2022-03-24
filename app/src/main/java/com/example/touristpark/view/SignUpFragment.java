package com.example.touristpark.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentSignInBinding;
import com.example.touristpark.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {
     private FragmentSignUpBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater,container,false);
       return binding.getRoot();
    }
}