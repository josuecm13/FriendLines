package com.friendlines.controller.dao;

import com.friendlines.model.post.Post;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostDAO
{
    private static final String COLLECTION_NAME = "posts";

    public PostDAO(){}

    public void addPost(Post post)
    {
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME).add(post);
    }
}
