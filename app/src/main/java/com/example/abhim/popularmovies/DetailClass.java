package com.example.abhim.popularmovies;

/**
 * Created by abhim on 7/11/2016.
 */
public class DetailClass {

    public String originalTitle;
    public String movieSynopsis;
    public String movieDate;
    public String moviesRating;

    public DetailClass(String originalTitle,String movieSynopsis, String movieDate, String moviesRating){
        super();
        this.originalTitle = originalTitle;
        this.movieSynopsis =movieSynopsis;
        this.movieDate = movieDate;
        this.moviesRating = moviesRating;
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

    public String getMoviesRating() {
        return moviesRating;
    }

    public void setMoviesRating(String moviesRating) {
        this.moviesRating = moviesRating;
    }

}
