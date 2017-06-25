package com.example.abhim.popularmovies.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhim.popularmovies.ModelClasses.DetailClass;
import com.example.abhim.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by anusha on 7/11/2016.
 */
public class DetailActivity extends AppCompatActivity {

    private int pos;
    private DetailClass detailClass;

    @InjectView(R.id.title_TextView)
    TextView titleTextView;
    @InjectView(R.id.synopsis_textView)
    TextView synopsisTextView;
    @InjectView(R.id.releaseDate_textView)
    TextView releaseDateTextView;
    @InjectView(R.id.ratingBar)
    RatingBar ratingBarView;
    @InjectView(R.id.detail_imageView)
    ImageView posterImageView;
    private String title;
    private String image;
    private String synopsis;
    private String release_date;
    private double rating;
    private Uri localBitmap;
    private ShareActionProvider miShareAction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShareActionBar();
        this.getSupportActionBar().setSubtitle("Detail Screen");
        setContentView(R.layout.detail_activity);
        setTheme(R.style.MyActionButtonOverflow);
        ButterKnife.inject(this);
        final Bundle extras = getIntent().getExtras();
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
                .fit().into(posterImageView, new Callback() {
            @Override
            public void onSuccess() {
                shareImage();
            }

            @Override
            public void onError() {
                Toast.makeText(getBaseContext(), "Picasso loading image URL failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void setShareActionBar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.share_icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_menu_details, menu);
        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.share_icon));
        MenuItem item = menu.findItem(R.id.share_movie);
        // Fetch reference to the share action provider
        miShareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share_movie) {
            shareImage();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Share image using intent.
     */
    private void shareImage() {
        Uri bmpUri = getLocalBitmap();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        shareIntent.setType("image/*");
        if (miShareAction != null && shareIntent != null)
            miShareAction.setShareIntent(shareIntent);
    }

    /**
     * Get local bitmap url.
     *
     * @return uri
     */
    public Uri getLocalBitmap() {

        Drawable drawable = posterImageView.getDrawable();
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) posterImageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //store image to default external storage directory.
        return bmpUri;
    }
}
