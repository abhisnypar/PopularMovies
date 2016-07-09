package com.example.abhim.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TopRatedMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_movies);
        if (savedInstanceState != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.action_top_rated_activity, new TopRatedFragment())
                    .commit();
        }
    }
}
