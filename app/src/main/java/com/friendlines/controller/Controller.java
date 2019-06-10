package com.friendlines.controller;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.friendlines.controller.dao.PostDAO;
import com.friendlines.controller.dao.UserDAO;
import com.friendlines.controller.listeners.SignUpListener;
import com.friendlines.controller.listeners.UserEventListener;
import com.friendlines.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

    public void registerUserAuthentication(Activity activity, String email, String password, final SignUpListener listener){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    Log.d(TAG, "registerUserAuthentication onComplete: failed");
                    Log.d(TAG, task.getException().getMessage());
                    listener.onFailure("Authentication failed.");
                }
            }
        });
    }

    public void signInUserAuthentication(String email, String password) throws ControlException{
        auth.signInWithEmailAndPassword(email, password);
        if(auth.getCurrentUser() == null)
            throw new ControlException("Invalid Sign-In credentials.");
    }

    public void addUser(){
        userDAO.addUser(dto.getUser());
    }

    public void updateUser(User user) throws ControlException{
        userDAO.updateUser(user);
    }

    public void deleteUser(String userID) throws ControlException{
        userDAO.deleteUser(userID);
    }

    public void listenUser(Activity activity, String user_id, UserEventListener listener) throws ControlException {
        userDAO.listen(activity, user_id, listener);
    }

    public void listenUser(Activity activity, FirebaseUser user, UserEventListener listener) throws ControlException {
        userDAO.listen(activity, user, listener);
    }

    public static Controller getInstance(){ return instance; }
}
