package com.example.abhim.popularmovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.abhim.popularmovies.DetailClass;
import com.example.abhim.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by abhim on 7/3/2016.
 */
public class GridAdapter extends BaseAdapter {

    public ArrayList<DetailClass> imagesUrlList;
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

        @InjectView(R.id.grid_imageView_id)ImageView imgView;

        public ViewHolder(View v) {

            ButterKnife.inject(this,v);
        }
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View rootView = convertView;
        if (rootView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.grid_list_view, parent, false);
            holder = new ViewHolder(rootView);
            rootView.setTag(holder);
        } else {

            holder = (ViewHolder) rootView.getTag();
        }

        Picasso.with(context).load(imagesUrlList.get(position).getGridImage())
                .placeholder(R.drawable.ramboo).fit()
                .into(holder.imgView);
        return rootView;
    }

    public void clear(ArrayList<DetailClass> imagesUrlList) {
        this.imagesUrlList.clear();
        this.imagesUrlList.addAll(imagesUrlList);
        notifyDataSetChanged();
    }
}
