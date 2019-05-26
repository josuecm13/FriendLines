package com.friendlines.controller.dao;

import com.friendlines.controller.manager.FirebaseManager;

public abstract class DAO
{
    protected FirebaseManager firebaseManager;
    protected DAO()
    {
        firebaseManager = new FirebaseManager();
    }
    abstract Boolean register(Object object);
    abstract Boolean delete(Object object);
    abstract Boolean update(Object oldObject, Object newObject);
    abstract Object get(Object object);
}
