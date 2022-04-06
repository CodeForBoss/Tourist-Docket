package com.example.touristpark.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentReviewBinding;
import com.example.touristpark.repository.model.Comment;
import com.example.touristpark.repository.model.Place;
import com.example.touristpark.view.adapter.CommentRecyclerAdapter;

public class ReviewFragment extends Fragment implements CommentListener{
     private FragmentReviewBinding binding;
    private Place place = new Place();
    private CommentRecyclerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReviewBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       checkBundle();
        setUpRecyclerView();
    }

    private void checkBundle() {
        Bundle bundle = getArguments();
        place = bundle.getParcelable("placeId");
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