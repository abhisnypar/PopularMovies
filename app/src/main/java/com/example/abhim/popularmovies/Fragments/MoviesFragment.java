package com.example.abhim.popularmovies.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.example.abhim.popularmovies.Activities.DetailActivity;
import com.example.abhim.popularmovies.Activities.TopRatedMoviesActivity;
import com.example.abhim.popularmovies.Adapter.GridAdapter;
import com.example.abhim.popularmovies.BuildConfig;
import com.example.abhim.popularmovies.ModelClasses.DetailClass;
import com.example.abhim.popularmovies.R;

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

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by anusha on 6/7/2017.
 */
public class MoviesFragment extends Fragment {

    @InjectView(R.id.gridList_id)
    GridView moviesGridView;
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
        moviesGridAdapter = new GridAdapter(getContext());
        ButterKnife.inject(this, rootView);
        if (NetworkUtil.isNetWorkConnected(getContext())) {
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

                    Intent i = new Intent(view.getContext(), DetailActivity.class);
                    originalTitle = detailClass.get(position).getOriginalTitle();
                    movieSynopsis = detailClass.get(position).getMovieSynopsis();
                    movieDate = detailClass.get(position).getMovieDate();
                    moviesRating = detailClass.get(position).getMoviesRating();
                    posterImage = detailClass.get(position).getPosterImage();
                    i.putExtra("Position", itemPosition);
                    i.putExtra("Title", originalTitle);
                    i.putExtra("Synopsis", movieSynopsis);
                    i.putExtra("Date", movieDate);
                    i.putExtra("Rating", moviesRating);
                    i.putExtra("Image", posterImage);
                    startActivity(i);
                }
            });
        } else {
            NetworkUtil.displayAlertDialog(getContext());
        }
        return rootView;
    }

    /**
     * Shows the menu
     *
     * @param menu     the menu item from the settings banner.
     * @param inflater this will helps to inflate the menu layout.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.action_top_rated);
        menu.getItem(0).setChecked(true);
        super.onPrepareOptionsMenu(menu);
    }

    /**
     * When the fragment is visible and touchable. This method will be called and we set
     * the refresh for the screen.
     */

    @Override
    public void onResume() {
        super.onResume();
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new PopularMoviesAsynTask().execute();
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        moviesGridAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
    }

    /**
     * When the options in the menu is clicked. Calls the Activity that corresponds to the manu item
     * and marks the item that is clicked.
     *
     * @param item is the menu item
     * @return super call.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_popular_movies:
                item.setChecked(true);
                break;
            case R.id.action_top_rated:
                final Intent intent = new Intent(getActivity(), TopRatedMoviesActivity.class);
                intent.putExtra("Menu Item", item.getTitle());
                startActivity(intent);
                item.setChecked(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Asynchronous call to the service to fetch the data from the server
     */
    public class PopularMoviesAsynTask extends AsyncTask<String, String, ArrayList<DetailClass>> {

        private final String LOG_TAG = PopularMoviesAsynTask.class.getSimpleName();

        /**
         * This method does the task in the background.
         *
         * @param params
         * @return detail class object that the data is setted.
         */

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

        /**
         * Parse the json value in to the objects.
         *
         * @param moviesJsonStr is the JSON string from the http request.
         * @return Array list of Detail class.
         * @throws JSONException if there exists any exception while parsing json
         */
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
            String imageUrl = "http://image.tmdb.org/t/p/w185";
            detailClass = new ArrayList<>();


            String[] resultStr = new String[moviesArray.length() - 1];

            Time datTime = new Time();
            datTime.setToNow();


            for (int i = 0; i < moviesArray.length(); i++) {

                JSONObject popularMovies = moviesArray.getJSONObject(i);
                detailClassObject = new DetailClass();
                detailClassObject.setOriginalTitle(popularMovies.getString(POM_TITLE));
                detailClassObject.setMovieSynopsis(popularMovies.getString(POM_SYNOPSIS));
                detailClassObject.setMovieDate(popularMovies.getString(POM_DATE));
                detailClassObject.setMoviesRating(popularMovies.getDouble(POM_RATING));
                detailClassObject.setPosterImage((imageUrl + popularMovies.getString(POM_BACKDROP_PATH)));
                detailClassObject.setGridImage((imageUrl + popularMovies.getString(POM_POSTER_PATH)));
                detailClass.add(detailClassObject);

            }
            for (String s : resultStr) {
                Log.v(LOG_TAG, "Movies Entry: " + s);
            }
            return detailClass;
        }

        /**
         * This will call the UI thread.
         *
         * @param result the result that comes from the do In back ground.
         */
        @Override
        protected void onPostExecute(ArrayList<DetailClass> result) {

            if (result != null) {
                moviesGridAdapter.clear(result);
            }
        }
    }
}
