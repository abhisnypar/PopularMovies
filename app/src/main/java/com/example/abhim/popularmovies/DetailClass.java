package com.example.abhim.popularmovies;

/**
 * Created by abhim on 7/11/2016.
 */
public class DetailClass {

    public String originalTitle;
    public String movieSynopsis;
    public String movieDate;
    public double moviesRating;
    public int posterImage;

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getMovieSynopsis() {
        return movieSynopsis;
    }

    public void setMovieSynopsis(String movieSynopsis) {
        this.movieSynopsis = movieSynopsis;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public double getMoviesRating() {
        return moviesRating;
    }

    public void setMoviesRating(double moviesRating) {
        this.moviesRating = moviesRating;
    }

    public int getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(int posterImage) {
        this.posterImage = posterImage;
    }

}
