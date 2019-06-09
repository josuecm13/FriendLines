package com.friendlines.controller.dao;

<<<<<<< HEAD

import com.friendlines.controller.ControlException;
import com.friendlines.controller.listeners.UserEventListener;
import com.friendlines.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

import javax.annotation.Nullable;

public class UserDAO
{
    private static final String COLLECTION = "users";

    DocumentReference document;
    UserEventListener listener;

    public UserDAO(User user, UserEventListener listener) throws ControlException{
        if(user.id == null)
            throw new ControlException("A user document ID must be provided to update a user.");
        else {
            this.listener = listener;
            this.document = FirebaseFirestore.getInstance().collection(COLLECTION).document(user.id);
            this.document.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    //manejar listener;
                }
            });
        }
    }

    public static void addUser(User user) throws ControlException{
        FirebaseFirestore.getInstance().collection(COLLECTION).add(user);
    }

    public static void updateUser(User user) throws ControlException {
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
}
