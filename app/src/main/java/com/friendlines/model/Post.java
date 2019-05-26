package com.friendlines.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post
{
    protected User user;
    protected String text;
    protected LocalDateTime publishedAt;
    protected List<Post> comments;
    protected List<User> likes;
    protected List<User> dislikes;

    public Post()
    {
        setComments(new ArrayList<Post>());
        setLikes(new ArrayList<User>());
        setDislikes(new ArrayList<User>());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public List<Post> getComments() {
        return comments;
    }

    public void setComments(List<Post> comments) {
        this.comments = comments;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public List<User> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<User> dislikes) {
        this.dislikes = dislikes;
    }
}
