package com.example.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.helper.Const;
import com.example.model.NowPlaying;
import com.example.model.UpComing;
import com.example.moviedb.R;

import java.util.List;

public class UpComingAdapter extends RecyclerView.Adapter<UpComingAdapter.CardViewViewHolder> {

    private Context context;
    private List<UpComing.Results> listUpComing;

    private List<UpComing.Results> getListUpComing() {return listUpComing;}
    public void setListUpComing(List<UpComing.Results> listUpComing) {
        this.listUpComing = listUpComing;
    }
    public UpComingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UpComingAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_now_playing, parent, false);
        return new UpComingAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingAdapter.CardViewViewHolder holder, int position) {
        final UpComing.Results results = getListUpComing().get(position);
        holder.lbl_title.setText(results.getTitle());
        holder.card_ratingbar.setRating((float) results.getVote_average());
        Glide.with(context).load(Const.IMG_URL + results.getPoster_path()).into(holder.img_poster);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", ""+results.getId());
                bundle.putString("from", "upcoming");
                Navigation.findNavController(view).navigate(R.id.action_upComingFragment_to_MovieDetailFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListUpComing().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView lbl_title;
        CardView cv;
        RatingBar card_ratingbar;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            img_poster = itemView.findViewById(R.id.img_poster_card_nowplaying);
            lbl_title = itemView.findViewById(R.id.title_card_nowplaying);
            card_ratingbar = itemView.findViewById(R.id.card_ratingbar);
            cv = itemView.findViewById(R.id.cv_card_nowplaying);
        }
    }
}
