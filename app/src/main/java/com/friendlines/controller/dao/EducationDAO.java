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

    public void add(Education education){
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME).add(education);
    }

    public void update(Education education) throws ControlException {
        if(education.getId() == null)
            throw new ControlException("An education ID must be provided to update");
        else {
            HashMap<String, Object> map = new HashMap();
            map.put("auth_id", education.getType());
            map.put("firstname", education.getInstitution());
            FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(education.getId()).update(map);
        }
    }

    public void delete(String id){
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(id).delete();
    }

    public void listen(Activity activity, String user_id, final EducationEventListener listener) throws ControlException{
        if(user_id == null)
            throw new ControlException("A user ID must be provided to listen to its education changes.");
        else {
            FirebaseFirestore.getInstance().collection(UserDAO.COLLECTION_NAME)
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
