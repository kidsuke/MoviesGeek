package kidsuke.moviesgeek.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author longv
 *         Created on 25-Mar-17.
 */

public class DTOReviewDetail {
    @SerializedName("id") private String id;
    @SerializedName("author") private String author;
    @SerializedName("content") private String content;
    @SerializedName("url") private String url;

    public DTOReviewDetail(String id, String author, String content, String url) {

        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
