package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.helper.Const;
import com.example.model.Credits;
import com.example.moviedb.R;

import java.util.List;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.CardViewViewHolder> {

    private Context context;
    private List<Credits.Cast> CreditsList;
    private List<Credits.Cast> getCreditsList()
    {
        return CreditsList;
    }
    public void setCreditsList(List<Credits.Cast> CreditsList) {
        this.CreditsList = CreditsList;
    }
    public CreditsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CreditsAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cast, parent, false);
        return new CreditsAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditsAdapter.CardViewViewHolder holder, int position) {
        final Credits.Cast results = getCreditsList().get(position);
        holder.cast_text_original.setText(results.getOriginal_name());
        holder.cast_text_character.setText(results.getCharacter());

        if (results.getProfile_path() == null) {
            holder.cast_img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_broken_image_24));
        } else {
            Glide.with(context)
                    .load(Const.IMG_URL + results.getProfile_path()).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(holder.cast_img);
        }
    }

    @Override
    public int getItemCount() {
        return getCreditsList().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView cast_img;
        TextView cast_text_original, cast_text_character;
        CardView cv_cast;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            cv_cast = itemView.findViewById(R.id.cv_cast);
            cast_img = itemView.findViewById(R.id.cast_img);
            cast_text_original = itemView.findViewById(R.id.cast_text_original);
            cast_text_character = itemView.findViewById(R.id.cast_text_character);

        }
    }
}
