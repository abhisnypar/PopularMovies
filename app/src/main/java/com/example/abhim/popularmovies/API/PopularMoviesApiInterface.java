package com.example.abhim.popularmovies.API;

import com.example.abhim.popularmovies.BuildConfig;
import com.example.abhim.popularmovies.ModelClasses.ModelClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abhim on 8/13/2016.
 */
public interface PopularMoviesApiInterface {
    @GET("movie/popular")
    Call<ModelClass> getPopularMovies(@Query(BuildConfig.POPULAR_MOVIES_API_KEY)String apiKey);
    /*
        Test Document.
     */
    
}
