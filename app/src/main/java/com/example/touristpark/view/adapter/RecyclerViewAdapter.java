package com.example.touristpark.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.touristpark.R;
import com.example.touristpark.repository.model.Comment;
import com.example.touristpark.repository.model.Place;
import com.example.touristpark.view.ItemClickListener;
import com.example.touristpark.view.ItemDetailFragment;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Place> allPlaces = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public RecyclerViewAdapter(ArrayList<Place> allPlaces, ItemClickListener itemClickListener) {
        this.allPlaces = allPlaces;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Place place = allPlaces.get(position);
       holder.locationShow.setText(allPlaces.get(position).getLocation());
       ArrayList<SlideModel> allImages  = new ArrayList<>();
       for(int i = 0; i<allPlaces.get(position).getImageUri().size(); i++){
           String imageUrl = allPlaces.get(position).getImageUri().get(i);
           allImages.add(new SlideModel(imageUrl,null));
       }
       holder.imageSlider.setImageList(allImages);
       holder.itemCardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               itemClickListener.singleItemClick(place);
           }
       });
    }

    @Override
    public int getItemCount() {
        return allPlaces.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView locationShow;
        ImageSlider imageSlider;
        CardView itemCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationShow = itemView.findViewById(R.id.locationshowId);
            imageSlider = itemView.findViewById(R.id.image_slider);
            itemCardView = itemView.findViewById(R.id.itemcardId);
        }
    }
}
