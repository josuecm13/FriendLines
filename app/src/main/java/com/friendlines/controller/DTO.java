package com.friendlines.controller;

import com.friendlines.model.post.Post;
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

    public User getUser()
    {
        if(user == null)
            user = new User();
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost()
    {
        if(post == null)
            post = new Post();
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
