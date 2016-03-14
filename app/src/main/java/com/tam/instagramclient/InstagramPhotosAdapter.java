package com.tam.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by toan on 3/13/2016.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public final static String INSTAGRAM_COLOR_CODE = "#3f729b";

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
        holder.tvViewAllComments.setTag(position);
        String captionHtmlFormat = "<b><font color='" + INSTAGRAM_COLOR_CODE + "'>" + photo.getUserName() + "</font></b> " + photo.getCaption();
        holder.tvCaption.setText(Html.fromHtml(captionHtmlFormat));
        holder.tvUsername.setText(Html.fromHtml(formatUserName(photo.getUserName())));
        holder.tvCreatedAt.setText(photo.getCreatedAt());
        holder.tvLikesCount.setText(String.valueOf(photo.getLikesCount()));
        AddViewComments(photo, holder);
        DisplayPhotos(photo, holder);


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

    public ArrayList<int[]> getSpans(String body, char prefix) {
        ArrayList<int[]> spans = new ArrayList<int[]>();

        Pattern pattern = Pattern.compile(prefix + "\\w+");
        Matcher matcher = pattern.matcher(body);

        // Check all occurrences
        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            spans.add(currentSpan);
        }

        return  spans;
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
                tvCommentUserName.setText(Html.fromHtml(formatUserName(comment.getUserName())));
                String commentText = comment.getText();
                SpannableStringBuilder commentSpan = formatComment(commentText);
                tvCommentText.setText(commentSpan);
                holder.linearComments.addView(viewComment);
                count++;
                if(count == 2) {
                    break;
                }
            }
        }
    }

    private String formatUserName(String userName) {
        return "<font color='" + INSTAGRAM_COLOR_CODE + "'>" + userName + "</font>";
    }

    @NonNull
    private SpannableStringBuilder formatComment(String commentText) {
        ArrayList<int[]> hashtagSpans = getSpans(commentText, '#');
        ArrayList<int[]> usertagSpans = getSpans(commentText, '@');
        SpannableStringBuilder commentSpan = new SpannableStringBuilder(commentText);
        ForegroundColorSpan colorStyle = new ForegroundColorSpan(Color.parseColor(INSTAGRAM_COLOR_CODE));
        StyleSpan boldStyle = new StyleSpan(Typeface.BOLD);
        for (int j = 0; j < hashtagSpans.size(); j++) {
            int[] span = hashtagSpans.get(j);
            int hashtagStart = span[0];
            int hashtagEnd = span[1];
            commentSpan.setSpan(colorStyle, hashtagStart, hashtagEnd, 0);
            commentSpan.setSpan(boldStyle, hashtagStart, hashtagEnd, 0);
        }
        for (int m = 0; m < usertagSpans.size(); m++) {
            int[] span = usertagSpans.get(m);
            int usertagStart = span[0];
            int usertagEnd = span[1];
            commentSpan.setSpan(colorStyle, usertagStart, usertagEnd, 0);
            commentSpan.setSpan(boldStyle, usertagStart, usertagEnd, 0);
        }
        return commentSpan;
    }

}
