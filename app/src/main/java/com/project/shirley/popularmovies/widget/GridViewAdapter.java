package com.project.shirley.popularmovies.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.project.shirley.popularmovies.R;
import com.project.shirley.popularmovies.vo.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class to load the movie posters from the url to the grid.
 * Referenced from http://javatechig.com/android/download-and-display-image-in-android-gridview
 */

public class GridViewAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private int layoutResourceId;
    private List gridData = new ArrayList<Movie>();

    public GridViewAdapter(Context context, int layoutResourceId, List gridData) {
        super(context, layoutResourceId, gridData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.gridData = gridData;
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param gridData
     */
    public void setGridData(List gridData) {
        this.gridData.clear();
        this.gridData.addAll(gridData);
        notifyDataSetChanged();
    }


    public List getGridData() {
        return this.gridData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.img);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Object object = gridData.get(position);

        if(object.getClass().equals(Movie.class)){
            Movie movie = (Movie)object;

            Picasso.with(context).load(Movie.IMAGE_BASE_URL + movie.getPosterPath()).placeholder(R.drawable.placeholder)
                    .error(R.drawable.error).into(holder.imageView);
            holder.imageView.setBackgroundResource(R.drawable.shadow);
        }
        else{
            String trailerKey = (String)object;
            Picasso.with(context).load("http://img.youtube.com/vi/"+trailerKey+"/default.jpg").placeholder(R.drawable.trailer_placeholder)
                    .error(R.drawable.trailer_error).into(holder.imageView);
        }
        return row;
    }

    static class ViewHolder {
        ImageView imageView;
    }

}