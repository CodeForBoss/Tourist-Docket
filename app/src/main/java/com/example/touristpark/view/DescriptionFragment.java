package com.example.touristpark.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentDescritionsBinding;
import com.example.touristpark.repository.model.Place;

public class DescriptionFragment extends Fragment {
    private FragmentDescritionsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDescritionsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       checkBundle();
    }

    private void checkBundle() {

        Bundle bundle = getArguments();
        Place place = bundle.getParcelable("placeId");
        binding.showDescriptionId4.setText(place.getDescriptions());
    }
}