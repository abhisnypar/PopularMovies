package com.example.abhim.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by abhim on 7/2/2016.
 */
public class MoviesFragment extends Fragment {

    private GridView moviesGridView;
    private GridAdapter moviesGridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mainfragment, container, false);
        moviesGridView = (GridView) rootView.findViewById(R.id.gridList_id);
        moviesGridAdapter = new GridAdapter(getContext());
        new PopularMoviesAsynTask().execute();
        moviesGridView.setAdapter(moviesGridAdapter);
        return rootView;
    }

    public class PopularMoviesAsynTask extends AsyncTask<String, String, ArrayList<String>> {


        private final String LOG_TAG = PopularMoviesAsynTask.class.getSimpleName();

        public String getReadableDataString(long time) {
            //API return Unique time.
            //it must be converted in to milliseconds
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE MM DD");
            return simpleDateFormat.format(time);
        }


        @Override
        protected ArrayList<String> doInBackground(String... params) {

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

        private ArrayList<String> getPopularMoviesDataFromJson(String moviesJsonStr) throws JSONException {
            //These are the names of the JSON object that need to be extracted.

            final String POM_LIST = "results";
            final String POM_POSTER_PATH = "poster_path";
            final String POM_RELEASE_DATE = "release_date";
            final String POM_TITLE = "original_title";
            final String POM_SYNOPSIS = "overview";
            final String POM_RATING = "vote_average";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(POM_LIST);
            ArrayList<String> urls = new ArrayList<>();


            String[] resultStr = new String[moviesArray.length() - 1];

            Time datTime = new Time();
            datTime.setToNow();

            for (int i = 0; i < moviesArray.length(); i++) {

                JSONObject popularMovies = moviesArray.getJSONObject(i);
                urls.add("http://image.tmdb.org/t/p/w185" + popularMovies.getString(POM_POSTER_PATH));


            }
            for (String s : resultStr) {
                Log.v(LOG_TAG, "Forecast entry: " + s);
            }
            return urls;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {

            if (result != null) {
                moviesGridAdapter.clear(result);
            }
        }
    }
}
