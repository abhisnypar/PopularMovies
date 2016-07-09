package com.example.abhim.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class TopRatedFragment extends Fragment {
    private GridView moviesGridView;
    private GridAdapter moviesGridAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
        moviesGridView = (GridView) rootView.findViewById(R.id.action_top_rated_grid_view);
        new TopRatedMoviesAsyncTask().execute();
        moviesGridAdapter = new GridAdapter(getContext());
        moviesGridView.setAdapter(moviesGridAdapter);
        setHasOptionsMenu(true);
        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_popular_movies) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public class TopRatedMoviesAsyncTask extends AsyncTask<String, String, ArrayList<String>> {

        private final String LOG_TAG = TopRatedFragment.class.getSimpleName();

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            //Initialize the network connection using #Http
            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;

            String topRatedJsonStr = null;
            String sortByAverage = "vote_average.desc";

            try {

                final String TOP_RATED_MOVIES_URL = "http://api.themoviedb.org/3/movie/top_rated?";
                final String API_KEY = "api_key";
                final String SORT_BY = "sort_by";

                Uri builtUri = Uri.parse(TOP_RATED_MOVIES_URL).buildUpon()
                        .appendQueryParameter(API_KEY, BuildConfig.POPULAR_MOVIES_API_KEY)
                        .appendQueryParameter(SORT_BY, sortByAverage)
                        .build();
                URL url = new URL(builtUri.toString());
                //Create the request to TheMoviesDB for movies by averageSortOrder.

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    //Nothing to do
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    //Since it's JSON, adding a new line isn't necessary(it won't effect parsing)
                    //But it does make debugging a  *lot* easier if you print out the completed
                    //buffer for debugging

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    //Stream was empty. No point in parsing.
                    return null;

                }

                topRatedJsonStr = buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
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
                return getTopRatedMoviesFromJson(topRatedJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        private ArrayList<String> getTopRatedMoviesFromJson(String topRatedJsonStr) throws JSONException {

            final String POM_LIST = "results";
            final String POM_POSTER_PATH = "poster_path";

            JSONObject movesObject = new JSONObject(topRatedJsonStr);
            JSONArray moviesArray = movesObject.getJSONArray(POM_LIST);

            ArrayList<String> url = new ArrayList<>();

            String[] resultStr = new String[moviesArray.length() - 1];

            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject topRatedMovies = moviesArray.getJSONObject(i);
                url.add("http://image.tmdb.org/t/p/w185" + topRatedMovies.getString(POM_POSTER_PATH));
            }

            for (String s : resultStr) {
                Log.v(LOG_TAG, "TopRatedMovies: " + s);
            }
            return url;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if (result != null) {
                moviesGridAdapter.clear(result);
            }
            super.onPostExecute(result);
        }
    }

}
