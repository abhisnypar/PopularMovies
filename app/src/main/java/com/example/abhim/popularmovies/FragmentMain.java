package com.example.abhim.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by abhim on 7/2/2016.
 */
public class FragmentMain extends Fragment {

    private GridView moviesGridView;
    private ArrayList<String> moviesProgram;
    String[] mNames = {"A team", "Inception", "GoneGirl", "Now you see me", "Creed", "Uncle", "Ramboo"};
    int[] imgList = {R.drawable.ateam, R.drawable.inception, R.drawable.gone_girl, R.drawable.nowyouseeme,
            R.drawable.creed, R.drawable.uncle, R.drawable.ramboo};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mainfragment, container, false);
        moviesGridView = (GridView) rootView.findViewById(R.id.gridList_id);
        GridAdapter adapter = new GridAdapter(getContext());
        moviesGridView.setAdapter(adapter);
        return rootView;

        
    }

}
