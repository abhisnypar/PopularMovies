package com.example.abhim.popularmovies.restful;

import com.example.abhim.popularmovies.ModelClasses.ResultsResponseBody;

public interface MoviesResponseCallBackEmitter {

    void onResponse(ResultsResponseBody resultsResponseBody);

    void onFailure(Throwable throwable);
}
