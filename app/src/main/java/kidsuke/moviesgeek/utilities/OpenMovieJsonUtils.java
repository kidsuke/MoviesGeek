package kidsuke.moviesgeek.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kidsuke.moviesgeek.model.DTOMovie;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public class OpenMovieJsonUtils {
    private static Gson gson = new Gson();

    public static List<DTOMovie> transferToDTOMovie(String json){
        List<DTOMovie> movies = null;

        if (json != null) {
            movies = new ArrayList<>();
            JsonObject jsonResult = gson.fromJson(json, JsonObject.class);
            JsonArray jsonMoviesArray = jsonResult.getAsJsonArray("results");

            for (int i = 0; i < jsonMoviesArray.size(); i++) {
                JsonElement jsonMovie = jsonMoviesArray.get(i);
                DTOMovie dtoMovie = gson.fromJson(jsonMovie, DTOMovie.class);
                movies.add(dtoMovie);
            }
        }

        return movies;
    }

    public static <T> List<T> convertJsonToObjectList(String json){
        Type listType = new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static <T> T convertJsonToObject(String json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }

    public static <T> String covertObjectToJson(T object) {
        return gson.toJson(object);
    }
}
