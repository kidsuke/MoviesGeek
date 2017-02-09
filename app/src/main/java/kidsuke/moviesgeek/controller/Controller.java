package kidsuke.moviesgeek.controller;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.List;

import kidsuke.moviesgeek.model.DTOMovie;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public interface Controller {
    interface MainView {
        void updateMoviesList(List<DTOMovie> movies);
        void navigateActivity(Class activityClass, Bundle extras);
    }

    void updateMoviesList(List<DTOMovie> movies);
}
