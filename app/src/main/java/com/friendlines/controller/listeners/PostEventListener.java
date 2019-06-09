package com.friendlines.controller.listeners;

import com.friendlines.model.post.Post;

public interface PostEventListener {
    void onPostAdded(String string, Post post);
    void onPostUpdated(Post post);
    void onPostDeleted(Post post);
    void onPostLiked(Post post);
    void onPostDisliked(Post post);
}