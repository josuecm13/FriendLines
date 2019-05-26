package com.friendlines.controller.manager;

import com.friendlines.controller.dao.DAO;

public abstract class Manager
{
    protected DAO dao;
    abstract Boolean register(Object object);
    abstract Boolean delete(Object object);
    abstract Boolean update(Object oldObject, Object newObject);
    abstract Object get(Object object);
}
