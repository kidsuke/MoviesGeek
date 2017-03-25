package kidsuke.moviesgeek.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public class NetworkUtils {
    public static final String TAG = NetworkUtils.class.getSimpleName();
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    public static final String IMAGESIZE_RELATIVE_URL = "w342";
    public static final String MOVIEDB_API_URL = "http://api.themoviedb.org/3/movie";
    public static final String POPULAR_MOVIES_RELATIVE_URL = "popular";
    public static final String TOPRATED_MOVIES_RELATIVE_URL = "top_rated";
    public static final String MOVIE_TRAILERS_RELATIVE_URL = "videos";
    public static final String MOVIE_REVIEWS_RELATIVE_URL = "reviews";
    //PUT YOUR API KEY IN API_KEY_VALUE
    public static final String API_KEY_PARAM = "api_key";
    public static final String API_KEY_VALUE = "";

    public static URL buildImageUrl(String filepath){
        Uri uri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                     .appendPath(IMAGESIZE_RELATIVE_URL)
                     .appendPath(filepath.substring(1))
                     .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Malformed Image URL Exception");
        }

        Log.v(TAG, "Built Image URI " + url);

        return url;
    }

    public static URL buildMovieApiUrl(String sortBy){
        Uri uri = Uri.parse(MOVIEDB_API_URL).buildUpon()
                .appendPath(sortBy)
                .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Malformed Movie Api URL Exception");
        }

        Log.v(TAG, "Built Movie Api URI " + url);

        return url;
    }

    public static URL buildMovieApiUrl(String id, String path){
        Uri uri = Uri.parse(MOVIEDB_API_URL).buildUpon()
                .appendPath(id)
                .appendPath(path)
                .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Malformed Movie Api URL Exception");
        }

        Log.v(TAG, "Built Movie Api URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (FileNotFoundException e){
            return null;
        } finally {
            urlConnection.disconnect();
        }
    }
}
