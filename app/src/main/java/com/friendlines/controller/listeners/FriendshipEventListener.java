package com.friendlines.controller.listeners;

import com.friendlines.model.Friendship;

public interface FriendshipEventListener {
    void onFriendshipRequested(Friendship incoming);
    void onFriendshipAccepted();
    void onFriendshipRejected();
}
