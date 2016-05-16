package com.project.shirley.popularmovies.service;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.project.shirley.popularmovies.BuildConfig;
import com.project.shirley.popularmovies.data.MovieContract;
import com.project.shirley.popularmovies.data.MovieOpenHelper;
import com.project.shirley.popularmovies.vo.Movie;
import com.project.shirley.popularmovies.vo.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shirley Thomas on 3/6/2016.
 * To get the movie details from themoviedb API.
 */
public class MovieService {
    private static final String LOG_TAG = MovieService.class.getSimpleName();
    private final static String MOVIE_BASE_URL="https://api.themoviedb.org/3/discover/movie?";
    private final static String MOVIE_BASE_URL_FOR_TRAILER="https://api.themoviedb.org/3/movie/";
    private final static String SORT_BY="sort_by";
    private final static String API_KEY="api_key";

    private static List<Movie> getResponseFromApiCall(Uri uri){

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonString = null;

        try {
            // Construct the URL for the query
            URL url =new URL(uri.toString());

           // Create the request , and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonString = buffer.toString();
            return getMoviesFromJson(movieJsonString);
        } catch (JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            //return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;

    }

    public static List<String> getMovieTrailers(int movieId){

        final String MDB_RESULTS = "results";
        final String MDB_KEY = "key";

        Uri uri = Uri.parse(MOVIE_BASE_URL_FOR_TRAILER+movieId+"/videos").buildUpon()
                .appendQueryParameter(API_KEY, BuildConfig.MOVIE_DB_API_KEY_VAL)
                .build();


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        List<String> keys = new ArrayList<String>();

        // Will contain the raw JSON response as a string.
        String movieJsonString = null;

        try {
            // Construct the URL for the query
            URL url =new URL(uri.toString());

            // Create the request , and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonString = buffer.toString();

            JSONObject movieJson = new JSONObject(movieJsonString);
            JSONArray resultsArray = movieJson.getJSONArray(MDB_RESULTS);

            for(int i = 0; i < resultsArray.length(); i++) {
                JSONObject movieDetail = resultsArray.getJSONObject(i);

                keys.add(movieDetail.getString(MDB_KEY));

            }


        } catch (JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            //return null;
        } catch (Exception e) {
        Log.e(LOG_TAG, "Error ", e);
        //return null;
    } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return keys;
    }

    public static List<Review> getMovieReviews(int movieId){

        final String MDB_RESULTS = "results";
        final String MDB_ID = "id";
        final String MDB_AUTHOR = "author";
        final String MDB_CONTENT = "content";

        Uri uri = Uri.parse(MOVIE_BASE_URL_FOR_TRAILER+movieId+"/reviews").buildUpon()
                .appendQueryParameter(API_KEY, BuildConfig.MOVIE_DB_API_KEY_VAL)
                .build();


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        List<Review> reviews = new ArrayList<Review>();

        // Will contain the raw JSON response as a string.
        String movieJsonString = null;

        try {
            // Construct the URL for the query
            URL url =new URL(uri.toString());

            // Create the request , and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonString = buffer.toString();

            JSONObject movieJson = new JSONObject(movieJsonString);
            JSONArray resultsArray = movieJson.getJSONArray(MDB_RESULTS);

            for(int i = 0; i < resultsArray.length(); i++) {
                JSONObject movieDetail = resultsArray.getJSONObject(i);

                Review review = new Review();
                review.setId(movieDetail.getString(MDB_ID));
                review.setAuthor(movieDetail.getString(MDB_AUTHOR));
                review.setContent(movieDetail.getString(MDB_CONTENT));

                reviews.add(review);

            }


        } catch (JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            //return null;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
            //return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return reviews;
    }

    public static List<Movie> getPopularMovies(){
        Uri uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(SORT_BY,"popularity.desc")
                .appendQueryParameter(API_KEY, BuildConfig.MOVIE_DB_API_KEY_VAL)
                .build();


        return getResponseFromApiCall(uri);
    }

    public static List<Movie> getFavoriteMovies(Context mContext){
        Cursor movieCursor = mContext.getContentResolver().query(MovieContract.FavoritesEntry.CONTENT_URI,new String[]{MovieContract.FavoritesEntry._ID,
        MovieContract.FavoritesEntry.COLUMN_TITLE,
                MovieContract.FavoritesEntry.COLUMN_POSTER_PATH,
                MovieContract.FavoritesEntry.COLUMN_VOTE_AVG,
                MovieContract.FavoritesEntry.COLUMN_OVERVIEW,
                MovieContract.FavoritesEntry.COLUMN_RELEASE_DATE
        },null,null,null);
        List<Movie> movies = new ArrayList<Movie>();
        if (movieCursor != null) {
            movieCursor.moveToFirst();
            for(int i=0; i<movieCursor.getCount(); i++){
                Movie movie = new Movie();
                movie.setId(movieCursor.getInt(0));
                movie.setTitle(movieCursor.getString(1));
                movie.setPosterPath(movieCursor.getString(2));
                movie.setVoteAverage(movieCursor.getString(3));
                movie.setOverview(movieCursor.getString(4));
                movie.setReleaseDate(movieCursor.getString(5));
                movies.add(movie);
                movieCursor.moveToNext();
            }
            movieCursor.close();
        }
        return movies;
    }

    public static long createFavorite(Context mContext, Movie movie){

        ContentValues values = new ContentValues();
        values.put(MovieContract.FavoritesEntry._ID, movie.getId());
        values.put(MovieContract.FavoritesEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MovieContract.FavoritesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(MovieContract.FavoritesEntry.COLUMN_VOTE_AVG, movie.getVoteAverage());
        values.put(MovieContract.FavoritesEntry.COLUMN_OVERVIEW, movie.getOverview());
        values.put(MovieContract.FavoritesEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        Uri favoriteUri =  mContext.getContentResolver().insert(MovieContract.FavoritesEntry.CONTENT_URI, values);
        return ContentUris.parseId(favoriteUri);
    }

    public static long removeFavorite(Context mContext, int movieId){
        return mContext.getContentResolver().delete(
                 MovieContract.FavoritesEntry.CONTENT_URI,
                MovieContract.FavoritesEntry._ID + "=?",
                new String[]{""+movieId}
         );
    }

    public static boolean isFavorite(Context mContext, int movieId){
        Cursor movieCursor =null;
        try {

            movieCursor = mContext.getContentResolver().query(MovieContract.FavoritesEntry.buildFavoritesUri(movieId), null, null, null, null);

            if (movieCursor.getCount() > 0) {

                movieCursor.moveToFirst();
                return true;
            }

            return false;
        }catch(Exception e){
            Log.e("MovieService",e.getMessage());
            return false;
        }finally {

            movieCursor.close();
        }
    }


    public static List<Movie> getHighestRatingMovies(){
            // Construct the URL for the query
            Uri uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_BY,"vote_count.desc")
                    .appendQueryParameter(API_KEY, BuildConfig.MOVIE_DB_API_KEY_VAL)
                    .build();


            return getResponseFromApiCall(uri);
    }

    private static List<Movie> getMoviesFromJson(String movieJsonString) throws JSONException {
        final String MDB_POSTER_PATH = "poster_path";
        final String MDB_ID = "id";
        final String MDB_TITLE = "title";
        final String MDB_RESULTS = "results";
        final String MDB_OVERVIEW = "overview";
        final String MDB_POPULARITY = "popularity";
        final String MDB_VOTE_AVERAGE = "vote_average";
        final String MDB_VOTE_COUNT = "vote_count";
        final String MDB_RELEASE_DATE = "release_date";

        JSONObject movieJson = new JSONObject(movieJsonString);
        JSONArray resultsArray = movieJson.getJSONArray(MDB_RESULTS);

        List<Movie> movies = new ArrayList<Movie>();
        for(int i = 0; i < resultsArray.length(); i++) {
            JSONObject movieDetail = resultsArray.getJSONObject(i);

            String poster_path = movieDetail.getString(MDB_POSTER_PATH);
            String title = movieDetail.getString(MDB_TITLE);
            int id = movieDetail.getInt(MDB_ID);
            String overview = movieDetail.getString(MDB_OVERVIEW);
            String popularity = movieDetail.getString(MDB_POPULARITY);
            String vote_count = movieDetail.getString(MDB_VOTE_COUNT);
            String vote_average = movieDetail.getString(MDB_VOTE_AVERAGE);
            String release_date = movieDetail.getString(MDB_RELEASE_DATE);

            Movie movie = new Movie();
            movie.setId(id);
            movie.setTitle(title);
            movie.setPosterPath(poster_path);
            movie.setOverview(overview);
            movie.setPopularity(popularity);
            movie.setVoteAverage(vote_average);
            movie.setVoteCount(vote_count);
            movie.setReleaseDate(release_date);

            movies.add(movie);
        }

        return movies;
    }


}
