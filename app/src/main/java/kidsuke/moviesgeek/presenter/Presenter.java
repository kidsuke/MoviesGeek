package kidsuke.moviesgeek.presenter;

import android.os.Bundle;

import java.util.List;

import kidsuke.moviesgeek.model.DTOMovie;
import kidsuke.moviesgeek.model.DTOReview;
import kidsuke.moviesgeek.model.DTOReviewDetail;
import kidsuke.moviesgeek.model.DTOTrailer;
import kidsuke.moviesgeek.model.DTOTrailerDetail;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public interface Presenter {
    interface MainView {
        void updateMoviesList(List<DTOMovie> movies);
        void navigateActivity(Class activityClass, Bundle extras);
    }

    interface DetailView {
        void updateTrailersList(List<DTOTrailerDetail> trailers);
        void updateReviewsList(List<DTOReviewDetail> reviews);
    }

    void updateMoviesList(List<DTOMovie> movies);
    void updateTrailersList(List<DTOTrailerDetail> trailers);
    void updateReviewsList(List<DTOReviewDetail> reviews);
}
