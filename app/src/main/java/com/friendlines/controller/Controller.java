package com.friendlines.controller;

import android.content.Context;

import com.friendlines.controller.manager.Manager;
import com.friendlines.controller.manager.PostManager;
import com.friendlines.controller.manager.UserManager;
import com.google.firebase.auth.FirebaseUser;

public class Controller implements IController
{
    private DTO dto;
    private static final Controller instance = new Controller();
    private Manager userManager;
    private Manager postManager;

    private Controller()
    {
        userManager = new UserManager();
        postManager = new PostManager();
    }

    public void login(String email, String password, Context context)
    {
        //TODO: login firebase
    }

    private void updateUI(FirebaseUser firebaseUser, Context context)
    {
        //TODO updateUI
        if(firebaseUser == null)
        {
            return;
        }
    }

    @Override
    public Boolean registerUser() {
        return null;
    }

    @Override
    public Boolean deleteUser() {
        return null;
    }

    @Override
    public Boolean updateUser() {
        return null;
    }

    @Override
    public Boolean getUser() {
        return null;
    }

    @Override
    public Boolean logout() {
        return null;
    }

    @Override
    public Boolean recoverPassword() {
        return null;
    }

    @Override
    public Boolean createComment() {
        return null;
    }

    @Override
    public Boolean getPost(int idPost) {
        return null;
    }

    @Override
    public Boolean createPost() {
        return null;
    }

    @Override
    public Boolean deletePost() {
        return null;
    }

    @Override
    public Boolean likePost(int idPost) {
        return null;
    }

    @Override
    public Boolean dislikePost(int idPost) {
        return null;
    }

    public DTO getDto() {
        return dto;
    }

    public static Controller getInstance() {
        return instance;
    }
}
