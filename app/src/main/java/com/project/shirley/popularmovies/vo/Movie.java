package com.project.shirley.popularmovies.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shirthom on 3/6/2016.
 */
public class Movie implements Parcelable {
    public static final String KEY_MOVIE = "movie";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    private int id;
    private String title;
    private String poster_path;
    private String overview;
    private String popularity;
    private String vote_count;
    private String vote_avg;
    private String release_date;

    public String getVideoKey() {
        return video_key;
    }

    public void setVideoKey(String video_key) {
        this.video_key = video_key;
    }

    private String video_key;

    public Movie(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVoteCount() {
        return vote_count;
    }

    public void setVoteCount(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getVoteAverage() {
        return vote_avg;
    }

    public void setVoteAverage(String vote_avg) {
        this.vote_avg = vote_avg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(popularity);
        dest.writeString(vote_count);
        dest.writeString(vote_avg);
        dest.writeString(release_date);

    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(Parcel in){
        id=in.readInt();
        title=in.readString();
        poster_path=in.readString();
        overview=in.readString();
        popularity=in.readString();
        vote_avg=in.readString();
        vote_count=in.readString();
        release_date = in.readString();
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }
}
