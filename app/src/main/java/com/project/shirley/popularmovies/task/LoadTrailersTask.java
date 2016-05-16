package com.project.shirley.popularmovies.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.shirley.popularmovies.R;
import com.project.shirley.popularmovies.service.MovieService;
import com.project.shirley.popularmovies.widget.GridViewAdapter;
import com.project.shirley.popularmovies.widget.ListViewAdapter;

import java.util.List;

/**
 * Created by shirthom on 5/16/2016.
 */

public class LoadTrailersTask extends AsyncTask<Integer, Void, Boolean> {

    List<String> videoKeys;
    List<String> list;
    boolean isFavorite;
    private GridViewAdapter gridViewAdapter;
    private View rootView;
    private Context context;

    public LoadTrailersTask(GridViewAdapter gridViewAdapter, View rootView, Context context){
        this.gridViewAdapter = gridViewAdapter;
        this.rootView =rootView;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Integer... params) {


        Log.v("movie.getId", params[0] + "");
        isFavorite = MovieService.isFavorite(context, params[0]);
        videoKeys = MovieService.getMovieTrailers(params[0]);
        return true;
    }

    // Sets the Bitmap returned by doInBackground
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (!videoKeys.isEmpty()) {
            gridViewAdapter.setGridData(videoKeys);
            View horizontalbar = (View)rootView.findViewById(R.id.horizontalbar);
            horizontalbar.setVisibility(View.VISIBLE);
            TextView trailerTitle = (TextView)rootView.findViewById(R.id.trailer);
            trailerTitle.setVisibility(View.VISIBLE);
        }
        if(isFavorite){
            Button favoriteButton  = (Button)rootView.findViewById(R.id.favoriteButton);
            favoriteButton.setText("Remove from Favorites");
            favoriteButton.setVisibility(View.VISIBLE);
        }
    }

}


