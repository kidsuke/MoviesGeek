package kidsuke.moviesgeek.view.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import kidsuke.moviesgeek.R;
import kidsuke.moviesgeek.presenter.Presenter;
import kidsuke.moviesgeek.model.DTOMovie;
import kidsuke.moviesgeek.utilities.NetworkUtils;
import kidsuke.moviesgeek.view.activity.DetailActivity;
import kidsuke.moviesgeek.view.activity.MainActivity;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DTOMovie> movies;
    private Activity context;
    private Presenter controller;

    public RecyclerViewAdapter(Activity ctx, List<DTOMovie> movies, Presenter controller){
        context = ctx;
        this.movies = movies;
        this.controller = controller;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_main_imageholder, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageView imageView = ((ViewHolder) holder).getImageView();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("movie", movies.get(position));
                ((MainActivity) context).navigateActivity(DetailActivity.class, bundle);
            }
        });
        URL imgUrl = NetworkUtils.buildImageUrl(movies.get(position).getPosterPath());
        Picasso.with(context).load(imgUrl.toString()).into(imageView);
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private View baseView;
        private ImageView imageView;

        ViewHolder(View view){
            super(view);
            baseView = view;
            imageView = (ImageView) baseView.findViewById(R.id.image);
        }

        ImageView getImageView(){
            return imageView;
        }
    }

    public void updateList(List<DTOMovie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }
}
