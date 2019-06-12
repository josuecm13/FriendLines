package com.friendlines.controller.listeners;

import com.friendlines.controller.ControlException;

public interface TaskListener<T> {
    void onSuccess(T object);
    void onFailure(ControlException exception);
}
