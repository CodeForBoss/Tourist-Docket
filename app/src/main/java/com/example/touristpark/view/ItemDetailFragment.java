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
import com.example.touristpark.view.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class ItemDetailFragment extends Fragment implements CommentListener {
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
        setAllValues();
        setUpRecyclerView();
    }

    private void setAllValues() {
        if(place !=null){
            ArrayList<SlideModel> allImages  = new ArrayList<>();
            for(int i = 0; i< place.getImageUri().size(); i++){
                allImages.add(new SlideModel(place.getImageUri().get(i),null));
            }
            binding.imageSlider2.setImageList(allImages);
            binding.locationshowId2.setText(place.getLocation());
            binding.descriptionShowId2.setText(place.getDescriptions());
        }
    }

    private void checkBundle() {
        Bundle bundle = this.getArguments();
        place = bundle.getParcelable("singleParcel");
    }

    public void setUpRecyclerView(){
        adapter = new CommentRecyclerAdapter(place.getAllComments(),this,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.commentRecyclerId.setLayoutManager(manager);
        binding.commentRecyclerId.setAdapter(adapter);
    }

    @Override
    public void commentClick(Comment comment) {

    }
}