package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Reviews;
import com.example.moviedb.R;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.CardViewViewHolder> {

    private Context context;
    private List<Reviews.Results> ReviewsList;
    private List<Reviews.Results> getReviewsList()
    {
        return ReviewsList;
    }
    public void setReviewsList(List<Reviews.Results> ReviewsList) {
        this.ReviewsList = ReviewsList;
    }
    public ReviewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewsAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reviews, parent, false);
        return new ReviewsAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.CardViewViewHolder holder, int position) {
        final Reviews.Results results = getReviewsList().get(position);
        holder.reviews_author.setText(results.getAuthor());
        holder.reviews_content.setText(results.getContent());
    }

    @Override
    public int getItemCount() {
        return getReviewsList().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {

        TextView reviews_content, reviews_author;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            reviews_author = itemView.findViewById(R.id.reviews_author);
            reviews_content = itemView.findViewById(R.id.reviews_content);

        }
    }
}
