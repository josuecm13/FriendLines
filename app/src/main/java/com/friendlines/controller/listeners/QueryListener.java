package com.friendlines.controller.listeners;

import com.friendlines.controller.ControlException;

public interface QueryListener<T> {
    void onSuccess(T object);
    void onError(ControlException exception);
}
