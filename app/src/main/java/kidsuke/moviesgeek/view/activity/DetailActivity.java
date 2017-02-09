package kidsuke.moviesgeek.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.sax.TextElementListener;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

import kidsuke.moviesgeek.R;
import kidsuke.moviesgeek.model.DTOMovie;
import kidsuke.moviesgeek.utilities.NetworkUtils;
import kidsuke.moviesgeek.utilities.OpenMovieJsonUtils;

/**
 * Created by ADMIN on 09-Feb-17.
 */

public class DetailActivity extends AppCompatActivity{
    private DTOMovie movie;
    private ImageView image;
    private TextView title, overview, rating, release_date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Init view
        image = (ImageView) findViewById(R.id.poster);
        title = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview_container);
        rating = (TextView) findViewById(R.id.user_rating_container);
        release_date = (TextView) findViewById(R.id.release_date_container);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null){
            if (extras.containsKey(MainActivity.TAG)){
                Bundle bundle = extras.getBundle(MainActivity.TAG);
                if (bundle != null){
                    Parcelable parcelable = bundle.getParcelable("movie");
                    movie = (DTOMovie) parcelable;
                    //Set up image
                    URL imgUrl = NetworkUtils.buildImageUrl(movie.getPosterPath());
                    Picasso.with(this).load(imgUrl.toString()).into(image);
                    title.setText(movie.getTitle());
                    overview.setText(movie.getOverview());
                    rating.setText(Integer.toString(movie.getRating()));
                    release_date.setText(movie.getReleaseDate());
                }
            }
        }
    }
}
