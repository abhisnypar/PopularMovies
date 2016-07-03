package com.example.abhim.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RatingBar;

import java.util.ArrayList;

/**
 * Created by abhim on 7/2/2016.
 */
public class FragmentMain extends Fragment {

    private GridView moviesGridView;
    private RatingBar mUserRating;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mainfragment, container, false);
        moviesGridView = (GridView) rootView.findViewById(R.id.gridList_id);
        mUserRating = (RatingBar)rootView.findViewById(R.id.ratingBar);
        ArrayList<String> gridList = new ArrayList<String>();

        gridList.add("The A team");
        gridList.add("Inception");
        gridList.add("Gone Girl");
        gridList.add("Now you see me");
        gridList.add("Creed");
        gridList.add("Uncle");
        gridList.add("Rambo");
        gridList.add("Movies_empty");
        gridList.add("Movies_empty");
        gridList.add("Movies_empty");
        gridList.add("Movies_empty");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.grid_list_view, R.id.list_item_movies_textView, gridList);
        moviesGridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return rootView;
    }

}
