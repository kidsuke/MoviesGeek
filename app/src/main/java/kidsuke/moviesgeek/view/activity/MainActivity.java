package kidsuke.moviesgeek.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kidsuke.moviesgeek.R;
import kidsuke.moviesgeek.presenter.Presenter;
import kidsuke.moviesgeek.presenter.impl.PresenterImpl;
import kidsuke.moviesgeek.model.DTOMovie;
import kidsuke.moviesgeek.network.FetchMovieTask;
import kidsuke.moviesgeek.utilities.NetworkUtils;
import kidsuke.moviesgeek.utilities.OpenMovieJsonUtils;
import kidsuke.moviesgeek.view.adapter.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements Presenter.MainView{
    public static final String TAG = MainActivity.class.getSimpleName();
    public final int SORT_BY_POPULAR = 0;
    public final int SORT_BY_TOPRATED = 1;
    public final int SORT_BY_FAVORITES = 2;
    public final String STATE_KEY = "CURRENT_SELCTION";
    private int currentState;
    private RecyclerViewAdapter adapter;
    private URL popularUrl, topratedUrl;
    private Presenter controller;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("appdata", Context.MODE_PRIVATE);

        //Init view
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);

        //Set up controller
        controller = new PresenterImpl(this);

        //Set up adapter for recycler view
        List<DTOMovie> movies = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this, movies, controller);
        recyclerView.setAdapter(adapter);
        //Set up layout manager for recycler view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //Set up tool bar
        setSupportActionBar(toolBar);

        //Fetch movie sort by popularity by default
        popularUrl = NetworkUtils.buildMovieApiUrl(NetworkUtils.POPULAR_MOVIES_RELATIVE_URL);
        topratedUrl = NetworkUtils.buildMovieApiUrl(NetworkUtils.TOPRATED_MOVIES_RELATIVE_URL);

        if (savedInstanceState != null) {
            currentState = savedInstanceState.getInt(STATE_KEY);
            if (currentState == SORT_BY_POPULAR)
                new FetchMovieTask(controller).execute(popularUrl);
            else if (currentState == SORT_BY_TOPRATED)
                new FetchMovieTask(controller).execute(topratedUrl);
            else if (currentState == SORT_BY_FAVORITES)
                loadMoviesByFavorites();
            else
                new FetchMovieTask(controller).execute(popularUrl);
        } else {
            new FetchMovieTask(controller).execute(popularUrl);
            currentState = SORT_BY_POPULAR;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_by:
                return false;
            case R.id.popular:
                new FetchMovieTask(controller).execute(popularUrl);
                currentState = SORT_BY_POPULAR;
                return true;
            case R.id.toprated:
                new FetchMovieTask(controller).execute(topratedUrl);
                currentState = SORT_BY_TOPRATED;
                return true;
            case R.id.favorite:
                loadMoviesByFavorites();
                currentState = SORT_BY_FAVORITES;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void navigateActivity(Class activityClass, Bundle extras) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(TAG, extras);
        startActivity(intent);
    }

    @Override
    public void updateMoviesList(List<DTOMovie> movies) {
        if (movies != null)
            adapter.updateList(movies);
        else{
            new AlertDialog.Builder(this)
                    .setMessage("No data received. Check again API key or internet connection")
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_KEY, currentState);
    }

    private void loadMoviesByFavorites(){
        Set<String> favoriteMovies = sharedPreferences.getStringSet("favorite", new HashSet<String>());
        List<DTOMovie> movies = new ArrayList<>();
        for (String s : favoriteMovies) {
            movies.add(OpenMovieJsonUtils.convertJsonToObject(s, DTOMovie.class));
        }
        updateMoviesList(movies);
    }
}
