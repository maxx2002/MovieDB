package com.example.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.model.Credits;
import com.example.model.Movies;
import com.example.model.NowPlaying;
import com.example.model.Popular;
import com.example.model.Reviews;
import com.example.model.TopRated;
import com.example.model.UpComing;
import com.example.repositories.MovieRepository;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository repository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = MovieRepository.getInstance();
    }

    // view model get movie
    private MutableLiveData<Movies> resultGetMovieById = new MutableLiveData<>();
    public void getMovieById(String movieId) {
        resultGetMovieById = repository.getMovieData(movieId);
    }
    public LiveData<Movies> getResultGetMovieById() {
        return resultGetMovieById;
    }

    // view model get now playing
    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();
    public void getNowPlaying(Integer page) {
        resultGetNowPlaying = repository.getNowPlayingData(page);
    }
    public LiveData<NowPlaying> getResultGetNowPlaying() {
        return resultGetNowPlaying;
    }

    // view model get upcoming
    private MutableLiveData<UpComing> resultGetUpComing = new MutableLiveData<>();
    public void getUpComing(Integer page) {
        resultGetUpComing = repository.getUpComingData(page);
    }
    public LiveData<UpComing> getResultGetUpComing() {
        return resultGetUpComing;
    }

    // view model get popular
    private MutableLiveData<Popular> resultGetPopular = new MutableLiveData<>();
    public void getPopular(Integer page) {
        resultGetPopular = repository.getPopularData(page);
    }
    public LiveData<Popular> getResultGetPopular() {
        return resultGetPopular;
    }

    // view model get top rated
    private MutableLiveData<TopRated> resultGetTopRated = new MutableLiveData<>();
    public void getTopRated(Integer page) {
        resultGetTopRated = repository.getTopRatedData(page);
    }
    public LiveData<TopRated> getResultGetTopRated() {
        return resultGetTopRated;
    }

    // view model get cast
    private MutableLiveData<Credits> resultGetCredits = new MutableLiveData<>();
    public void getCredits(String movieId) {
        resultGetCredits = repository.getCreditsData(movieId);
    }
    public LiveData<Credits> getResultGetCredits() {
        return resultGetCredits;
    }

    // view model get cast
    private MutableLiveData<Reviews> resultGetReviews = new MutableLiveData<>();
    public void getReviews(String movieId) {
        resultGetReviews = repository.getReviewsData(movieId);
    }
    public LiveData<Reviews> getResultGetReviews() {
        return resultGetReviews;
    }
}
