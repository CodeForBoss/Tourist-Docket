package com.example.touristpark.view;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.denzcoskun.imageslider.models.SlideModel;
import com.example.touristpark.R;
import com.example.touristpark.databinding.FragmentItemDetailBinding;
import com.example.touristpark.repository.model.Comment;
import com.example.touristpark.repository.model.Place;
import com.example.touristpark.repository.model.User;
import com.example.touristpark.view.adapter.CommentRecyclerAdapter;
import com.example.touristpark.viewmodel.TouristParkViewModel;

import java.util.ArrayList;

public class ItemDetailFragment extends Fragment implements CommentListener{
    private FragmentItemDetailBinding binding;
    private Place place = new Place();
    private TouristParkViewModel touristParkViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentItemDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        touristParkViewModel = new ViewModelProvider(requireActivity()).get(TouristParkViewModel.class);
        checkBundle();
        setAllValues();
        setUpRecyclerView();
        listeners();
    }
    private void listeners(){
        binding.descriptionBtnId.setOnClickListener(view -> {
            if(binding.reviewsLayoutId.getVisibility() == View.VISIBLE){
                binding.reviewsLayoutId.setVisibility(View.GONE);
                binding.reviewButtonId.setBackground(getResources().getDrawable(R.drawable.rectangle_shape));
            }
            binding.descriptionsLayoutId.setVisibility(View.VISIBLE);
            binding.descriptionBtnId.setBackground(getResources().getDrawable(R.drawable.round_shape));
        });
        binding.reviewButtonId.setOnClickListener(view -> {
            if(binding.descriptionsLayoutId.getVisibility() == View.VISIBLE){
                binding.descriptionsLayoutId.setVisibility(View.GONE);
                binding.descriptionBtnId.setBackground(getResources().getDrawable(R.drawable.rectangle_shape));
            }
            binding.reviewsLayoutId.setVisibility(View.VISIBLE);
            binding.reviewButtonId.setBackground(getResources().getDrawable(R.drawable.round_shape));
        });

        binding.addCommentBtnId.setOnClickListener(view -> addCommentAlertDialog());
    }

    private void setAllValues() {
        if(place !=null){
            ArrayList<SlideModel> allImages  = new ArrayList<>();
            for(int i = 0; i< place.getImageUri().size(); i++){
                allImages.add(new SlideModel(place.getImageUri().get(i),null));
            }
            binding.imageSlider2.setImageList(allImages);
            binding.locationshowId2.setText(place.getLocation());
            binding.showDescriptionsId5.setText(place.getDescriptions());
        }
    }

    private void checkBundle() {
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            place = bundle.getParcelable("singleParcel");
        }
    }

    private void setUpRecyclerView(){
        CommentRecyclerAdapter adapter = new CommentRecyclerAdapter(place.getAllComments(), this, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.commentRecyclerId.setLayoutManager(manager);
        binding.commentRecyclerId.setAdapter(adapter);
    }

    private void addCommentAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.add_comment_layout,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        Button cancel = view.findViewById(R.id.cancelBtnId);
        cancel.setOnClickListener(view1 -> alertDialog.dismiss());
        Button okButton = view.findViewById(R.id.okBtnId);
        EditText comments = view.findViewById(R.id.addcommentId);
        okButton.setOnClickListener(view12 -> {
            if(TextUtils.isEmpty(comments.getText())){
                comments.setError("Add a comment!");
                comments.requestFocus();
                return;
            }
            ArrayList<Comment> allComments = new ArrayList<>(place.getAllComments());
            User user =new User("anisur", "anisurhju","88843","4835784","asidei");
            Comment comment = new Comment(user,comments.getText().toString(), 3.5F);
            allComments.add(comment);
            place.setAllComments(allComments);
            touristParkViewModel.addCommentToPlace(place);
            alertDialog.dismiss();
            setUpRecyclerView();
        });
    }


    @Override
    public void commentClick(Comment comment) {

    }
}