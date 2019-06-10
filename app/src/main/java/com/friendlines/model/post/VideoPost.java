package com.friendlines.model.post;

import com.google.firebase.Timestamp;

public class VideoPost extends Post
{
    public static final String TYPE = "VIDEO";

    private String video;

    public VideoPost(){
        super();
    }

    public VideoPost(String user_id, String user_name, String user_image, String type, String text, Timestamp created, String video) {
        super(user_id, user_name, user_image, type, text, created);
        this.video = video;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
