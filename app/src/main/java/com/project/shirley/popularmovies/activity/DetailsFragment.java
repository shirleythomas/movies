package com.project.shirley.popularmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.shirley.popularmovies.R;
import com.project.shirley.popularmovies.helper.LeadingMarginHelper;
import com.project.shirley.popularmovies.service.MovieService;
import com.project.shirley.popularmovies.task.LoadReviewsTask;
import com.project.shirley.popularmovies.task.LoadTrailersTask;
import com.project.shirley.popularmovies.vo.Movie;
import com.project.shirley.popularmovies.vo.Review;
import com.project.shirley.popularmovies.widget.ExpandableTextView;
import com.project.shirley.popularmovies.widget.GridViewAdapter;
import com.project.shirley.popularmovies.widget.ListViewAdapter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shirley Thomas on 5/1/2016.
 */
public class DetailsFragment extends Fragment {

    Button favoriteButton;
    Button removeFavoriteButton;
    Context context;
    GridView grid;
    ListView reviewList;
    GridViewAdapter gridViewAdapter;
    ListViewAdapter listViewAdapter;

    Movie movie = null;
    View rootView;
    private final static String MOVIE="Movie";

    public DetailsFragment() {
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        if (savedInstanceState == null) {
            if (this.getArguments() != null) {
                movie = this.getArguments().getParcelable(Movie.KEY_MOVIE);
            }

            if (movie == null) {

                Intent intent = getActivity().getIntent();

                if (intent == null || intent.getParcelableExtra(Movie.KEY_MOVIE) == null) {
                    return rootView;
                }

                movie = (Movie) intent.getParcelableExtra(Movie.KEY_MOVIE);
            }
        } else {
            movie = (Movie) savedInstanceState.getParcelable(MOVIE);
        }

        favoriteButton = (Button) rootView.findViewById(R.id.favoriteButton);
        removeFavoriteButton = (Button) rootView.findViewById(R.id.favoriteButton);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.grid_item_image);

        TextView releaseDateLabel = (TextView) rootView.findViewById(R.id.releaseDateLabel);
        releaseDateLabel.setVisibility(View.VISIBLE);
        TextView releaseDateView = (TextView) rootView.findViewById(R.id.releaseDate);
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(movie.getReleaseDate());
            releaseDateView.setText(new SimpleDateFormat("d MMM, yyyy").format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextView voteAverageLabel = (TextView) rootView.findViewById(R.id.voteAverageLabel);
        voteAverageLabel.setVisibility(View.VISIBLE);
        TextView voteAverageView = (TextView) rootView.findViewById(R.id.voteAverage);
        voteAverageView.setText(movie.getVoteAverage());

        TextView synopsis = (TextView) rootView.findViewById(R.id.synopsis);
        synopsis.setVisibility(View.VISIBLE);
        ExpandableTextView messageView = (ExpandableTextView) rootView.findViewById(R.id.overview);
        messageView.setText(movie.getOverview());
        getActivity().setTitle(movie.getTitle());
        Picasso.with(getActivity()).load(Movie.IMAGE_BASE_URL + movie.getPosterPath()).placeholder(R.drawable.placeholder)
                .error(R.drawable.error).into(imageView);

        Point size = new Point();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(size);
        imageView.measure(size.x, size.y);
        int height = imageView.getMeasuredHeight();
        int width = imageView.getMeasuredWidth() + 2;
        messageView.measure(width, height); //to allow getTotalPaddingTop
        int padding = messageView.getTotalPaddingTop();

        float textLineHeight = messageView.getPaint().getTextSize();
        int lines = (int) Math.round((height - padding) / textLineHeight);

        int leftMargin = imageView.getMeasuredWidth() + 15;

        SpannableString ss = new SpannableString(movie.getOverview());
        ss.setSpan(new LeadingMarginHelper(lines - 5, leftMargin), 0, ss.length(), 0);

        messageView.setText(ss);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) messageView.getLayoutParams();
        int[] rules = params.getRules();
        rules[RelativeLayout.RIGHT_OF] = 0;


        reviewList = (ListView) rootView.findViewById(R.id.listView);
        List<Review> reviews = new ArrayList<Review>();
        listViewAdapter = new ListViewAdapter(getActivity(), R.layout.review_list, reviews);
        reviewList.setAdapter(listViewAdapter);

        grid = (GridView) rootView.findViewById(R.id.gridView);
        List<String> gridData = new ArrayList<String>();
        gridViewAdapter = new GridViewAdapter(getActivity(), R.layout.trailer_list, gridData);
        grid.setAdapter(gridViewAdapter);
        context = getActivity();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent videoClient = new Intent(Intent.ACTION_VIEW);
                videoClient.setData(Uri.parse("http://m.youtube.com/watch?v=" + gridViewAdapter.getGridData().get(arg2)));
                startActivity(videoClient);
            }
        });


        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markFavorite(v);
            }
        });

        new LoadTrailersTask(gridViewAdapter, rootView, context).execute(movie.getId());
        new LoadReviewsTask(listViewAdapter, rootView).execute(movie.getId());
        return rootView;
    }


    public void markFavorite(View view) {

        if ("Mark as Favorite".equals(favoriteButton.getText())) {
            if (MovieService.createFavorite(context, movie) > 0) {
                favoriteButton.setText("Remove from Favorites");
            }
        } else {
            if (MovieService.removeFavorite(context, movie.getId()) > 0) {
                favoriteButton.setText("Mark as Favorite");
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE, movie);
    }


}
