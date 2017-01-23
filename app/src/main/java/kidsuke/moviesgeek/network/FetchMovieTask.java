package kidsuke.moviesgeek.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import kidsuke.moviesgeek.controller.Controller;
import kidsuke.moviesgeek.model.DTOMovie;
import kidsuke.moviesgeek.utilities.NetworkUtils;
import kidsuke.moviesgeek.utilities.OpenMovieJsonUtils;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public class FetchMovieTask extends AsyncTask <URL, Void, List<DTOMovie>> {
    public static final String TAG = FetchMovieTask.class.getSimpleName();
    private Controller controller;

    public FetchMovieTask(Controller controller){
        this.controller = controller;
    }

    @Override
    protected List<DTOMovie> doInBackground(URL... urls) {
        URL url = urls[0];

        String jsonMovieResponse = null;
        try {
            jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Input/output stream exception");
        }

        return OpenMovieJsonUtils.transferToDTOMovie(jsonMovieResponse);
    }

    @Override
    protected void onPostExecute(List<DTOMovie> movies) {
        if (movies != null && movies.size() != 0){
            controller.updateMoviesList(movies);
        }
    }
}
