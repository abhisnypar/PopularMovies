package com.example.abhim.popularmovies.rx2;

import com.example.abhim.popularmovies.BuildConfig;
import com.example.abhim.popularmovies.ModelClasses.ResultsResponseBody;
import com.example.abhim.popularmovies.restful.RestServiceController;
import com.example.abhim.popularmovies.restful.RetrofitServiceInterface;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class MoviesAsyncTask {
    private RetrofitServiceInterface retrofitServiceInterface;
    private Retrofit restServiceController;

    public MoviesAsyncTask(){

        restServiceController = RestServiceController.getRestController(BuildConfig.BASE_URL);
        retrofitServiceInterface = restServiceController.create(RetrofitServiceInterface.class);
    }

    public Observable<ResultsResponseBody> getPopularMovies(){
        return retrofitServiceInterface.getPopularMovies(BuildConfig.POPULAR_MOVIES_API_KEY);
    }
}
