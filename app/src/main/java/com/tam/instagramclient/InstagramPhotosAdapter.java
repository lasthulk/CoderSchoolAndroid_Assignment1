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
        ImageView ivPhoto;
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
//        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        viewHolder.tvCaption.setText(photo.getCaption());
        viewHolder.ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.getImageUrl()).into(viewHolder.ivPhoto);
        return  convertView;
    }
}
