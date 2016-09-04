package com.example.abhim.popularmovies.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abhim on 8/13/2016.
 */
public class PopularMoviesApiClient  {
/*
    To send network requests to an API, we are using this as a Retrofit Builder class and
    specify the base URL for the service.
 */
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public static Retrofit  getClient(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
