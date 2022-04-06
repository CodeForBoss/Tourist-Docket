package com.example.touristpark.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.models.SlideModel;
import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentItemDetailBinding;
import com.example.touristpark.repository.model.Comment;
import com.example.touristpark.repository.model.Place;
import com.example.touristpark.view.adapter.CommentRecyclerAdapter;
import com.example.touristpark.view.adapter.PagerAdapter;
import com.example.touristpark.view.adapter.RecyclerViewAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ItemDetailFragment extends Fragment {
    private FragmentItemDetailBinding binding;
    private Place place = new Place();
    private CommentRecyclerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentItemDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkBundle();
        viewPagerSetup();
        setAllValues();
    }

    private void viewPagerSetup() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("placeId",place);
        PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(),binding.tabLayoutId.getTabCount(),bundle);
        binding.viewPagerShow.setAdapter(pagerAdapter);
        binding.tabLayoutId.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPagerShow.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                binding.viewPagerShow.setCurrentItem(tab.getPosition());
            }
        });
    }

    private void setAllValues() {
        if(place !=null){
            ArrayList<SlideModel> allImages  = new ArrayList<>();
            for(int i = 0; i< place.getImageUri().size(); i++){
                allImages.add(new SlideModel(place.getImageUri().get(i),null));
            }
            binding.imageSlider2.setImageList(allImages);
            binding.locationshowId2.setText(place.getLocation());
        }
    }

    private void checkBundle() {
        Bundle bundle = this.getArguments();
        place = bundle.getParcelable("singleParcel");
    }
}