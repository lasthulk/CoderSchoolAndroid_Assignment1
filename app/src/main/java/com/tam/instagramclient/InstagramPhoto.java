package com.tam.instagramclient;

/**
 * Created by toan on 3/13/2016.
 */
public class InstagramPhoto {

    private String userName;
    private String caption;
    private String imageUrl;
    private int imageHeight;
    private int likesContent;

    public InstagramPhoto() {
        this.userName = "";
        this.caption = "";
        this.caption = "";
        this.imageHeight = 0;
        this.likesContent = 0;
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
