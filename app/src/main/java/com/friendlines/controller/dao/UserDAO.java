package com.friendlines.controller.dao;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.listeners.UserEventListener;
import com.friendlines.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

public class UserDAO
{
    private static final String COLLECTION = "users";

    public UserDAO(){}

    public User getUser(String auth_id) throws ControlException{
        final ArrayList<User> users = new ArrayList();
        FirebaseFirestore.getInstance().collection(COLLECTION).whereEqualTo("auth_id", auth_id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                User user = document.toObject(User.class);
                                user.id = document.getId();
                                users.add(user);
                            }
                        }
                        else
                            Log.d(Controller.TAG, "UserDAO getUser failed: " + task.getException());
                    }
                });
        if(users.size() == 0)
            throw new ControlException("User not found");
        return users.get(0);
    }

    public void addUser(User user){
        FirebaseFirestore.getInstance().collection(COLLECTION).add(user);
    }

    public void updateUser(User user) throws ControlException {
        if (user.id == null)
            throw new ControlException("A user document ID must be provided to update a user.");
        else {
            HashMap<String, Object> map = new HashMap();
            map.put("auth_id", user.auth_id);
            map.put("firstname", user.firstname);
            map.put("lastname", user.lastname);
            map.put("image", user.image);
            map.put("birthday", user.birthday);
            map.put("phone", user.phone);
            map.put("gender", user.gender);
            map.put("city", user.city);
            map.put("country", user.country);
            FirebaseFirestore.getInstance().collection(COLLECTION).document(user.id).update(map);
        }
    }

    public void deleteUser(User user) throws ControlException{
        if(user.id == null)
            throw new ControlException("A user document ID must be provided to delete a user.");
        else{
            FirebaseFirestore.getInstance().collection(COLLECTION).document(user.id).delete();
        }
    }

    public void listen(Activity activity, User user, final UserEventListener listener) throws ControlException{
        if(user.id == null)
            throw new ControlException("A user document ID must be provided to listen to a user.");
        else {
            FirebaseFirestore.getInstance().collection(COLLECTION).document(user.id).addSnapshotListener(activity, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(e != null){
                        Log.d(Controller.TAG, "UserDAO listen failed: " + e);
                    }
                    else{
                        User user = documentSnapshot.toObject(User.class);
                        if(documentSnapshot.exists())
                            listener.onUserChanged(user);
                        else{
                            listener.onUserDeleted(user);
                        }
                    }
                }
            });
        }
    }
}
