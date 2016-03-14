package com.tam.instagramclient;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.Log;
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


        @Bind(R.id.tvViewAllComments)
        TextView tvViewAllComments;

        public PhotoViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
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
            int countComments = photo.getComments().size();
            if (countComments == 0) {
                holder.tvViewAllComments.setVisibility(View.GONE);
            } else {
                holder.tvViewAllComments.setVisibility(View.VISIBLE);
            }
            holder.tvViewAllComments.setTag(position);
            holder.tvCaption.setText(InstagramUIHelper.formatCaption(photo.getUserName(), photo.getCaption()));
            holder.tvUsername.setText(InstagramUIHelper.formatUserName(photo.getUserName()));
            holder.tvCreatedAt.setText(photo.getCreatedAt());
            int likesCount = photo.getLikesCount();
            holder.tvLikesCount.setText(InstagramUIHelper.formatLikesCount(likesCount));
            AddViewComments(photo, holder);
            DisplayPhotos(photo, holder);
        } catch (Exception ex) {
            Log.d(this.toString(), ex.getMessage());
        }
        return  convertView;
    }

    private void DisplayPhotos(InstagramPhoto photo, PhotoViewHolder holder) {
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
    }


    private void AddViewComments(InstagramPhoto photo, PhotoViewHolder holder) {
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
                TextView tvCommentUserName = (TextView) viewComment.findViewById(R.id.tvCommentUserName);
                tvCommentUserName.setText(InstagramUIHelper.formatUserName(comment.getUserName()));
                String commentText = comment.getText();
                SpannableStringBuilder commentSpan = InstagramUIHelper.formatComment(commentText);
                tvCommentText.setText(commentSpan);
                holder.linearComments.addView(viewComment);
                count++;
                if(count == 2) {
                    break;
                }
            }
        }
    }

}
