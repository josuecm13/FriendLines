package com.friendlines.controller;

import android.content.Context;

import com.friendlines.controller.dao.PostDAO;
import com.friendlines.controller.dao.UserDAO;
import com.friendlines.controller.listeners.EducationEventListener;
import com.friendlines.controller.listeners.UserEventListener;
import com.friendlines.model.User;

import java.util.ArrayList;

public class Controller
{
    private static final Controller instance = new Controller();

    private ArrayList<UserDAO> userDAOs;
    private ArrayList<PostDAO> postDAOs;

    private Controller() {
        userDAOs = new ArrayList();
    }

    public static Controller getInstance(){ return instance; }

    public void listen(User user, UserEventListener listener) {
        userDAOs.add(new UserDAO(user, listener));
    }
}
