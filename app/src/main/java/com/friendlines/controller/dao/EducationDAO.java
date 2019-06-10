package com.friendlines.controller.dao;

import android.app.Activity;
import android.util.Log;

import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.listeners.EducationEventListener;
import com.friendlines.model.Education;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import javax.annotation.Nullable;

public class EducationDAO {

    public static final String COLLECTION_NAME = "educations";

    public EducationDAO(){}

    public void add(String user_id, Education education) throws ControlException{
        if(user_id == null)
            throw new ControlException("A user ID must be provided to add an education to.");
        else
            FirebaseFirestore.getInstance()
                    .collection(UserDAO.COLLECTION_NAME)
                    .document(user_id)
                    .collection(COLLECTION_NAME)
                    .add(education);
    }

    public void update(String user_id, Education education) throws ControlException {
        if(user_id == null)
            throw new ControlException("An user_id must be provided to update its education.");
        if(education.getId() == null)
            throw new ControlException("An education ID must be provided to update.");
        else {
            HashMap<String, Object> map = new HashMap();
            map.put("institution", education.getInstitution());
            map.put("type", education.getType());
            FirebaseFirestore.getInstance()
                    .collection(UserDAO.COLLECTION_NAME)
                    .document(user_id)
                    .collection(COLLECTION_NAME)
                    .document(education.getId())
                    .update(map);
        }
    }

    public void delete(String user_id, String education_id) throws ControlException{
        if(user_id == null)
            throw new ControlException("An user_id must be provided to delete its education.");
        else if(education_id == null)
            throw new ControlException("An education ID must be provided to delete.");
        else
            FirebaseFirestore.getInstance()
                    .collection(UserDAO.COLLECTION_NAME)
                    .document(user_id)
                    .collection(COLLECTION_NAME)
                    .document(education_id)
                    .delete();
    }

    public void listen(Activity activity, String user_id, final EducationEventListener listener) throws ControlException{
        if(user_id == null)
            throw new ControlException("A user ID must be provided to listen to its educations.");
        else {
            FirebaseFirestore.getInstance()
                    .collection(UserDAO.COLLECTION_NAME)
                    .document(user_id)
                    .collection(COLLECTION_NAME)
                    .addSnapshotListener(activity, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                    if(e != null)
                        Log.d(Controller.TAG, "EducationDAO listen error: " + e);
                    else{
                        for(DocumentChange change : querySnapshot.getDocumentChanges()){
                            Education education = change.getDocument().toObject(Education.class);
                            education.setId(change.getDocument().getId());
                            switch(change.getType()){
                                case ADDED:
                                    listener.onEducationAdded(education);
                                    break;
                                case MODIFIED:
                                    listener.onEducationChanged(education);
                                    break;
                                case REMOVED:
                                    listener.onEducationDeleted(education);
                                    break;
                            }
                        }
                    }
                }
            });
        }
    }
}
