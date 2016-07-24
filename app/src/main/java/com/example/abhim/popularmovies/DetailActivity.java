package com.example.abhim.popularmovies;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by abhim on 7/11/2016.
 */
public class DetailActivity extends AppCompatActivity {

    private int pos;
    private DetailActivityAdapter activityAdapter;
    private DetailClass detailClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pos = extras.getInt("Position");
        }
        new DetailViewAsyncTask().execute();
        activityAdapter = new DetailActivityAdapter(getApplicationContext());
        activityAdapter.notifyDataSetChanged();
    }

    public class DetailViewAsyncTask extends AsyncTask<String, Void, ArrayList<DetailClass>> {

        private final String LOG_TAG = DetailViewAsyncTask.class.getSimpleName();

        private String getReadableDataString(long time) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("EE MM DD");
            return dateFormat.format(time);
        }

        @Override
        protected ArrayList<DetailClass> doInBackground(String... params) {

            //if there's no API no data is collected


            if (params.length == 0) {
                // do nothing
                return null;
            }

            HttpURLConnection connection = null;

            BufferedReader reader = null;

            String moviesStrJson = null;

            String sortByAverage = "vote_average.desc";


            try {
                //Construct the URL for the movies database api.

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String sortBy = preferences.getString(getString(R.string.pref_string_movies),
                        getString(R.string.pref_string_default));
                Uri builtUri = null;
                if (sortBy.equals("Popular Movies")) {

                    final String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?";
                    final String API_KEY = "api_key";

                    builtUri = Uri.parse(POPULAR_MOVIES_URL).buildUpon()
                            .appendQueryParameter(API_KEY, BuildConfig.POPULAR_MOVIES_API_KEY).build();
                } else if (sortBy.equals("Top Rated Movies")) {

                    final String POPULAR_MOVIES_URL = "http://api.themoviedb.org/3/movie/top_rated?";
                    final String API_KEY = "api_key";
                    final String SORT_BY = "sort_by";

                    builtUri = Uri.parse(POPULAR_MOVIES_URL).buildUpon()
                            .appendQueryParameter(API_KEY, BuildConfig.POPULAR_MOVIES_API_KEY)
                            .appendQueryParameter(SORT_BY, sortByAverage)
                            .build();
                }

                URL url = new URL(builtUri.toString());

                //Create the request for the movies API and open the connection.

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                //Read the inputStream in to a string

                InputStream inputStream = connection.getInputStream();

                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    //TODO Nothing
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    //Since it's JSON, adding a new line isn't necessary (it won't effect parsing)
                    //But it does make debugging a *lot* easier if you print out the completed
                    //buffer for debugging.

                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    //Stream was empty. No point in parsing.
                    return null;
                }

                moviesStrJson = buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            try {
                return getMoviesDataFromJson(moviesStrJson);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }


            return null;
        }

        private ArrayList<DetailClass> getMoviesDataFromJson(String moviesStrJson) throws JSONException {

            final String POM_LIST = "results";
            final String POM_BACKDROP_PATH = "backdrop_path";
            final String POM_RELEASE_DATE = "release_date";
            final String POM_TITLE = "original_title";
            final String POM_SYNOPSIS = "overview";
            final String POM_RATING = "vote_average";

            JSONObject moviesJsonObject = new JSONObject(moviesStrJson);
            JSONArray moviesArray = new JSONArray();

            moviesArray = moviesJsonObject.getJSONArray(POM_LIST);
            ArrayList<DetailClass> detailsArray = new ArrayList<>();

            detailClass = new DetailClass();
            for (int i = 0; i < moviesArray.length(); i++) {

                JSONObject moviesObject = moviesArray.getJSONObject(i);
                if (i == pos) {

                    detailClass.setOriginalTitle(moviesObject.getString(POM_TITLE));
                    detailClass.setMovieDate(moviesJsonObject.getInt(POM_RELEASE_DATE));
                    detailClass.setMoviesRating(moviesJsonObject.getDouble(POM_RATING));
                    detailClass.setMovieSynopsis(moviesJsonObject.getString(POM_SYNOPSIS));
                    detailClass.setPosterImage("http://image.tmdb.org/t/p/w185" + moviesJsonObject.getString(POM_BACKDROP_PATH));

                    detailsArray.add(detailClass);
                } else {
                    return null;
                }
            }

            return detailsArray;
        }

        @Override
        protected void onPostExecute(ArrayList<DetailClass> detailClasses) {

            DetailActivityAdapter adapter = new DetailActivityAdapter(getApplicationContext());
            adapter.notifyDataSetChanged();
            adapter.clear(detailClasses);

        }
    }
}
