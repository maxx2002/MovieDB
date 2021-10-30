package com.example.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.helper.Const;
import com.example.model.Movies;
import com.example.moviedb.R;
import com.example.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private TextView details_text_judul, details_text_date, details_text_subtitle, details_text_genre, details_text_runtime;
    private ImageView details_img_poster, details_img_back;
    private String movie_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        details_text_judul = findViewById(R.id.details_text_judul);
        details_text_date = findViewById(R.id.details_text_date);
        details_text_subtitle = findViewById(R.id.details_text_subtitle);
        details_text_genre = findViewById(R.id.details_text_genre);
        details_text_runtime = findViewById(R.id.details_text_duration);
        details_img_poster = findViewById(R.id.details_img_poster);
        details_img_back = findViewById(R.id.details_img_backto);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");

        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showResultMovie);

        details_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String title = movies.getTitle();
            String subtitle = movies.getOverview();
            String date = movies.getRelease_date();
            String runtime = String.valueOf(movies.getRuntime());
            String genre = "";

            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size()-1) {
                    genre += movies.getGenres().get(i).getName();
                } else {
                    genre += movies.getGenres().get(i).getName()+", ";
                }
            }

            String img_path = Const.IMG_URL + movies.getPoster_path().toString();
            Glide.with(MovieDetailsActivity.this).load(img_path).into(details_img_poster);
            details_text_judul.setText(title);
            details_text_date.setText(date);
            details_text_subtitle.setText(subtitle);
            details_text_genre.setText(genre);
            details_text_runtime.setText(runtime + " minutes");
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }
}