package com.friendlines.controller.listeners;

import com.friendlines.model.User;

public interface UserEventListener {
    void onUserSignUp(User user);
    void onUserSignIn(User user);
    void onUserChanged(User user);
    void onUserDeleted(User user);
}