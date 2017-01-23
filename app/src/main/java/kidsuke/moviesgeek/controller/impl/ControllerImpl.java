package kidsuke.moviesgeek.controller.impl;

import java.util.List;

import kidsuke.moviesgeek.controller.Controller;
import kidsuke.moviesgeek.model.DTOMovie;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public class ControllerImpl implements Controller {
    private View mView;

    public ControllerImpl(View view){
        mView = view;
    }

    @Override
    public void updateMoviesList(List<DTOMovie> movies) {
        mView.updateMoviesList(movies);
    }
}
