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
    Boolean register(Object object)
    {
        return ((UserDAO)dao).register(object);
    }

    @Override
    Boolean delete(Object object)
    {
        return ((UserDAO)dao).delete(object);
    }

    @Override
    Boolean update(Object oldObject, Object newObject)
    {
        return ((UserDAO)dao).update(oldObject, newObject);
    }

    @Override
    Object get(Object object)
    {
        return ((UserDAO)dao).get(object);
    }
}
