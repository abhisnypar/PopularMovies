package com.example.abhim.popularmovies.restful;

import com.example.abhim.popularmovies.ModelClasses.ResultsResponseBody;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitServiceInterface {

    @GET("movie/popular")
    Single<ResultsResponseBody> getPopularMoviesApi(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<ResultsResponseBody> getTopRatedMovies(@Query("api_key") String api_key, @Query("sort_by") String vote_average);
}
