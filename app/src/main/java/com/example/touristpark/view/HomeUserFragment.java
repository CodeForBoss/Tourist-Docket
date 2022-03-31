package com.example.touristpark.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentHomeUserBinding;

public class HomeUserFragment extends Fragment {
    private FragmentHomeUserBinding fragmentHomeUserBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeUserBinding = FragmentHomeUserBinding.inflate(inflater,container,false);
       return fragmentHomeUserBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}