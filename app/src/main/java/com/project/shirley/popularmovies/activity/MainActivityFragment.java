package com.project.shirley.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.project.shirley.popularmovies.R;
import com.project.shirley.popularmovies.task.LoadMoviePostersTask;
import com.project.shirley.popularmovies.vo.Movie;
import com.project.shirley.popularmovies.widget.GridViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    GridView grid;
    GridViewAdapter gridViewAdapter;
    List<Movie> gridData = new ArrayList<Movie>();
    boolean isDataLoaded = false;
    DetailsFragment newFragment;
    private final static String GRID_DATA = "GridData";


    int previous = 0;
    int current = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        grid = (GridView) rootView.findViewById(R.id.gridView);

        gridViewAdapter = new GridViewAdapter(getActivity(), R.layout.movie_list, gridData);
        grid.setAdapter(gridViewAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //Pass the image title and url to DetailsActivity
                if (getActivity().findViewById(R.id.detail_container) == null) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putParcelable(Movie.KEY_MOVIE, (Movie) gridViewAdapter.getGridData().get(arg2));
                    intent.putExtras(mBundle);

                    //Start details activity
                    startActivity(intent);
                } else {

                    Movie movie = (Movie) gridViewAdapter.getGridData().get(arg2);

                    previous = current;

                    current = movie.getId();
                    if (current != previous) {
                        newFragment = new DetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Movie.KEY_MOVIE, movie);
                        newFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack
                        transaction.replace(R.id.detail_container, newFragment, newFragment.toString());
                        transaction.addToBackStack(newFragment.toString());

                        // Commit the transaction
                        transaction.commit();
                    }
                }
            }
        });
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(GRID_DATA, (ArrayList<Movie>) gridViewAdapter.getGridData());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            gridData = savedInstanceState.getParcelableArrayList(GRID_DATA);
            gridViewAdapter.setGridData(gridData);
            isDataLoaded = true;
        } else {
            isDataLoaded = false;
            //new LoadMoviePostersTask(getActivity(), gridViewAdapter).execute();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isDataLoaded) {
            new LoadMoviePostersTask(getActivity(), gridViewAdapter).execute();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
