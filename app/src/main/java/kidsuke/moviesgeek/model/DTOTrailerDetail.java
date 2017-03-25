package kidsuke.moviesgeek.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author longv
 *         Created on 25-Mar-17.
 */

public class DTOTrailerDetail {
    @SerializedName("id") private String id;
    @SerializedName("iso_639_1") private String language;
    @SerializedName("iso_3166_1") private String country;
    @SerializedName("key") private String key;
    @SerializedName("name") private String name;
    @SerializedName("site") private String site;
    @SerializedName("size") private int size;
    @SerializedName("type") private String type;

    public DTOTrailerDetail(String id, String language, String country, String key, String name, String site, int size, String type) {
        this.id = id;
        this.language = language;
        this.country = country;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
