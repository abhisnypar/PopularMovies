package com.example.abhim.popularmovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.abhim.popularmovies.ModelClasses.DetailClass;
import com.example.abhim.popularmovies.R;
import com.example.abhim.popularmovies.viewmodel.MovieInfoViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GridAdapter extends BaseAdapter {

    Context context;
    private MovieInfoViewModel movieInfoViewModel;

    public GridAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return movieInfoViewModel.getDetailClass().size();
    }

    @Override
    public Object getItem(int position) {
        return movieInfoViewModel.getDetailClass().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        @InjectView(R.id.grid_imageView_id)
        ImageView imgView;

        public ViewHolder(View v) {

            ButterKnife.inject(this, v);
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

        final String imageUrl = "http://image.tmdb.org/t/p/w185" + movieInfoViewModel.getDetailClass().get(position).getPoster_path();
        Picasso.with(context).load(imageUrl)
                .placeholder(R.drawable.ramboo).fit()
                .into(holder.imgView);
        return rootView;
    }

    public void clear(ArrayList<DetailClass> imagesUrlList) {
//        this.imagesUrlList.clear();
//        this.imagesUrlList.addAll(imagesUrlList);
//        notifyDataSetChanged();
    }

    public void setData(final MovieInfoViewModel movieInfoViewModel) {
        this.movieInfoViewModel = movieInfoViewModel;
    }
}
