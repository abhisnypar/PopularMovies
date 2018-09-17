package com.example.abhim.popularmovies.rx2;

import com.example.abhim.popularmovies.restful.MoviesResponseCallBackEmitter;

public class RxSubcriberCallBackEmitter {

    private MoviesResponseCallBackEmitter listener;

    public RxSubcriberCallBackEmitter(MoviesResponseCallBackEmitter listener) {
        this.listener = listener;
    }
}
