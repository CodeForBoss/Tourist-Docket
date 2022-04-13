package com.example.touristpark.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.touristpark.R;
import com.example.touristpark.repository.model.Comment;
import com.example.touristpark.view.CommentListener;

import java.util.ArrayList;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {

    private ArrayList<Comment> allComments;
    private CommentListener commentListener;
    private Context context;

    public CommentRecyclerAdapter(ArrayList<Comment> allComments, CommentListener commentListener,Context context) {
        this.allComments = allComments;
        this.commentListener = commentListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Comment comment = allComments.get(position);

           if(comment.getUser().getEmail().equals("ano")){
               holder.userRating.setVisibility(View.GONE);
               holder.imageButton.setVisibility(View.GONE);
           } else {
               holder.imageButton.setVisibility(View.VISIBLE);
               holder.userRating.setVisibility(View.VISIBLE);
               holder.userRating.setRating(comment.getRating());
               Glide.with(context).load(comment.getUser().getProfileImageUri()).into(holder.userProfilePic);
           }
           holder.userComment.setText(comment.getUserComment());
           holder.userName.setText(comment.getUser().getName());
           holder.commentCard.setOnClickListener(view -> commentListener.commentClick(comment));

    }

    @Override
    public int getItemCount() {
        return allComments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
         TextView userName,userComment;
         RatingBar userRating;
         ImageView userProfilePic;
         CardView commentCard;
         ImageButton imageButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.showUserNameId);
            userComment = itemView.findViewById(R.id.showcommentId3);
            userRating = itemView.findViewById(R.id.showRatingId);
            userProfilePic = itemView.findViewById(R.id.showProfilePicId);
            commentCard = itemView.findViewById(R.id.commentCardId);
            imageButton = itemView.findViewById(R.id.verifyIconId);
        }
    }
}
