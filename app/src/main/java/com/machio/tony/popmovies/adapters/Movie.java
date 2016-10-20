package com.machio.tony.popmovies.adapters;

/**
 * Created by Tony on 20/10/2016.
 */

public class Movie {
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
}
