package com.project.shirley.popularmovies.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.project.shirley.popularmovies.service.MovieService;
import com.project.shirley.popularmovies.vo.Movie;
import com.project.shirley.popularmovies.widget.GridViewAdapter;

import java.util.List;

/**
 * Created by Shirley Thomas on 5/16/2016.
 */
public class LoadMoviePostersTask extends AsyncTask<Void, Void, List<Movie>> {

    private Context context;
    private GridViewAdapter gridViewAdapter;

    public LoadMoviePostersTask(Context context, GridViewAdapter gridViewAdapter){
        this.context =context;
        this.gridViewAdapter = gridViewAdapter;
    }

    @Override
    protected List<Movie> doInBackground(Void... params) {
        List<Movie> movies = null;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String sortOption = sharedPref.getString("options_list", "1");


        switch (sortOption) {
            case "1":
                movies = MovieService.getPopularMovies();
                break;
            case "2":
                movies = MovieService.getHighestRatingMovies();
                break;
            case "3":
                movies = MovieService.getFavoriteMovies(context);
                break;
        }
        return movies;
    }

    // Sets the List<Movie> returned by doInBackground
    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null && gridViewAdapter != null) {
            gridViewAdapter.setGridData(movies);
        }
    }

}

