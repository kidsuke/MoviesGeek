package kidsuke.moviesgeek.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import kidsuke.moviesgeek.model.DTOMovie;
import kidsuke.moviesgeek.model.DTOTrailer;
import kidsuke.moviesgeek.presenter.Presenter;
import kidsuke.moviesgeek.utilities.NetworkUtils;
import kidsuke.moviesgeek.utilities.OpenMovieJsonUtils;

/**
 * @author longv
 *         Created on 25-Mar-17.
 */

public class FetchTrailerTask extends AsyncTask<URL, Void, DTOTrailer> {
    public static final String TAG = FetchTrailerTask.class.getSimpleName();
    private Presenter presenter;

    public FetchTrailerTask(Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    protected DTOTrailer doInBackground(URL... urls) {
        URL url = urls[0];

        String jsonMovieResponse = null;
        try {
            jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Input/output stream exception");
        }

        return OpenMovieJsonUtils.convertJsonToObject(jsonMovieResponse, DTOTrailer.class);
    }

    @Override
    protected void onPostExecute(DTOTrailer trailers) {
        presenter.updateTrailersList(trailers.getResults());
    }
}
