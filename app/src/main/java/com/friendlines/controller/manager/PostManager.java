package com.friendlines.controller.manager;

import com.friendlines.controller.dao.PostDAO;

public class PostManager extends Manager
{
    public PostManager()
    {
        super();
        dao = new PostDAO();
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
