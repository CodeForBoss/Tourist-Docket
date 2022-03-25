package com.example.touristpark.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentAddPlaceBinding;

public class AddPlaceFragment extends Fragment {

    private FragmentAddPlaceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddPlaceBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}