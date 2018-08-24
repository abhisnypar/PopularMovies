package com.example.abhim.popularmovies.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.example.abhim.popularmovies.Activities.MainActivity;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class TopRatedFragment extends Fragment implements Callback<ResultsResponseBody> {
    @InjectView(R.id.action_top_rated_grid_view)
    GridView moviesGridView;
    private GridAdapter moviesGridAdapter;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;
    private ArrayList<DetailClass> detailClassObject;
    private MovieInfoViewModel movieInfoViewModel;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
        moviesGridAdapter = new GridAdapter(getContext());
        movieInfoViewModel = ViewModelProviders.of(this).get(MovieInfoViewModel.class);

        if (NetworkUtil.isNetWorkConnected(getContext())) {
            ButterKnife.inject(this, rootView);

            final Retrofit restServiceController = RestServiceController.getRestController(BuildConfig.BASE_URL);
            final RetrofitServiceInterface retrofitServiceInterface = restServiceController.create(RetrofitServiceInterface.class);
            final Call<ResultsResponseBody> resultsResponseBodyCall = retrofitServiceInterface.getTopRatedMovies(BuildConfig.POPULAR_MOVIES_API_KEY, "vote_Average.desc");
            resultsResponseBodyCall.enqueue(this);
            mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
            mEditor = mSettings.edit();
            mEditor.apply();
            setHasOptionsMenu(true);
        } else {
            NetworkUtil.displayAlertDialog(getContext());
        }
        intent = getActivity().getIntent();
        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        /*
        This menu item checked can be set to checked with out the intent also.
        This is purely a showcase of using intent to send extras and creating a bundle to receive the
        extras.
         */
        Bundle bundle = intent.getExtras();
        String menuString = bundle.getString("Menu Item");
        if (menuString != null && menuString.equals("Top Rated Movies")) {
            menu.getItem(1).setChecked(true);
        }

        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        Swipe refesh layout will add refresh on the screen.
         */
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_top_rated);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular_movies:
                final Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("Menu Item", item.getTitle());
                startActivity(intent);
                item.setChecked(true);
                break;
            case R.id.action_top_rated:
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
