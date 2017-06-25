package com.example.abhim.popularmovies.ModelClasses;

/**
 * Created by anusha on 6/11/2017.
 */
public class DetailClass  {

    public String originalTitle;
    public String movieSynopsis;
    public String movieDate;
    public double moviesRating;
    public String posterImage;
    public String gridImage;


    public String getGridImage() {
        return gridImage;
    }

    public void setGridImage(String gridImage) {
        this.gridImage = gridImage;
    }

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

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

}
