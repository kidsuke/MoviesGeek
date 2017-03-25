package kidsuke.moviesgeek.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public class DTOMovie implements Parcelable{
    private String poster_path, overview, release_date, original_title, original_language, title, backdrop_path;
    private boolean adult, video;
    private int[] genre_ids;
    private int id, popularity, votecount, vote_average;

    public String getPosterPath(){
        return poster_path;
    }

    public String getTitle(){
        return title;
    }

    public String getOverview(){
        return overview;
    }

    public int getRating(){
        return popularity;
    }

    public int getId(){
        return id;
    }

    public String getReleaseDate(){
        return release_date;
    }

    private DTOMovie (Parcel in){
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        original_language = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        adult = in.readInt() == 1;
        video = in.readInt() == 1;
        genre_ids = in.createIntArray();
        id = in.readInt();
        popularity = in.readInt();
        votecount = in.readInt();
        vote_average = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(poster_path);
        out.writeString(overview);
        out.writeString(release_date);
        out.writeString(original_title);
        out.writeString(original_language);
        out.writeString(title);
        out.writeString(backdrop_path);
        out.writeInt(adult? 1 : 0);
        out.writeInt(video? 1 : 0);
        out.writeIntArray(genre_ids);
        out.writeInt(id);
        out.writeInt(popularity);
        out.writeInt(votecount);
        out.writeInt(vote_average);
    }

    public static final Parcelable.Creator<DTOMovie> CREATOR
            = new Parcelable.Creator<DTOMovie>() {

        // This simply calls our new constructor (typically private) and 
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public DTOMovie createFromParcel(Parcel in) {
            return new DTOMovie(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public DTOMovie[] newArray(int size) {
            return new DTOMovie[size];
        }
    };
}
