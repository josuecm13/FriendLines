package com.friendlines.controller.listeners;

import com.friendlines.model.Friendship;

public interface FriendshipEventListener {
    void onFriendshipAdded(Friendship friendship);
    void onFriendshipAccepted(Friendship friendship);
    void onFriendshipRejected(Friendship friendship);
}
