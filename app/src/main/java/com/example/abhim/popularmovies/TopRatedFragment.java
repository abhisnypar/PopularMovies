package com.example.abhim.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class TopRatedFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
        return rootView;
    }

//    public class TopRatedAsyncTask extends AsyncTask<String, String, ArrayList<String>> {
//
//        @Override
//        protected ArrayList<String> doInBackground(String... params) {
//
//            return null;
//        }
//    }

}
