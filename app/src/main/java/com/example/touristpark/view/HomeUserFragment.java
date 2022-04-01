package com.example.touristpark.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentHomeUserBinding;
import com.example.touristpark.repository.model.Place;
import com.example.touristpark.view.adapter.RecyclerViewAdapter;
import com.example.touristpark.viewmodel.TouristParkViewModel;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class HomeUserFragment extends Fragment implements ItemClickListener {
    private FragmentHomeUserBinding fragmentHomeUserBinding;
    private ArrayList<Place> placeList = new ArrayList<>();
    private TouristParkViewModel touristParkViewModel;
    private RecyclerViewAdapter adapter;

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
        touristParkViewModel = new ViewModelProvider(requireActivity()).get(TouristParkViewModel.class);
        setUpRecyclerView();
        observers();
    }
    public void setUpRecyclerView(){
          adapter = new RecyclerViewAdapter(placeList,this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        fragmentHomeUserBinding.recyclerId.setLayoutManager(manager);
        fragmentHomeUserBinding.recyclerId.setAdapter(adapter);
    }

    public void observers(){
        touristParkViewModel.allPlaces.observe(getViewLifecycleOwner(), item-> {
            placeList.clear();
            placeList.addAll(item);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void singleItemClick(Place place) {

    }
}