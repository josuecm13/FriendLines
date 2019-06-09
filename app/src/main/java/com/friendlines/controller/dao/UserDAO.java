package com.friendlines.controller.dao;

import com.google.firebase.auth.FirebaseAuth;

public class UserDAO extends DAO
{
    public UserDAO()
    {
        super();
    }
    @Override
    public Boolean register(Object object)
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return null;
    }

    @Override
    public Boolean delete(Object object) {
        return null;
    }

    @Override
    public Boolean update(Object oldObject, Object newObject) {
        return null;
    }

    @Override
    public Object get(Object object) {
        return null;
    }
}
