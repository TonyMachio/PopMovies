package com.machio.tony.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.machio.tony.popmovies.adapters.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by Tony on 24/10/2016.
 */

public class MovieDetailFragment extends Fragment {

    private final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        Intent intent = getActivity().getIntent();

        if(intent != null && intent.hasExtra("MOVIE")){
            Movie movie = (Movie)intent.getParcelableExtra("MOVIE");

            ((TextView)rootView.findViewById(R.id.detail_title)).setText(movie.getTitle());
            ((TextView)rootView.findViewById(R.id.detail_original_title)).setText(movie.getOriginalTitle());
            ((TextView)rootView.findViewById(R.id.detail_release_date)).setText(movie.getReleaseDate());
            ((TextView)rootView.findViewById(R.id.detail_overview)).setText(movie.getOverview());
            Picasso.with(rootView.getContext()).load(movie.getPoster())
                    .into((ImageView) rootView.findViewById(R.id.detail_poster));
            ((RatingBar)rootView.findViewById(R.id.detail_rating_bar)).setRating((float)(movie.getUserAverage()* 0.5));
        }

        return rootView;
    }


}
