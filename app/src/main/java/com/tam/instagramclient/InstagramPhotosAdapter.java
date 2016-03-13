package com.tam.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by toan on 3/13/2016.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    private static class ViewHolder {
        TextView tvCaption;
        TextView tvCreatedAt;
        TextView tvLikesCount;
        ImageView ivPhoto;
        ImageView ivUser;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.ivUser = (ImageView) convertView.findViewById(R.id.ivUser);
            viewHolder.tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
            viewHolder.tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikesCount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
//        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        viewHolder.tvCaption.setText(photo.getCaption());
        viewHolder.tvCreatedAt.setText(photo.getCreatedAt());
        viewHolder.tvLikesCount.setText(String.valueOf(photo.getLikesCount()));
        Picasso.with(getContext()).load(photo.getProfilePicture()).into(viewHolder.ivUser);
        Picasso.with(getContext()).load(photo.getImageUrl()).into(viewHolder.ivPhoto);
        return  convertView;
    }
}
