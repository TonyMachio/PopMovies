package com.machio.tony.popmovies.adapters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tony on 20/10/2016.
 */

public class Movie implements Parcelable{
    private String title;
    private String originalTitle;
    private String releaseDate;
    private String overview;
    private double userAverage;
    private String poster;

    public Movie(String title, String originalTitle, String releaseDate, String overview, double userAverage, String poster) {
        this.title = title;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.userAverage = userAverage;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getUserAverage() {
        return userAverage;
    }

    public void setUserAverage(double userAverage) {
        this.userAverage = userAverage;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    //Implementation of Parcelable
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeDouble(userAverage);
        dest.writeString(poster);
    }

    private Movie(Parcel in) {
        title = in.readString();
        originalTitle = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        userAverage = in.readDouble();
        poster = in.readString();
    }
}
