package com.example.abhim.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by abhim on 7/2/2016.
 */
public class MoviesFragment extends Fragment {

    private GridView moviesGridView;
    private GridAdapter moviesGridAdapter;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;
    private String originalTitle;
    private String movieSynopsis;
    private String movieDate;
    private double moviesRating;
    private String posterImage;
    private ArrayList<DetailClass> detailClass;
    private DetailClass detailClassObject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mainfragment, container, false);
        moviesGridView = (GridView) rootView.findViewById(R.id.gridList_id);
        moviesGridAdapter = new GridAdapter(getContext());
        new PopularMoviesAsynTask().execute();
        moviesGridView.setAdapter(moviesGridAdapter);
        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSettings.edit();
        mEditor.apply();
        setHasOptionsMenu(true);
        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                originalTitle = detailClassObject.getOriginalTitle();
                movieSynopsis = detailClassObject.getMovieSynopsis();
                movieDate = detailClassObject.getMovieDate();
                moviesRating = detailClassObject.getMoviesRating();
                posterImage = detailClassObject.getPosterImage();
                Intent i = new Intent(view.getContext(), DetailActivity.class);
                i.putExtra("Title", originalTitle);
                i.putExtra("Synopsis", movieSynopsis);
                i.putExtra("Date", movieDate);
                i.putExtra("Rating", moviesRating);
                i.putExtra("Image", posterImage);
                startActivity(i);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.action_top_rated);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_top_rated) {
            Intent intent = new Intent(getContext(), TopRatedMoviesActivity.class);
            startActivity(intent);
            if (item.isChecked()) {
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public class PopularMoviesAsynTask extends AsyncTask<String, String, ArrayList<DetailClass>> {

        private final String LOG_TAG = PopularMoviesAsynTask.class.getSimpleName();


        @Override
        protected ArrayList<DetailClass> doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJsonStr = null;


            try {

                final String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?";
                final String API_KEY = "api_key";

                Uri builtUri = Uri.parse(POPULAR_MOVIES_URL).buildUpon()
                        .appendQueryParameter(API_KEY, BuildConfig.POPULAR_MOVIES_API_KEY).build();

                URL url = new URL(builtUri.toString());
                //Create the request to TheMoviesDB API, and open the connection

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    //Nothing to do
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    //Since it's JSON, adding a newline isn't necessary (it won't effect parsing)
                    //But it does make debugging a *lot* easier if you print out the completed
                    //buffer for debugging
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    //Stream was empty. No point in parsing.
                    return null;

                }
                moviesJsonStr = buffer.toString();

            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }
            try {
                return getPopularMoviesDataFromJson(moviesJsonStr);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        private ArrayList<DetailClass> getPopularMoviesDataFromJson(String moviesJsonStr) throws JSONException {
            //These are the names of the JSON object that need to be extracted.

            final String POM_LIST = "results";
            final String POM_POSTER_PATH = "poster_path";
            final String POM_TITLE = "original_title";
            final String POM_BACKDROP_PATH = "backdrop_path";
            final String POM_RATING = "vote_average";
            final String POM_DATE = "release_date";
            final String POM_SYNOPSIS = "overview";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(POM_LIST);
//            ArrayList<String> urls = new ArrayList<>();
            String imageUrl = "http://image.tmdb.org/t/p/w185";
            detailClass = new ArrayList<>();


            String[] resultStr = new String[moviesArray.length() - 1];

            Time datTime = new Time();
            datTime.setToNow();
            detailClassObject = new DetailClass();

            for (int i = 0; i < moviesArray.length(); i++) {

                JSONObject popularMovies = moviesArray.getJSONObject(i);
                detailClassObject.setOriginalTitle(popularMovies.getString(POM_TITLE));
                detailClassObject.setMovieSynopsis(popularMovies.getString(POM_SYNOPSIS));
                detailClassObject.setMovieDate(popularMovies.getString(POM_DATE));
                detailClassObject.setMoviesRating(popularMovies.getDouble(POM_RATING));
                detailClassObject.setPosterImage((imageUrl + popularMovies.getString(POM_BACKDROP_PATH)));
                detailClassObject.setGridImage((imageUrl+popularMovies.getString(POM_POSTER_PATH)));
//                urls.add(imageUrl + popularMovies.getString(POM_POSTER_PATH));
                detailClass.add(detailClassObject);

            }
            for (String s : resultStr) {
                Log.v(LOG_TAG, "Forecast entry: " + s);
            }
            return detailClass;
        }

        @Override
        protected void onPostExecute(ArrayList<DetailClass> result) {

            if (result != null) {
                moviesGridAdapter.clear(result);
            }
        }
    }
}
