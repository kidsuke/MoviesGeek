package kidsuke.moviesgeek.presenter.impl;

import java.util.List;

import kidsuke.moviesgeek.model.DTOReviewDetail;
import kidsuke.moviesgeek.model.DTOTrailer;
import kidsuke.moviesgeek.model.DTOTrailerDetail;
import kidsuke.moviesgeek.presenter.Presenter;
import kidsuke.moviesgeek.model.DTOMovie;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public class PresenterImpl implements Presenter {
    private MainView mMainView;
    private DetailView mDetailVIew;

    public PresenterImpl(MainView view){
        mMainView = view;
    }

    public PresenterImpl(DetailView view){
        mDetailVIew = view;
    }

    @Override
    public void updateMoviesList(List<DTOMovie> movies) {
        mMainView.updateMoviesList(movies);
    }

    @Override
    public void updateTrailersList(List<DTOTrailerDetail> trailers) {
        mDetailVIew.updateTrailersList(trailers);
    }

    @Override
    public void updateReviewsList(List<DTOReviewDetail> reviews) {
        mDetailVIew.updateReviewsList(reviews);
    }
}
