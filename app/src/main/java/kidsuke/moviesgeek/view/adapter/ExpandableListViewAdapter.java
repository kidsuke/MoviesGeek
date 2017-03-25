package kidsuke.moviesgeek.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kidsuke.moviesgeek.R;
import kidsuke.moviesgeek.model.DTOReview;
import kidsuke.moviesgeek.model.DTOReviewDetail;
import kidsuke.moviesgeek.model.DTOTrailer;
import kidsuke.moviesgeek.model.DTOTrailerDetail;

/**
 * @author longv
 *         Created on 25-Mar-17.
 */

public class ExpandableListViewAdapter<T> extends BaseExpandableListAdapter {
    private Activity context;
    private String header;
    private List<T> childList;

    public ExpandableListViewAdapter(Activity context, String header, List<T> childList) {
        this.header = header;
        this.context = context;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return header;
    }

    @Override
    public T getChild(int groupPosition, int childPosition) {
        return childList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.header_view, null);
        }

        TextView text = (TextView) convertView.findViewById(R.id.header);
        text.setText(getGroup(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final T childItem = getChild(groupPosition, childPosition);
        if (childItem instanceof DTOTrailerDetail) {
            if (convertView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(R.layout.trailer_view, null);
            }
            TextView text = (TextView) convertView.findViewById(R.id.trailer_name);
            text.setText(((DTOTrailerDetail)childItem).getName());
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri url = Uri.parse("https://www.youtube.com/watch?v=" + ((DTOTrailerDetail) childItem).getKey());
                    Intent intent = new Intent(Intent.ACTION_VIEW, url);
                    ExpandableListViewAdapter.this.context.startActivity(intent);
                }
            });
        } else if (childItem instanceof DTOReviewDetail){
            if (convertView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(R.layout.review_view, null);
            }

            ImageView image = (ImageView) convertView.findViewById(R.id.image);
            TextView author = (TextView) convertView.findViewById(R.id.author);
            TextView content = (TextView) convertView.findViewById(R.id.content);
            Picasso.with(context).load("https://api.adorable.io/avatars/150/" + ((DTOReviewDetail) childItem).getAuthor() + ".png").into(image);
            author.setText(((DTOReviewDetail) childItem).getAuthor());
            content.setText(((DTOReviewDetail) childItem).getContent());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void updateList(List<T> list){
        this.childList = list;
        notifyDataSetChanged();
    }
}
