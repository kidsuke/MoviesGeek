package kidsuke.moviesgeek.model;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public class DTOMovie {
    private String poster_path, overview, release_date, original_title, original_language, title, backdrop_path;
    private boolean adult, video;
    private int[] genre_ids;
    private int id, popularity, votecount, vote_average;

    public String getPosterPath(){
        return poster_path;
    }
}
