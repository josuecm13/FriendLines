package com.friendlines.model;

public class FriendRequest extends Notification
{
    private User requester;

    public FriendRequest()
    {
        super();
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }
}
