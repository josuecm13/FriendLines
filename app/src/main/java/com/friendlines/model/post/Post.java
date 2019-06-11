package com.friendlines.model.post;

import com.friendlines.model.Identifiable;
import com.google.firebase.Timestamp;

public class Post extends Identifiable
{
    public static final String TYPE = "TEXT";

    private String user_id;
    private String user_name;
    private String user_image;
    private String type;
    private String text;
    private Timestamp created;

    public Post(){}

    public Post(String user_id, String user_name, String user_image, String type, String text, Timestamp created) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_image = user_image;
        this.type = type;
        this.text = text;
        this.created = created;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
