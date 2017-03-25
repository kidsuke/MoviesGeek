package kidsuke.moviesgeek.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author longv
 *         Created on 25-Mar-17.
 */

public class DTOReview {
    @SerializedName("id") private int id;
    @SerializedName("page") private int page;
    @SerializedName("results") private List<DTOReviewDetail> results;

    public DTOReview(int id, int page, List<DTOReviewDetail> results) {
        this.id = id;
        this.page = page;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<DTOReviewDetail> getResults() {
        return results;
    }

    public void setResults(List<DTOReviewDetail> results) {
        this.results = results;
    }
}
