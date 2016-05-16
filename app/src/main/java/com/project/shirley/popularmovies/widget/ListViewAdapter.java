package com.project.shirley.popularmovies.widget;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.shirley.popularmovies.R;
import com.project.shirley.popularmovies.vo.Movie;
import com.project.shirley.popularmovies.vo.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class to load the movie posters from the url to the grid.
 * Referenced from http://javatechig.com/android/download-and-display-image-in-android-gridview
 */

public class ListViewAdapter extends ArrayAdapter<Review> {

    private Context context;
    private int layoutResourceId;
    private List reviews = new ArrayList<Review>();

    public ListViewAdapter(Context context, int layoutResourceId, List reviews) {
        super(context, layoutResourceId, reviews);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.reviews = reviews;
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param listData
     */
    public void setListData(List listData) {
        this.reviews.clear();
        this.reviews.addAll(listData);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.author = (TextView) row.findViewById(R.id.author);
            holder.content = (ExpandableTextView) row.findViewById(R.id.content);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Review review = (Review)reviews.get(position);


        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());

        //makeTextViewResizable(holder.content, 3, "More", true);


        return row;
    }

    static class ViewHolder {
        TextView author;
        ExpandableTextView content;
    }



}