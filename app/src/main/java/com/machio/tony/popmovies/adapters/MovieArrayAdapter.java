package com.machio.tony.popmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.machio.tony.popmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Tony on 20/10/2016.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private List<Movie> movies;
    private Context context;

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context,0,movies);
        this.context = context;
        this.movies = movies;
    }

    public Movie getMovie(int position) {
        return movies.get(position);
    }

    @Override
    public View getView(int position, View viewLayout, ViewGroup parent) {
        Movie movieItem = getItem(position);

        if (viewLayout == null) {
            viewLayout = LayoutInflater.from(context).inflate(R.layout.image_view_grid, parent, false);
        }

        Picasso.with(context).load(movieItem.getPoster()).into(
                (ImageView) viewLayout.findViewById(R.id.poster_grid));
        ((TextView)viewLayout.findViewById(R.id.rating_grid)).setText(Double.toString(movieItem.getUserAverage()));

        return viewLayout;
    }
}
