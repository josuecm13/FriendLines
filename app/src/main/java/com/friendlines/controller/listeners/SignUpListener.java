package com.friendlines.controller.listeners;

public interface SignUpListener {
    void onSuccess();
    void onFailure(String error);
}
