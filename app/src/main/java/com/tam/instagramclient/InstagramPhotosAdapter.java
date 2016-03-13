package com.tam.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        LinearLayout linearComments;
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
            viewHolder.linearComments = (LinearLayout) convertView.findViewById(R.id.linearComments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.linearComments.removeAllViews();
//        ArrayList<InstagramPhotoComment> comments = photo.getComments();
//        if (!comments.isEmpty()) {
//            int count = 1;
//            InstagramPhotoComment comment = null;
//            for (int i = comments.size(); i >= 0; i--) {
//                comment = new InstagramPhotoComment();
//                comment.setUserName(comments.get(i).getUserName());
//                comment.setText(comments.get(i).getText());
//            }
//            View viewComment = viewHolder.linearComments.inflate(getContext(), R.layout.latest_comments_photo, null);
//        }
        viewHolder.tvCaption.setText(photo.getCaption());
        viewHolder.tvCreatedAt.setText(photo.getCreatedAt());
        viewHolder.tvLikesCount.setText(String.valueOf(photo.getLikesCount()));
        Picasso.with(getContext()).load(photo.getProfilePicture())
                .resize(50, 50)
                .centerCrop()
                .into(viewHolder.ivUser);
        //Picasso.with(getContext()).load(photo.getImageUrl()).into(viewHolder.ivPhoto);
        Picasso.with(getContext()).load(photo.getImageUrl())

                .error(R.drawable.error)
                .placeholder(R.drawable.progress_animation)
                .into(viewHolder.ivPhoto);
        return  convertView;
    }
}
