package com.friendlines.controller.dao;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.listeners.QueryListener;
import com.friendlines.controller.listeners.UserEventListener;
import com.friendlines.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.HashMap;

import javax.annotation.Nullable;

public class UserDAO
{
    public static final String COLLECTION_NAME = "users";
    //valid field names for user names
    public static final String USER_FIRSTNAME_FIELD_NAME = "firstname";
    public static final String USER_LASTNAME_FIELD_NAME = "lastname";

    public UserDAO(){}

    public void addUser(User user){
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME).add(user);
    }

    public void updateUser(User user) throws ControlException {
        if (user.getId() == null)
            throw new ControlException("A user document ID must be provided to update a user.");
        else {
            HashMap<String, Object> map = new HashMap();
            map.put("auth_id", user.getAuth_id());
            map.put("firstname", user.getFirstname());
            map.put("lastname", user.getLastname());
            map.put("image", user.getImage());
            map.put("birthday", user.getBirthday());
            map.put("phone", user.getPhone());
            map.put("gender", user.getGender());
            map.put("city", user.getCity());
            map.put("country", user.getCountry());
            FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(user.getId()).update(map);
        }
    }

    public void deleteUser(String user_id) throws ControlException{
        if(user_id == null)
            throw new ControlException("A user ID must be provided to delete.");
        else
            FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(user_id).delete();
    }

    public void listen(Activity activity, FirebaseUser user, final UserEventListener listener) throws ControlException{
        if(user == null)
            throw new ControlException("A FirebaseUser must be provided to listen to a user.");
        else {
            FirebaseFirestore.getInstance().collection(COLLECTION_NAME).whereEqualTo("auth_id", user.getUid()).addSnapshotListener(activity, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                    if(e != null)
                        Log.d(Controller.TAG, "UserDAO listen error: " + e);
                    else{
                        for(DocumentChange change : querySnapshot.getDocumentChanges()){
                            User user = change.getDocument().toObject(User.class);
                            user.setId(change.getDocument().getId());
                            switch(change.getType()){
                                case ADDED:
                                    listener.onUserChanged(user);
                                    break;
                                case MODIFIED:
                                    listener.onUserChanged(user);
                                    break;
                                case REMOVED:
                                    listener.onUserDeleted(user);
                                    break;
                            }
                        }
                    }
                }
            });
        }
    }

    public void listen(Activity activity, String user_id, final UserEventListener listener) throws ControlException{
        if(user_id == null)
            throw new ControlException("A user ID must be provided to listen to its changes.");
        else {
            FirebaseFirestore.getInstance()
                    .collection(COLLECTION_NAME)
                    .document(user_id)
                    .addSnapshotListener(activity, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(e != null)
                        Log.d(Controller.TAG, "UserDAO listen error: " + e);
                    else{
                        User user = documentSnapshot.toObject(User.class);
                        user.setId(documentSnapshot.getId());
                        if(!documentSnapshot.exists())
                            listener.onUserDeleted(user); //MISSING: Delete user remnants
                        else{
                            listener.onUserChanged(user);
                        }
                    }
                }
            });
        }
    }

    private String getPrefixSuccessor(String string) {
        if (string.equals(""))
            return string;
        else {
            int last = string.charAt(string.length() - 1);
            if (string.length() == 1)
                return String.valueOf((char)last+1);
            else
                return string.substring(0, string.length() - 2) + ((char)last+1);
        }
    }

    //matches any string field which starts with the specified prefix
    public void query(Activity activity, final String field, final String prefix, final QueryListener<User> listener) throws ControlException{
        if(prefix == null || prefix.equals(""))
            throw new ControlException("Text needed to perform search.");
        else if(!field.equals(USER_FIRSTNAME_FIELD_NAME) && !field.equals(USER_LASTNAME_FIELD_NAME))
            throw new ControlException("Invalid field parameter in UserDAO's listen method: "+field);
        else {
            final String successor = getPrefixSuccessor(prefix);
            FirebaseFirestore.getInstance()
                    .collection(COLLECTION_NAME)
                    .whereGreaterThanOrEqualTo(field, prefix)
                    .whereLessThan(field, successor)
                    .get().addOnCompleteListener(activity, new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (!task.isSuccessful()) {
                        listener.onError(new ControlException("On UserDAO.query with field = " + field
                                + ", prefix  = " + prefix +
                                ", successor = " + successor));
                    } else {
                        for (DocumentChange change : task.getResult().getDocumentChanges()) {
                            User user = change.getDocument().toObject(User.class);
                            user.setId(change.getDocument().getId());
                            listener.onSuccess(user);
                        }
                    }
                }
            });
        }
    }
}
