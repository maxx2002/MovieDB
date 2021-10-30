package com.example.view.fragment;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.helper.Const;
import com.example.model.Movies;
import com.example.moviedb.R;
import com.example.viewmodel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView details_text_duration, details_text_releasedate, details_text_popularity, details_text_title, details_text_overview, details_text_tagline, details_text_votecount, details_text_voteavg;
    private ImageView details_img_posters, details_img_backdrop, details_img_backk;
    private MovieViewModel viewModel;
    private RatingBar ratingBar;
    private LinearLayout details_layout_logo, details_layout_genre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        details_text_duration = view.findViewById(R.id.details_text_duration);
        details_text_releasedate = view.findViewById(R.id.details_text_releasedate);
        details_text_popularity = view.findViewById(R.id.details_text_popularity);
        details_text_title = view.findViewById(R.id.details_text_title);
        details_text_overview = view.findViewById(R.id.details_text_overview);
        details_text_tagline = view.findViewById(R.id.details_text_tagline);
        details_text_votecount = view.findViewById(R.id.details_text_votecount);
        details_text_voteavg = view.findViewById(R.id.details_text_voteavg);
        details_img_posters = view.findViewById(R.id.details_img_posters);
        details_img_backdrop = view.findViewById(R.id.details_img_backdrop);
        details_img_backk = view.findViewById(R.id.details_img_backk);
        ratingBar = view.findViewById(R.id.ratingBar);
        details_layout_logo = view.findViewById(R.id.details_layout_logo);
        details_layout_genre = view.findViewById(R.id.details_layout_genre);

        String movieId = getArguments().getString("movieId").toString();
        String from = getArguments().getString("from").toString();

        viewModel = new ViewModelProvider(MovieDetailsFragment.this).get(MovieViewModel.class);
        viewModel.getMovieById(movieId);
        viewModel.getResultGetMovieById().observe(getViewLifecycleOwner(), showResultMovie);

        details_img_backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from.equalsIgnoreCase("nowplaying")) {
                    Navigation.findNavController(view).navigate(R.id.action_MovieDetailFragment_to_nowPlayingFragment);
                } else if (from.equalsIgnoreCase("upcoming")) {
                    Navigation.findNavController(view).navigate(R.id.action_MovieDetailFragment_to_upComingFragment);
                } else if (from.equalsIgnoreCase("popular")) {
                    Navigation.findNavController(view).navigate(R.id.action_MovieDetailFragment_to_popularFragment);
                } else if (from.equalsIgnoreCase("toprated")) {
                    Navigation.findNavController(view).navigate(R.id.action_MovieDetailFragment_to_topRatedFragment2);
                }
            }
        });

        return view;
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String title = movies.getTitle();
            String overview = movies.getOverview();
            String date = movies.getRelease_date();
            String runtime = String.valueOf(movies.getRuntime());
            String genre = "";
            String tagline = movies.getTagline();
            String popilarity = String.valueOf(movies.getPopularity());
            String votecount = String.valueOf(movies.getVote_count());
            String votenumber = String.valueOf(movies.getVote_average());
            double voteavg = movies.getVote_average();

            String poster_path = Const.IMG_URL + movies.getPoster_path().toString();
            Glide.with(MovieDetailsFragment.this).load(poster_path).into(details_img_posters);
            String backdrop_path = Const.IMG_URL + movies.getBackdrop_path().toString();
            Glide.with(MovieDetailsFragment.this).load(backdrop_path).into(details_img_backdrop);

            details_text_title.setText(title);
            details_text_releasedate.setText(date);
            details_text_overview.setText(overview);
            details_text_popularity.setText(popilarity);
            details_text_duration.setText(runtime + " minutes");
            details_text_tagline.setText(tagline);
            details_text_votecount.setText("taken from " + votecount + " votes");
            details_text_voteavg.setText(votenumber);
            ratingBar.setRating((float) voteavg);

            //genre
            for (int i = 0; i < movies.getGenres().size(); i++) {
                String genres = movies.getGenres().get(i).getName();
                TextView GenreList = new TextView(details_layout_genre.getContext());

                GenreList.setText(genres);
                GenreList.setTextColor(getResources().getColor(R.color.white));
                GenreList.setBackground(getResources().getDrawable(R.drawable.kapsulgenre));
                GenreList.setPadding(40,10,40,10);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                params.setMargins(10,0,10,0);
                GenreList.setLayoutParams(params);
                details_layout_genre.addView(GenreList);
            }

            //production companies
            for (int i = 0; i < movies.getProduction_companies().size(); i++) {
                String ProductionLogo = Const.IMG_URL + movies.getProduction_companies().get(i).getLogo_path();
                String ProductionName = movies.getProduction_companies().get(i).getName();
                ImageView img = new ImageView(details_layout_logo.getContext());

                if (movies.getProduction_companies().get(i).getLogo_path() == null) {
                    img.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_broken_image_24, getActivity().getTheme()));
                }else if (ProductionLogo != "https://image.tmdb.org/3/t/p/w500/null") {
                    Glide.with(getActivity()).load(ProductionLogo).into(img);
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(270, 270);
                params.setMargins(20,0,20,0);
                img.setLayoutParams(params);
                details_layout_logo.addView(img);

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(getContext(), ProductionName, Toast.LENGTH_SHORT);
                        toast.setText(ProductionName);
                        toast.show();
                    }
                });
            }
        }
    };
}