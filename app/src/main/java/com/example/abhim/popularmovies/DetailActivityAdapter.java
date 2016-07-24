package com.example.abhim.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by abhim on 7/20/2016.
 */
public class DetailActivityAdapter extends BaseAdapter {

    private ArrayList<DetailClass> detailClass;
    Context c;
    private LayoutInflater inflater;

    public DetailActivityAdapter(Context c) {
        super();
        this.c = c;
        detailClass = new ArrayList<>();
        Collections.addAll(detailClass);
    }



    public class Holder {
        private TextView mTitle;
        private TextView mDate;
        private TextView mSynopsis;
        private TextView mRating;
        private ImageView mPosterImage;

        public Holder(View view) {

            mTitle = (TextView) view.findViewById(R.id.title_TextView);
            mDate = (TextView) view.findViewById(R.id.releaseDate_textView);
            mSynopsis = (TextView) view.findViewById(R.id.synopsis_textView);
            mRating = (TextView) view.findViewById(R.id.rating_textView);
            mPosterImage = (ImageView) view.findViewById(R.id.detail_imageView);
        }
    }

    @Override
    public int getCount() {
        return detailClass.size();
    }

    @Override
    public Object getItem(int position) {
        return detailClass.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        View rootView = convertView;
        if (rootView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.detail_activity, parent, false);
           holder  = new Holder(rootView);
            rootView.setTag(holder);
        } else {

            holder = (Holder) rootView.getTag();
        }
        holder.mTitle.setText(detailClass.get(position).getOriginalTitle());
        holder.mRating.setText((int) detailClass.get(position).getMoviesRating());
        holder.mDate.setText(detailClass.get(position).getMovieDate());
        holder.mSynopsis.setText(detailClass.get(position).getMovieSynopsis());

        Picasso.with(c).load(detailClass.get(position)
                .getPosterImage())
                .placeholder(R.drawable.ramboo)
                .fit()
                .into(holder.mPosterImage);

            return null;
        }

    public void clear(ArrayList<DetailClass> detailClass) {
        this.detailClass.clear();
        this.detailClass.addAll(detailClass);
        notifyDataSetChanged();
    }
}
