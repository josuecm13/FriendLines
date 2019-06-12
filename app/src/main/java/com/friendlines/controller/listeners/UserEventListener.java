package com.friendlines.controller.listeners;

import com.friendlines.model.User;

public interface UserEventListener {
    void onUserChanged(User user);
    void onUserDeleted(User user);
}