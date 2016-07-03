package com.example.abhim.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by abhim on 7/3/2016.
 */
public class GridAdapter extends BaseAdapter {

    ArrayList<SingleRow> list;
    Context context;

    public GridAdapter(Context c) {
        context = c;
        list = new ArrayList<>();
        String[] mNames = {"A team", "Inception", "GoneGirl", "Now you see me", "Creed", "Uncle", "Ramboo"};
        int[] imgList = {R.drawable.ateam, R.drawable.inception, R.drawable.gone_girl, R.drawable.nowyouseeme,
                R.drawable.creed, R.drawable.uncle, R.drawable.ramboo};
        for (int i = 0; i < mNames.length; i++) {
            list.add(new SingleRow(mNames[i], imgList[i]));
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        private TextView mText;
        private ImageView imgView;

        public ViewHolder(View v) {
            mText = (TextView) v.findViewById(R.id.list_item_movies_textView);
            imgView = (ImageView) v.findViewById(R.id.grid_imageView_id);
        }
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        View rootView = convertView;
        final SingleRow temp = list.get(position);
        if (rootView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.grid_list_view, parent, false);
            holder = new ViewHolder(rootView);
            rootView.setTag(holder);
        } else {

            holder = (ViewHolder) rootView.getTag();
        }

        holder.mText.setText(temp.mNames);
        holder.imgView.setImageResource(temp.imgList);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked " + temp.mNames, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}
