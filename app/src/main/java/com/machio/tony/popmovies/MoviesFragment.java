package com.machio.tony.popmovies;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class MoviesFragment extends Fragment {

    private final String LOG_TAG = MoviesFragment.class.getSimpleName();

    public MoviesFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        UpdateMovieGrid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        //Setear el adaptador

        //Definir lanzamiento de actividad de detalle al hacer pulsar sobre un item de la grid

        return rootView;
    }

    private void UpdateMovieGrid() {
    }

    public class FetchMoviesTask extends AsyncTask<String, Integer, String[]>{

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();


        @Override
        protected String[] doInBackground(String... params) {

            String apiKey = BuildConfig.THEMOVIEDB_API_KEY;
            return new String[0];
        }
    }

}
