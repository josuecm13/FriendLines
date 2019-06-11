package com.friendlines.controller;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.friendlines.controller.dao.CommentDAO;
import com.friendlines.controller.dao.EducationDAO;
import com.friendlines.controller.dao.FriendshipDAO;
import com.friendlines.controller.dao.LikeDAO;
import com.friendlines.controller.dao.PostDAO;
import com.friendlines.controller.dao.UserDAO;
import com.friendlines.controller.listeners.EducationEventListener;
import com.friendlines.controller.listeners.FriendshipEventListener;
import com.friendlines.controller.listeners.PostEventListener;
import com.friendlines.controller.listeners.TaskListener;
import com.friendlines.controller.listeners.UserEventListener;
import com.friendlines.model.User;
import com.friendlines.model.post.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.annotation.Nonnull;

public class Controller
{
    public static final String TAG = "FriendLines";
    private static final Controller instance = new Controller();

    private DTO dto;
    private UserDAO userDAO;
    private EducationDAO educationDAO;
    private FriendshipDAO friendshipDAO;
    private PostDAO postDAO;
    private CommentDAO commentDAO;
    private LikeDAO likeDAO;

    private FirebaseAuth auth;

    private Controller() {
        this.dto = new DTO();
        this.userDAO = new UserDAO();
        this.educationDAO = new EducationDAO();
        this.friendshipDAO = new FriendshipDAO();
        this.postDAO = new PostDAO();
        this.commentDAO = new CommentDAO();
        this.likeDAO = new LikeDAO();
        this.auth = FirebaseAuth.getInstance();
    }

    public DTO getDto(){
        return dto;
    }

    //authentication
    public void resetPassword(String email){
        auth.sendPasswordResetEmail(email);
    }

    public void register(Activity activity, String email, String password, final TaskListener listener){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@Nonnull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess(null);
                } else {
                    Log.d(TAG, "Controller.registerUserAuthentication onComplete: failed");
                    Log.d(TAG, task.getException().getMessage());
                    listener.onFailure(new ControlException("Registration failed."));
                }
            }
        });
    }

    public void signIn(Activity activity, String email, String password, final TaskListener listener) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    listener.onSuccess(null);
                else{
                    Log.d(TAG, "Controller.signIn email&password onComplete: failed");
                    Log.d(TAG, task.getException().getMessage());
                    listener.onFailure(new ControlException("Sign-in failed."));
                }
            }
        });
    }

    public void signIn(Activity activity, final TaskListener listener){
        auth.getCurrentUser().reload().addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    listener.onSuccess(null);
                else{
                    Log.d(TAG, "Control.signIn onComplete: failed");
                    Log.d(TAG, task.getException().getMessage());
                    listener.onFailure(new ControlException("User was deleted or its password reset."));
                }
            }
        });
    }

    public void signOut(){
        auth.signOut();
    }

    public boolean singedIn(){
        return auth.getCurrentUser() != null;
    }

    //user
    public void addUser(){
        userDAO.addUser(dto.getUser());
    }

    public void updateUser() throws ControlException{
        userDAO.updateUser(dto.getUser());
    }

    public void deleteUser(String user_id) throws ControlException{
        userDAO.deleteUser(user_id);
    }

    public void listenUser(Activity activity, String user_id, UserEventListener listener) throws ControlException {
        userDAO.listen(activity, user_id, listener);
    }

    public void listenUser(Activity activity, UserEventListener listener) throws ControlException {
        if(auth.getCurrentUser() == null)
            throw new ControlException("No user is currently logged in.");
        else
            userDAO.listen(activity, auth.getCurrentUser(), listener);
    }

    //NOTE: queries both the firstname and the lastname of all users
    public void queryUsers(Activity activity, String name, TaskListener<User> listener) throws ControlException{
        userDAO.query(activity, UserDAO.USER_FIRSTNAME_FIELD_NAME, name, listener);
        userDAO.query(activity, UserDAO.USER_LASTNAME_FIELD_NAME, name, listener);
    }

    //education
    public void addEducation(String user_id) throws ControlException{
        educationDAO.add(user_id, dto.getEducation());
    }

    public void updateEducation(String user_id) throws ControlException{
        educationDAO.update(user_id, dto.getEducation());
    }

    public void deleteEducation(String user_id, String education_id) throws ControlException{
        educationDAO.delete(user_id, education_id);
    }

    public void listenEducation(Activity activity, String user_id, EducationEventListener listener) throws ControlException{
        educationDAO.listen(activity, user_id, listener);
    }

    //friendship
    public void addFriendship() throws ControlException{
        friendshipDAO.add(dto.getFriendship());
    }

    public void acceptFriendship(String friendship_id) throws ControlException{
        friendshipDAO.accept(friendship_id);
    }

    public void rejectFriendship(String friendship_id) throws ControlException{
        friendshipDAO.reject(friendship_id);
    }

    public void listenFriendship(Activity activity, String user_id, FriendshipEventListener listener) throws ControlException{
        friendshipDAO.listen(activity, user_id, FriendshipDAO.SENDER_ID_FIELD_NAME, listener);
        friendshipDAO.listen(activity, user_id, FriendshipDAO.RECEIVER_ID_FIELD_NAME, listener);
    }

    //posts
    public void addPost(){
        postDAO.add(dto.getPost());
    }

    public void updatePost() throws ControlException{
        postDAO.update(dto.getPost());
    }

    public void deletePost(String post_id) throws ControlException{
        postDAO.delete(post_id);
    }

    public void listenPost(Activity activity, String user_id, PostEventListener listener) throws ControlException{
        postDAO.listen(activity, user_id, listener);
    }

    public void listenPost(Activity activity, PostEventListener listener){
        postDAO.listen(activity, listener);
    }

    public void queryPosts(Activity activity, String text, TaskListener<Post> listener){
        postDAO.query(activity, text, listener);
    }

    //comments
    public void addComment(String post_id) throws ControlException{
        commentDAO.add(post_id, dto.getComment());
    }

    public void updateComment(String post_id) throws ControlException{
        commentDAO.update(post_id, dto.getComment());
    }

    public void deleteComment(String post_id, String comment_id) throws ControlException{
        commentDAO.delete(post_id, comment_id);
    }

    //NOTE: post_id refers to the parent post id, not the comment itself.
    public void listenComment(Activity activity, String post_id, PostEventListener listener) throws ControlException{
        commentDAO.listen(activity, post_id, listener);
    }

    public static Controller getInstance(){ return instance; }
}

