package com.friendlines.controller.listeners;

import com.friendlines.model.Like;

public interface LikeEventListener {
    void onLikeAdded(Like like);
    void onLikeChanged(Like like);
    void onLikeRemoved(Like like);
}
