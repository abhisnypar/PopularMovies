package com.example.abhim.popularmovies;

/**
 * Created by abhim on 7/3/2016.
 */
public class SingleRow {

    private int imgList;
    private String movieName;
    private String movieDetails;
    private String movieReleaseDate;

    public int getImgList() {
        return imgList;
    }

    public void setImgList(int imgList) {
        this.imgList = imgList;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(String movieDetails) {
        this.movieDetails = movieDetails;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public SingleRow(String mNames, int imgList) {
            this.imgList = imgList;
        }
}
