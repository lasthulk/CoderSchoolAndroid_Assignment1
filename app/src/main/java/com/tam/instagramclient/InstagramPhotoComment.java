package com.tam.instagramclient;

/**
 * Created by toan on 3/14/2016.
 */
public class InstagramPhotoComment {
    private String text;
    private String userName;
    private String profilePicture;

    public InstagramPhotoComment() {
        this.text = "";
        this.userName = "";
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
