package kidsuke.moviesgeek.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kidsuke.moviesgeek.R;
import kidsuke.moviesgeek.model.DTOMovie;
import kidsuke.moviesgeek.model.DTOReviewDetail;
import kidsuke.moviesgeek.model.DTOTrailerDetail;
import kidsuke.moviesgeek.network.FetchReviewTask;
import kidsuke.moviesgeek.network.FetchTrailerTask;
import kidsuke.moviesgeek.presenter.Presenter;
import kidsuke.moviesgeek.presenter.impl.PresenterImpl;
import kidsuke.moviesgeek.utilities.NetworkUtils;
import kidsuke.moviesgeek.utilities.OpenMovieJsonUtils;
import kidsuke.moviesgeek.view.adapter.ExpandableListViewAdapter;

/**
 * Created by ADMIN on 09-Feb-17.
 */

public class DetailActivity extends AppCompatActivity implements Presenter.DetailView{
    private DTOMovie movie;
    private ImageView image, favorite;
    private TextView title, overview, rating, release_date;
    private ExpandableListView trailersView, reviewsView;
    private ExpandableListViewAdapter<DTOTrailerDetail> trailerAdapter;
    private ExpandableListViewAdapter<DTOReviewDetail> reviewAdapter;
    private Presenter presenter;
    private SharedPreferences sharedPreferences;
    private URL TRAILER_URL, REVIEW_URL;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        presenter = new PresenterImpl(this);
        sharedPreferences = getSharedPreferences("appdata", Context.MODE_PRIVATE);

        //Init view
        image = (ImageView) findViewById(R.id.poster);
        favorite = (ImageView) findViewById(R.id.favorite);
        title = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview_container);
        rating = (TextView) findViewById(R.id.user_rating_container);
        release_date = (TextView) findViewById(R.id.release_date_container);
        trailersView = (ExpandableListView) findViewById(R.id.trailers_list);
        reviewsView = (ExpandableListView) findViewById(R.id.reviews_list);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> favoriteMovies = sharedPreferences.getStringSet("favorite", new HashSet<String>());
                String chosen = OpenMovieJsonUtils.covertObjectToJson(movie);
                if (favoriteMovies.contains(chosen)) {
                    favoriteMovies.remove(chosen);
                    favorite.setColorFilter(getResources().getColor(R.color.colorGray), PorterDuff.Mode.SRC_ATOP);
                } else {
                    favoriteMovies.add(chosen);
                    favorite.setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("favorite", favoriteMovies);
                editor.apply();
                editor.commit();
            }
        });
        Set<String> favoriteMovies = sharedPreferences.getStringSet("favorite", new HashSet<String>());
        String chosen = OpenMovieJsonUtils.covertObjectToJson(movie);
        if (favoriteMovies.contains(chosen)) {
            favorite.setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
        } else {
            favorite.setColorFilter(getResources().getColor(R.color.colorGray), PorterDuff.Mode.SRC_ATOP);
        }

        //Init list
        trailerAdapter = new ExpandableListViewAdapter<>(this, "Trailer", new ArrayList<DTOTrailerDetail>());
        reviewAdapter =  new ExpandableListViewAdapter<>(this, "Review", new ArrayList<DTOReviewDetail>());
        trailersView.setAdapter(trailerAdapter);
        trailersView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeight(trailersView, groupPosition);
                if (!trailersView.isGroupExpanded(groupPosition)) {
                    trailersView.expandGroup(groupPosition);
                    trailersView.setGroupIndicator(getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp));
                } else {
                    trailersView.collapseGroup(groupPosition);
                    trailersView.setGroupIndicator(getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp));
                }
                return true;
            }
        });
        reviewsView.setAdapter(reviewAdapter);
        reviewsView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeight(reviewsView, groupPosition);
                if (!reviewsView.isGroupExpanded(groupPosition)) {
                    reviewsView.expandGroup(groupPosition);
                    reviewsView.setGroupIndicator(getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp));
                } else {
                    reviewsView.collapseGroup(groupPosition);
                    reviewsView.setGroupIndicator(getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp));
                }
                return true;
            }
        });
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
                    //Get trailers and reviews
                    TRAILER_URL = NetworkUtils.buildMovieApiUrl(Integer.toString(movie.getId()), NetworkUtils.MOVIE_TRAILERS_RELATIVE_URL);
                    REVIEW_URL = NetworkUtils.buildMovieApiUrl(Integer.toString(movie.getId()), NetworkUtils.MOVIE_REVIEWS_RELATIVE_URL);
                    new FetchTrailerTask(presenter).execute(TRAILER_URL);
                    new FetchReviewTask(presenter).execute(REVIEW_URL);
                }
            }
        }
    }

    @Override
    public void updateTrailersList(List<DTOTrailerDetail> trailers) {
        if (trailers != null)
            trailerAdapter.updateList(trailers);
        else{
            new AlertDialog.Builder(this)
                    .setMessage("No trailers received. Check again API key or internet connection")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(true).show();
        }
    }

    @Override
    public void updateReviewsList(List<DTOReviewDetail> reviews) {
        if (reviews != null)
            reviewAdapter.updateList(reviews);
        else{
            new AlertDialog.Builder(this)
                    .setMessage("No reviews received. Check again API key or internet connection")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(true).show();
        }
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height + 50;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
