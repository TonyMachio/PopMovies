package com.machio.tony.popmovies;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.machio.tony.popmovies.adapters.Movie;
import com.machio.tony.popmovies.adapters.MovieArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class MainFragment extends Fragment {

    private final String LOG_TAG = MainFragment.class.getSimpleName();
    private MovieArrayAdapter mMovieArrayAdapter;
    private GridView gridView;

    public MainFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        UpdateMovieGrid();
    }

    private void UpdateMovieGrid() {
        new FetchMoviesTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Setear el adaptador
        gridView = (GridView) rootView.findViewById(R.id.gridview_movies);

        //Definir lanzamiento de actividad de detalle al hacer pulsar sobre un item de la grid
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie)gridView.getItemAtPosition(position);
                Intent detailIntent = new Intent(getActivity(), MovieDetailActivity.class).putExtra("MOVIE", movie);
                startActivity(detailIntent);
            }
        });

        return rootView;
    }

    public class FetchMoviesTask extends AsyncTask<String, Integer, Movie[]>{

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();


        @Override
        protected Movie[] doInBackground(String... params) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String apiKey = BuildConfig.THEMOVIEDB_API_KEY;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            Movie[] moviesResultJsonArrayStr = null;

            String value_APIKEY = BuildConfig.THEMOVIEDB_API_KEY;
            String value_sortType = sharedPreferences.getString(getString(R.string.pref_sort_key),
                    getString(R.string.pref_sort_popular));
            String value_language = getResources().getConfiguration().locale.getLanguage();

            try {
                final String THEMOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie/" + value_sortType + "?";
                final String APIKEY_PARAM = "api_key";
                final String LANG_PARAM = "language";

                //Building the request URL
                Uri uri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                        .appendQueryParameter(APIKEY_PARAM, value_APIKEY)
                        .appendQueryParameter(LANG_PARAM, value_language)
                        .build();

                URL url = new URL(uri.toString());
                Log.v(LOG_TAG, "Built URL: " + url);

                // Create the request to TheMoviesDB, and open the connection
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
                String moviesJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Result from TheMovieDB HTTP Request: " + moviesJsonStr);
                moviesResultJsonArrayStr = getMoviesDataFromJson(moviesJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attempting
                // to parse it.
                return null;
            } catch (JSONException jse) {
                Log.e(LOG_TAG, "Error ", jse);
                return null;
            } finally {
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
            return moviesResultJsonArrayStr;
        }

        @Override
        protected void onPostExecute(Movie[] result) {
            if (result != null) {
                mMovieArrayAdapter = new MovieArrayAdapter(getActivity(), Arrays.asList(result));
                gridView.setAdapter(mMovieArrayAdapter);
            }
        }


        /**
         * Take the String representing the complete movie data in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         * <p>
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private Movie[] getMoviesDataFromJson(String moviesJsonStr)
                throws JSONException {

            final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342";
            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_RESULTS = "results";
            final String TMDB_TITLE = "title";
            final String TMDB_ORIGINAL_TITLE = "original_title";
            final String TMDB_RELEASE_DATE = "release_date";
            final String TMDB_OVERVIEW = "overview";
            final String TMDB_POSTER = "poster_path";
            final String TMDB_USER_RATING = "vote_average";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(TMDB_RESULTS);

            Movie[] resultMovies = new Movie[moviesArray.length()];
            Log.v(LOG_TAG, "Movies fetched: " + moviesArray.length());

            for (int i = 0; i < moviesArray.length(); i++) {
                // Get the JSON object representing the movie
                JSONObject movie = moviesArray.getJSONObject(i);

                resultMovies[i] = new Movie(
                        movie.getString(TMDB_TITLE),
                        movie.getString(TMDB_ORIGINAL_TITLE),
                        movie.getString(TMDB_RELEASE_DATE),
                        movie.getString(TMDB_OVERVIEW),
                        Double.parseDouble(movie.getString(TMDB_USER_RATING)),
                        POSTER_BASE_URL + movie.getString(TMDB_POSTER));
            }

            return resultMovies;
        }
    }

}
