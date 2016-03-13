package com.tam.instagramclient;

import android.text.format.DateUtils;

import java.util.ArrayList;

/**
 * Created by toan on 3/13/2016.
 */
public class InstagramPhoto {

    private String userName;
    private String caption;
    private String imageUrl;
    private int imageHeight;
    private int likesContent;
    private String profilePicture;
    private String createdAt;
    private int likesCount;
    private ArrayList<InstagramPhotoComment> comments;


    public InstagramPhoto() {
        this.userName = "";
        this.caption = "";
        this.caption = "";
        this.imageHeight = 0;
        this.likesContent = 0;
    }

    public ArrayList<InstagramPhotoComment> getComments() {
        if (comments == null) {
            comments = new ArrayList<InstagramPhotoComment>();
        }
        return comments;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long longCreatedAt) {
        String result = DateUtils.getRelativeTimeSpanString(longCreatedAt * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        this.createdAt = result;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getLikesContent() {
        return likesContent;
    }

    public void setLikesContent(int likesContent) {
        this.likesContent = likesContent;
    }
}
