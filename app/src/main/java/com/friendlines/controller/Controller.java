package com.friendlines.controller;

import android.app.Activity;

import com.friendlines.controller.dao.PostDAO;
import com.friendlines.controller.dao.UserDAO;
import com.friendlines.controller.listeners.UserEventListener;
import com.friendlines.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Controller
{
    public static final String TAG = "FriendLines";
    private static final Controller instance = new Controller();

    private DTO dto;
    private UserDAO userDAO;
    private PostDAO postDAO;
    private FirebaseAuth auth;

    private Controller() {
        this.dto = new DTO();
        this.userDAO = new UserDAO();
        this.postDAO = new PostDAO();
        this.auth = FirebaseAuth.getInstance();
    }

    public DTO getDto(){
        return dto;
    }

    public void sendPasswordResetEmail(String email){
        auth.sendPasswordResetEmail(email);
    }

    public User getUser(FirebaseUser user) throws ControlException{
        return userDAO.getUser(user.getUid());
    }

    public void registerUserAuthentication(String email, String password){
        auth.createUserWithEmailAndPassword(email, password);
    }

    public void addUser(){
        userDAO.addUser(dto.getUser());
    }

    public void updateUser() throws ControlException{
        userDAO.updateUser(dto.getUser());
    }

    public void deleteUser() throws ControlException{
        userDAO.deleteUser(dto.getUser());
    }

    public void listen(Activity activity, User user, UserEventListener listener) throws ControlException {
        userDAO.listen(activity, user, listener);
    }

    public static Controller getInstance(){ return instance; }
}
