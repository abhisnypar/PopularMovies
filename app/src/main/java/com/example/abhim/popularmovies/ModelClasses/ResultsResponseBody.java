package com.example.abhim.popularmovies.ModelClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultsResponseBody {

    @SerializedName("results")
    public ArrayList<DetailClass> detailClass;


    public ArrayList<DetailClass> getDetailClass() {
        return detailClass;
    }
}

