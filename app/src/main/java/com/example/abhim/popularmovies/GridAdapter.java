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
 * Created by abhim on 7/3/2016.
 */
public class GridAdapter extends BaseAdapter {

    public ArrayList<String> imagesUrlList;
    Context context;

    public GridAdapter(Context c) {
        context = c;
        imagesUrlList = new ArrayList<>();
        Collections.addAll(imagesUrlList);
    }

    @Override
    public int getCount() {
        return imagesUrlList.size();
    }

    @Override
    public Object getItem(int position) {
        return imagesUrlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        private TextView mText;
        private ImageView imgView;

        public ViewHolder(View v) {
            imgView = (ImageView) v.findViewById(R.id.grid_imageView_id);
        }
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View rootView = convertView;
        final String temp = imagesUrlList.get(position);
        if (rootView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.grid_list_view, parent, false);
            holder = new ViewHolder(rootView);
            rootView.setTag(holder);
        } else {

            holder = (ViewHolder) rootView.getTag();
        }

        Picasso.with(context).load("https://api.themoviedb.org/3/movie/popular?api_key=1f19c0f420eab0495d79f856d09c9a29")
                .placeholder(R.drawable.inception)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgView);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return rootView;
    }

    public void clear(ArrayList<String> imagesUrlList) {
        this.imagesUrlList.clear();
        this.imagesUrlList.addAll(imagesUrlList);
        notifyDataSetChanged();
    }
}
