package kidsuke.moviesgeek.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import kidsuke.moviesgeek.R;
import kidsuke.moviesgeek.model.DTOMovie;
import kidsuke.moviesgeek.utilities.NetworkUtils;

/**
 * Created by ADMIN on 23-Jan-17.
 */

public class GridViewAdapter extends ArrayAdapter<DTOMovie> {
    private List<DTOMovie> movies;
    private Activity context;

    public GridViewAdapter(Activity ctx, int layoutId, List<DTOMovie> movies){
        super(ctx, layoutId, movies);
        this.movies = movies;
        context = ctx;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Nullable
    @Override
    public DTOMovie getItem(int position) {
        return movies.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null){
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.activity_main_imageholder, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) view.getTag();

        ImageView imageView = viewHolder.getImageView();
        URL imgUrl = NetworkUtils.buildImageUrl(getItem(position).getPosterPath());
        Picasso.with(context).load(imgUrl.toString()).into(imageView);

        return view;
    }

    private class ViewHolder{
        private View baseView;
        private ImageView imageView;

        ViewHolder(View view){
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
