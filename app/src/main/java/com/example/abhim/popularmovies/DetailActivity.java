package com.example.abhim.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by abhim on 7/11/2016.
 */
public class DetailActivity extends AppCompatActivity {

    private int pos;
    private DetailActivityAdapter activityAdapter;
    private DetailClass detailClass;
    private TextView titleTextView;
    private TextView synopsisTextView;
    private ImageView posterImageView;
    private TextView releaseDateTextView;
    private TextView ratingTextView;
    private String title;
    private int image;
    private String synopsis;
    private String date;
    private String release_date;
    private String rating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        titleTextView = (TextView) findViewById(R.id.title_TextView);
        synopsisTextView = (TextView) findViewById(R.id.synopsis_textView);
        releaseDateTextView = (TextView) findViewById(R.id.releaseDate_textView);
        ratingTextView = (TextView) findViewById(R.id.rating_textView);
        posterImageView = (ImageView) findViewById(R.id.detail_imageView);
        activityAdapter = new DetailActivityAdapter(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("Title");
            image = (int) extras.get("Image");
            synopsis = extras.getString("Synopsis");
            rating = extras.getString("Rating");
            release_date = extras.getString("Date");
        }
        titleTextView.setText(title);
        synopsisTextView.setText(synopsis);
        ratingTextView.setText(rating);
        releaseDateTextView.setText(date);
    }

}
