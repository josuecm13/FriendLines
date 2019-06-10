package com.friendlines.model;

import com.google.firebase.Timestamp;

public class Friendship extends Identifiable
{
    //valid status states
    public static final String PENDING_STATUS = "PENDING";
    public static final String ACCEPTED_STATUS = "ACCEPTED";

    private String sender_id;
    private String sender_name;
    private String sender_image;
    private String receiver_id;
    private String receiver_name;
    private String receiver_image;
    private String status;
    private Timestamp created;

    public Friendship(){}

    public Friendship(String sender_id, String sender_name, String sender_image, String receiver_id, String receiver_name, String receiver_image, String status, Timestamp created) {
        this.sender_id = sender_id;
        this.sender_name = sender_name;
        this.sender_image = sender_image;
        this.receiver_id = receiver_id;
        this.receiver_name = receiver_name;
        this.receiver_image = receiver_image;
        this.status = status;
        this.created = created;
    }

    public String getSender_id() {
        return sender_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getSender_image() {
        return sender_image;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_image() {
        return receiver_image;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public void setSender_image(String sender_image) {
        this.sender_image = sender_image;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public void setReceiver_image(String receiver_image) {
        this.receiver_image = receiver_image;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
