package kidsuke.moviesgeek.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kidsuke.moviesgeek.model.DTOMovie;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public class OpenMovieJsonUtils {
    public static List<DTOMovie> transferToDTOMovie(String json){
        Gson gson = new Gson();
        List<DTOMovie> movies = new ArrayList<>();

        JsonObject jsonResult = gson.fromJson(json, JsonObject.class);
        JsonArray jsonMoviesArray = jsonResult.getAsJsonArray("results");

        for (int i = 0; i < jsonMoviesArray.size(); i++){
            JsonElement jsonMovie = jsonMoviesArray.get(i);
            DTOMovie dtoMovie = gson.fromJson(jsonMovie, DTOMovie.class);
            movies.add(dtoMovie);
        }

        return movies;
    }
}
