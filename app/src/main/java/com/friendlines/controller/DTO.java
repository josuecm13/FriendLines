package com.friendlines.controller;

import com.friendlines.model.Post;
import com.friendlines.model.User;

public class DTO
{
    private User user;
    private Post post;
    private Post[] posts;

    public DTO()
    {
        posts = new Post[10];
    }

    public User getUser() {
        return user == null ? new User() : user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
    }
}
