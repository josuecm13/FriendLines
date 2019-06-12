package com.friendlines.controller.listeners;

import com.friendlines.model.post.Post;

public interface PostEventListener {
    void onPostAdded(Post post);
    void onPostChanged(Post post);
    void onPostDeleted(Post post);
}