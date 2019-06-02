package com.friendlines.model;

import java.time.LocalDateTime;

public abstract class Notification
{
    protected LocalDateTime createdAt;
    protected String title;
    protected Picture image;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Picture getImage() {
        return image;
    }

    public void setImage(Picture image) {
        this.image = image;
    }
}
