package com.example.abhim.popularmovies.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.abhim.popularmovies.DetailClass;
import com.example.abhim.popularmovies.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by abhim on 7/11/2016.
 */
public class DetailActivity extends AppCompatActivity {

    private int pos;
    private DetailClass detailClass;

    @InjectView(R.id.title_TextView) TextView titleTextView;
    @InjectView(R.id.synopsis_textView) TextView synopsisTextView;
    @InjectView(R.id.releaseDate_textView) TextView releaseDateTextView;
    @InjectView(R.id.ratingBar) RatingBar ratingBarView;
    @InjectView(R.id.detail_imageView) ImageView posterImageView;
    private String title;
    private String image;
    private String synopsis;
    private String release_date;
    private double rating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.inject(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pos = extras.getInt("Position");
            title = extras.getString("Title");
            image = extras.getString("Image");
            synopsis = extras.getString("Synopsis");
            rating = extras.getDouble("Rating");
            release_date = extras.getString("Date");
            
        }
        titleTextView.setText(title);
        synopsisTextView.setText(synopsis);
        ratingBarView.setRating((float) rating);
        ratingBarView.setStepSize((float) rating);
        releaseDateTextView.setText(release_date);
        Picasso.with(getApplicationContext()).load(image)
                .fit().into(posterImageView);
    }

}
