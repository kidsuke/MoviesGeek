package kidsuke.moviesgeek.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author longv
 *         Created on 25-Mar-17.
 */

public class DTOTrailer {
    @SerializedName("id") private int id;
    @SerializedName("results") private List<DTOTrailerDetail> results;

    public DTOTrailer(int id, List<DTOTrailerDetail> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DTOTrailerDetail> getResults() {
        return results;
    }

    public void setResults(List<DTOTrailerDetail> results) {
        this.results = results;
    }

}
