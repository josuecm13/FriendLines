package com.friendlines.controller.manager;

import com.friendlines.controller.dao.UserDAO;

public class UserManager extends Manager
{
    public UserManager()
    {
        super();
        dao = new UserDAO();
    }
    @Override
    Boolean register(Object object) {
        return null;
    }

    @Override
    Boolean delete(Object object) {
        return null;
    }

    @Override
    Boolean update(Object oldObject, Object newObject) {
        return null;
    }

    @Override
    Object get(Object object) {
        return null;
    }
}
