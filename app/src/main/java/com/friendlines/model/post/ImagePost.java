package com.friendlines.model.post;

import com.google.firebase.Timestamp;

public class ImagePost extends Post
{
    public static final String TYPE = "IMAGE";

    private String image;

    public ImagePost(){
        super();
    }

    public ImagePost(String user_id, String user_name, String user_image, String type, String text, Timestamp created, String image) {
        super(user_id, user_name, user_image, type, text, created);
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
