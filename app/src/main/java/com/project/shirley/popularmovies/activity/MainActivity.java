package com.project.shirley.popularmovies.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.project.shirley.popularmovies.R;

public class MainActivity extends AppCompatActivity {
    private static final String DETAIL_FRAGMENT_TAG = "DFTAG";
    DetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            if (findViewById(R.id.detail_container) != null) {
                detailsFragment = new DetailsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, detailsFragment, DETAIL_FRAGMENT_TAG)
                        .commit();
            }
        }
    }

    public void markFavorite(View view) {
        detailsFragment.markFavorite(view);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
