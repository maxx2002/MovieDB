package com.example.retrofit;

import com.example.model.Credits;
import com.example.model.Movies;
import com.example.model.NowPlaying;
import com.example.model.Popular;
import com.example.model.Reviews;
import com.example.model.TopRated;
import com.example.model.UpComing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {

    @GET("movie/{movie_id}")
    Call<Movies> getMovieById(
        @Path("movie_id") String movieId,
        @Query("api_key") String apiKey
    );

    @GET("movie/now_playing")
    Call<NowPlaying> getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("page") Integer page
    );

    @GET("movie/upcoming")
    Call<UpComing> getUpComing(
            @Query("api_key") String apiKey,
            @Query("page") Integer page
    );

    @GET("movie/popular")
    Call<Popular> getPopular(
            @Query("api_key") String apiKey,
            @Query("page") Integer page
    );

    @GET("movie/top_rated")
    Call<TopRated> getTopRated(
            @Query("api_key") String apiKey,
            @Query("page") Integer page
    );

    @GET("movie/{movie_id}/credits")
    Call<Credits> getCredits(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/reviews")
    Call<Reviews> getReviews(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );
}
