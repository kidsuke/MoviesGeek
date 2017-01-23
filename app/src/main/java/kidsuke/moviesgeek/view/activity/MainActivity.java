package kidsuke.moviesgeek.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kidsuke.moviesgeek.R;
import kidsuke.moviesgeek.controller.Controller;
import kidsuke.moviesgeek.controller.impl.ControllerImpl;
import kidsuke.moviesgeek.model.DTOMovie;
import kidsuke.moviesgeek.network.FetchMovieTask;
import kidsuke.moviesgeek.utilities.NetworkUtils;
import kidsuke.moviesgeek.view.adapter.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements Controller.View{
    private RecyclerViewAdapter adapter;
    private URL popularUrl, topratedUrl;
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init view
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);

        //Set up controller
        controller = new ControllerImpl(this);

        //Set up adapter for recycler view
        List<DTOMovie> movies = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this, movies);
        recyclerView.setAdapter(adapter);
        //Set up layout manager for recycler view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //Set up tool bar
        setSupportActionBar(toolBar);

        //Fetch movie sort by popularity by default
        popularUrl = NetworkUtils.buildMovieApiUrl(NetworkUtils.POPULAR_MOVIES_RELATIVE_URL);
        topratedUrl = NetworkUtils.buildMovieApiUrl(NetworkUtils.TOPRATED_MOVIED_RELATIVE_URL);
        new FetchMovieTask(controller).execute(popularUrl);
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
                return true;
            case R.id.toprated:
                new FetchMovieTask(controller).execute(topratedUrl);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateMoviesList(List<DTOMovie> movies) {
        adapter.updateList(movies);
    }
}
