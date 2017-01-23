package kidsuke.moviesgeek.controller;

import java.util.List;

import kidsuke.moviesgeek.model.DTOMovie;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public interface Controller {
    interface View {
        void updateMoviesList(List<DTOMovie> movies);
    }
    void updateMoviesList(List<DTOMovie> movies);
}
