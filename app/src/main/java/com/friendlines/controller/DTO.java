package com.friendlines.controller;

import com.friendlines.model.Education;
import com.friendlines.model.Friendship;
import com.friendlines.model.post.Post;
import com.friendlines.model.User;

import java.util.ArrayList;
import java.util.List;

public class DTO
{
    private User user;
    private Education education;
    private Friendship friendship;
    private Post post;
    private Post comment;
    private List<Post> posts;
    private List<Post> comments;
    private List<Friendship> friendships;

    public DTO()
    {
        posts = new ArrayList<>();
        comments = new ArrayList<>();
        friendships = new ArrayList<>();
    }

    public User getUser()
    {
        if(user == null)
            user = new User();
        return user;
    }

    public List<Friendship> getFriendships(){return friendships;}

    public List<Post> getComments(){return comments;}

    public List<Post> getPosts(){return posts;}

    public void setUser(User user) {
        this.user = user;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Friendship getFriendship() {
        return friendship;
    }

    public void setFriendship(Friendship friendship) {
        this.friendship = friendship;
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

    public Post getComment() {
        return comment;
    }

    public void setComment(Post comment) {
        this.comment = comment;
    }
}
