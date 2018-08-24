package com.example.abhim.popularmovies.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.abhim.popularmovies.Activities.TopRatedMoviesActivity;
import com.example.abhim.popularmovies.Adapter.GridAdapter;
import com.example.abhim.popularmovies.BuildConfig;
import com.example.abhim.popularmovies.ModelClasses.DetailClass;
import com.example.abhim.popularmovies.ModelClasses.ResultsResponseBody;
import com.example.abhim.popularmovies.R;
import com.example.abhim.popularmovies.restful.RestServiceController;
import com.example.abhim.popularmovies.restful.RetrofitServiceInterface;
import com.example.abhim.popularmovies.viewmodel.MovieInfoViewModel;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MoviesFragment extends Fragment implements Callback<ResultsResponseBody> {

    @InjectView(R.id.gridList_id)
    GridView moviesGridView;
    private GridAdapter moviesGridAdapter;
    private ArrayList<DetailClass> detailClassObject;
    private MovieInfoViewModel movieInfoViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mainfragment, container, false);
        moviesGridAdapter = new GridAdapter(getContext());
        ButterKnife.inject(this, rootView);

        movieInfoViewModel = ViewModelProviders.of(this).get(MovieInfoViewModel.class);
        if (NetworkUtil.isNetWorkConnected(getContext())) {

            Retrofit restServiceController = RestServiceController.getRestController(BuildConfig.BASE_URL);
            final RetrofitServiceInterface retrofitServiceInterface = restServiceController.create(RetrofitServiceInterface.class);
            final Call<ResultsResponseBody> detailClassCall = retrofitServiceInterface.getPopularMovies(BuildConfig.POPULAR_MOVIES_API_KEY);
            detailClassCall.enqueue(this);

            final SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor mEditor = mSettings.edit();
            mEditor.apply();
            setHasOptionsMenu(true);
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
        final SwipeRefreshLayout swipeRefreshLayout = getActivity().findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesGridAdapter.setData(movieInfoViewModel);
                moviesGridAdapter.notifyDataSetChanged();

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

    @Override
    public void onResponse(Call<ResultsResponseBody> call, Response<ResultsResponseBody> response) {
        detailClassObject = response.body().getDetailClass();
        movieInfoViewModel.setDetailClass(detailClassObject);
        moviesGridAdapter.setData(movieInfoViewModel);
        moviesGridView.setAdapter(moviesGridAdapter);
    }

    @Override
    public void onFailure(Call<ResultsResponseBody> call, Throwable t) {
        Log.d("tag", t.getMessage());
    }
}
