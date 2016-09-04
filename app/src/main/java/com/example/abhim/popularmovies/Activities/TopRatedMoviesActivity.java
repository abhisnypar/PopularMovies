package com.example.abhim.popularmovies.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.abhim.popularmovies.R;
import com.example.abhim.popularmovies.TopRatedFragment;

public class TopRatedMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_movies);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.action_top_rated_activity, new TopRatedFragment())
                    .commit();
        }
    }
}
