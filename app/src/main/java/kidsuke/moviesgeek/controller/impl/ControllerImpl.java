package kidsuke.moviesgeek.controller.impl;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.List;

import kidsuke.moviesgeek.controller.Controller;
import kidsuke.moviesgeek.model.DTOMovie;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public class ControllerImpl implements Controller {
    private MainView mMainView;

    public ControllerImpl(MainView view){
        mMainView = view;
    }

    @Override
    public void updateMoviesList(List<DTOMovie> movies) {
        mMainView.updateMoviesList(movies);
    }
}
