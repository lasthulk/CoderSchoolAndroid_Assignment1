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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by toan on 3/13/2016.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    static class PhotoViewHolder {
        @Bind(R.id.tvCaption)
        TextView tvCaption;

        @Bind(R.id.tvCreatedAt)
        TextView tvCreatedAt;

        @Bind(R.id.tvLikesCount)
        TextView tvLikesCount;

        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;

        @Bind(R.id.ivUser)
        ImageView ivUser;

        @Bind(R.id.linearComments)
        LinearLayout linearComments;

        @Bind(R.id.tvUsername)
        TextView tvUsername;

        public PhotoViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        PhotoViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            holder = new PhotoViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PhotoViewHolder) convertView.getTag();
            holder.linearComments.removeAllViews();
        }
//        holder.linearComments.removeAllViews();
        ArrayList<InstagramPhotoComment> comments = photo.getComments();
        if (!comments.isEmpty()) {
            int count = 0;
            InstagramPhotoComment comment = null;
            for (int i = comments.size() - 1; i >= 0; i--) {
                comment = new InstagramPhotoComment();
                comment.setUserName(comments.get(i).getUserName());
                comment.setText(comments.get(i).getText());
                View viewComment = holder.linearComments.inflate(getContext(), R.layout.latest_comments_photo, null);
                TextView tvCommentText = (TextView) viewComment.findViewById(R.id.tvCommentText);
                tvCommentText.setText(comment.getText());
                TextView tvCommentUserName = (TextView) viewComment.findViewById(R.id.tvCommentUserName);
                tvCommentUserName.setText(comment.getUserName());

                count++;
                holder.linearComments.addView(viewComment);
                if(count == 2) {
                    break;
                }
            }
        }
        holder.tvUsername.setText(photo.getUserName());
        holder.tvCaption.setText(photo.getCaption());
        holder.tvCreatedAt.setText(photo.getCreatedAt());
        holder.tvLikesCount.setText(String.valueOf(photo.getLikesCount()));
        //int width = Math.round(DeviceDimensionsHelper.convertDpToPixel(50, getContext()));
        Picasso.with(getContext()).load(photo.getProfilePicture())
                //.resize(width, width)
                .fit()
                .centerCrop()
                .into(holder.ivUser);
        Picasso.with(getContext()).load(photo.getImageUrl())
                .error(R.drawable.error)
                .placeholder(R.drawable.progress_animation)
                .into(holder.ivPhoto);
        return  convertView;
    }
}
