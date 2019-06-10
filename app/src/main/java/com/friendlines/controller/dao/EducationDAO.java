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

    public static final String COLLECTION = "educations";

    public EducationDAO(){}

    public void add(Education education){
        FirebaseFirestore.getInstance().collection(COLLECTION).add(education);
    }

    public void update(Education education) throws ControlException {
        if(education.id != null)
            throw new ControlException("An education ID must be provided to update");
        else {
            HashMap<String, Object> map = new HashMap();
            map.put("auth_id", education.type);
            map.put("firstname", education.institution);
            FirebaseFirestore.getInstance().collection(COLLECTION).document(education.id).update(map);
        }
    }

    public void delete(String id){
        FirebaseFirestore.getInstance().collection(COLLECTION).document(id).delete();
    }

    public void listen(Activity activity, String user_id, final EducationEventListener listener) throws ControlException{
        if(user_id == null)
            throw new ControlException("A user ID must be provided to listen to its education changes.");
        else {
            FirebaseFirestore.getInstance().collection(COLLECTION).whereEqualTo("user_id", user_id).addSnapshotListener(activity, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                    if(e != null)
                        Log.d(Controller.TAG, "EducationDAO listen error: " + e);
                    else{
                        for(DocumentChange change : querySnapshot.getDocumentChanges()){
                            Education education = change.getDocument().toObject(Education.class);
                            education.id = change.getDocument().getId();
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
