package com.tam.instagramclient;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by toan on 3/14/2016.
 */
public class InstagramCommentsAdapter extends ArrayAdapter<InstagramPhotoComment> {

    static class CommentViewHolder {
        @Bind(R.id.tvItemCommentText)
        TextView tvItemCommentText;

        @Bind(R.id.tvItemCommentUserName)
        TextView tvItemCommentUserName;

        @Bind(R.id.ivItemCommentUserProfile)
        ImageView ivItemCommentUserProfile;

        public CommentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public InstagramCommentsAdapter(Context context, List<InstagramPhotoComment> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhotoComment comment = getItem(position);
        CommentViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
            holder = new CommentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CommentViewHolder) convertView.getTag();
        }
        holder.tvItemCommentText.setText(comment.getText());
        String userNameFormat = "<b><font color='" + InstagramPhotosAdapter.INSTAGRAM_COLOR_CODE + "'>" + comment.getUserName() + "</font></b>";

        holder.tvItemCommentUserName.setText(Html.fromHtml(userNameFormat));
        Picasso.with(getContext()).load(comment.getProfilePicture())
                .fit()
                .centerCrop()
                .into(holder.ivItemCommentUserProfile);
        return convertView;
    }
}
