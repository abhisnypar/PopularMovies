package com.example.abhim.popularmovies.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.abhim.popularmovies.ModelClasses.DetailClass;

import java.util.ArrayList;

public class MovieInfoViewModel extends ViewModel {

    public ArrayList<DetailClass> detailClass;

    public void setDetailClass(ArrayList<DetailClass> detailClass) {
        this.detailClass = detailClass;
    }

    public ArrayList<DetailClass> getDetailClass() {
        return detailClass;
    }
}
