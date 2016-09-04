package com.example.abhim.popularmovies.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abhim on 8/13/2016.
 */
public class ModelClass implements Parcelable {

    public String originalTitle;
    public String movieSynopsis;
    public String movieDate;
    public double moviesRating;
    public String posterImage;
    public String gridImage;

    protected ModelClass(Parcel in) {
        originalTitle = in.readString();
        movieSynopsis = in.readString();
        movieDate = in.readString();
        moviesRating = in.readDouble();
        posterImage = in.readString();
        gridImage = in.readString();
    }

    public static final Creator<ModelClass> CREATOR = new Creator<ModelClass>() {
        @Override
        public ModelClass createFromParcel(Parcel in) {
            return new ModelClass(in);
        }

        @Override
        public ModelClass[] newArray(int size) {
            return new ModelClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalTitle);
        dest.writeString(movieSynopsis);
        dest.writeString(movieDate);
        dest.writeDouble(moviesRating);
        dest.writeString(posterImage);
        dest.writeString(gridImage);
    }
}
