package com.friendlines.controller.manager;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseManager extends Manager
{
    private String collection;

    @Override
    Boolean register(Object object)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection).add(object)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
        {
            @Override
            public void onSuccess(DocumentReference documentReference)
            {
                Log.i("AddObject", "Success");
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.i("AddObject", "Failure");
            }
        });
        return true;
    }

    @Override
    Boolean delete(Object object)
    {
        String id = (String)object;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection).document(id).delete()
        .addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                Log.i("DeleteObject", "Success");
            }
        })
        .addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.i("DeleteObject", "Failure");
            }
        });
        return true;
    }

    //TODO: update
    @Override
    Boolean update(Object oldObject, Object newObject) {
        return null;
    }

    //TODO: get
    @Override
    Object get(Object object) {
        return null;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
