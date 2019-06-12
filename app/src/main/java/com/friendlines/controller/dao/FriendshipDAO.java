package com.friendlines.controller.dao;

import android.app.Activity;
import android.util.Log;

import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.listeners.FriendshipEventListener;
import com.friendlines.model.Friendship;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import javax.annotation.Nullable;

public class FriendshipDAO {
    public static final String COLLECTION_NAME = "friendships";
    //valid field names in the listen method
    public static final String SENDER_ID_FIELD_NAME = "sender_id";
    public static final String RECEIVER_ID_FIELD_NAME = "receiver_id";

    public FriendshipDAO(){}

    public void add(Friendship friendship){
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME).add(friendship);
    }

    public void accept(String friendship_id) throws ControlException {
        if(friendship_id == null)
            throw new ControlException("A friendship ID must be provided to accept the request.");
        else{
            HashMap<String, Object> map = new HashMap();
            map.put("status", Friendship.ACCEPTED_STATUS);
            FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(friendship_id).update(map);
        }
    }

    public void reject(String friendship_id) throws ControlException {
        if(friendship_id == null)
            throw new ControlException("A friendship ID must be provided to reject the request.");
        else
            FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(friendship_id).delete();
    }

    //REMEMBER: to listen for both sent and received friendships in the controller
    public void listen(Activity activity, String user_id, String query_field, final FriendshipEventListener listener) throws ControlException{
        if(user_id == null)
            throw new ControlException("A user ID must be provided to listen to its friendships.");
        else if(!query_field.equals(SENDER_ID_FIELD_NAME) && !query_field.equals(RECEIVER_ID_FIELD_NAME))
            throw new ControlException("Invalid query_field parameter in FriendshipDAO's listen method: "+query_field);
        else{
            FirebaseFirestore.getInstance().collection(COLLECTION_NAME)
                    .whereEqualTo(query_field, user_id)
                    .addSnapshotListener(activity, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                    if(e != null)
                        Log.d(Controller.TAG, "FriendshipDAO listen error: " + e);
                    else{
                        for(DocumentChange change : querySnapshot.getDocumentChanges()){
                            Friendship friendship = change.getDocument().toObject(Friendship.class);
                            friendship.setId(change.getDocument().getId());
                            switch(change.getType()){
                                case ADDED:
                                    listener.onFriendshipAdded(friendship);
                                    break;
                                case MODIFIED:
                                    if(friendship.getStatus().equals(Friendship.ACCEPTED_STATUS))
                                        listener.onFriendshipAccepted(friendship);
                                    break;
                                case REMOVED:
                                    listener.onFriendshipRejected(friendship);
                            }
                        }
                    }
                }
            });
        }
    }
}
