package com.friendlines.controller.dao;

import android.util.Log;

import com.friendlines.controller.Controller;
import com.friendlines.model.User;
import com.google.firebase.Timestamp;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOTest {

    @Test
    public void getUser() {

    }

    @Test
    public void addUser() {
        UserDAO dao = new UserDAO();
        User user = new User("auth_id", "Alejandro", "Schmidt", "ajoscram@gmail.com", "img.com", Timestamp.now(), 88903408, "MALE", "Cartago", "Costa Rica");
        dao.addUser(user);
        Log.d(Controller.TAG, "getUser: done");
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void listen() {
    }
}