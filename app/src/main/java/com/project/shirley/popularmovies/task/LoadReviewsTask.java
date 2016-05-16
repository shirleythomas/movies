package com.project.shirley.popularmovies.task;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.shirley.popularmovies.R;
import com.project.shirley.popularmovies.service.MovieService;
import com.project.shirley.popularmovies.vo.Review;
import com.project.shirley.popularmovies.widget.ListViewAdapter;

import java.util.List;

/**
 * Created by shirthom on 5/1/2016.
 */
public class LoadReviewsTask extends AsyncTask<Integer, Void, Boolean> {

    List<Review> reviews;
    private ListViewAdapter listViewAdapter;
    private View rootView;

    public LoadReviewsTask(ListViewAdapter listViewAdapter, View rootView){
        this.listViewAdapter = listViewAdapter;
        this.rootView =rootView;
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        reviews = MovieService.getMovieReviews(params[0]);
        return true;
    }

    // Sets the Bitmap returned by doInBackground
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (!reviews.isEmpty()) {
            listViewAdapter.setListData(reviews);
            View horizontalbar1 = (View)rootView.findViewById(R.id.horizontalbar1);
            horizontalbar1.setVisibility(View.VISIBLE);
            TextView reviewTitle = (TextView)rootView.findViewById(R.id.review);
            reviewTitle.setVisibility(View.VISIBLE);
            GridView trailersGrid = (GridView)rootView.findViewById(R.id.gridView);
            ViewGroup.LayoutParams layoutParams = trailersGrid.getLayoutParams();
            layoutParams.height = convertDpToPixels(110,rootView.getContext()); //this is in pixels
            trailersGrid.setLayoutParams(layoutParams);
        }
    }

    public static int convertDpToPixels(float dp, Context context){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

}
