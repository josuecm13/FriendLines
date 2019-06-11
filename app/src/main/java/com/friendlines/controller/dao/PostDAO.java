package com.friendlines.controller.dao;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.listeners.PostEventListener;
import com.friendlines.controller.listeners.QueryListener;
import com.friendlines.model.post.ImagePost;
import com.friendlines.model.post.Post;
import com.friendlines.model.post.VideoPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import javax.annotation.Nullable;

public class PostDAO {

    public static final String COLLECTION_NAME = "posts";

    public PostDAO(){}

    public void add(Post post){
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME).add(post);
    }

    public void update(Post post) throws ControlException {
        if(post.getId() == null)
            throw new ControlException("A post ID must be provided to update.");
        else{
            HashMap<String, Object> map = new HashMap();
            map.put("user_id", post.getUser_id());
            map.put("user_name", post.getUser_name());
            map.put("user_image", post.getUser_image());
            map.put("type", post.getType());
            map.put("text", post.getText());
            map.put("created", post.getCreated());
            if(post instanceof ImagePost)
                map.put("image", ((ImagePost)post).getImage());
            else if(post instanceof VideoPost)
                map.put("video", ((VideoPost)post).getVideo());
            FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(post.getId()).update(map);
        }
    }

    public void delete(String id) throws ControlException{
        if(id == null)
            throw new ControlException("A post ID must be provided to delete.");
        FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document(id).delete();
    }

    //listen to all posts
    public void listen(Activity activity, final PostEventListener listener){
        FirebaseFirestore.getInstance()
                .collection(COLLECTION_NAME)
                .addSnapshotListener(activity, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                if(e != null)
                    Log.d(Controller.TAG, "PostDAO listen error: " + e);
                else{
                    for(DocumentChange change : querySnapshot.getDocumentChanges()){
                        String type = (String)change.getDocument().get("type");
                        Post post;
                        if(type.equals(ImagePost.TYPE))
                            post = change.getDocument().toObject(ImagePost.class);
                        else if(type.equals(VideoPost.TYPE))
                            post = change.getDocument().toObject(VideoPost.class);
                        else //if post's type is Post.TYPE
                            post = change.getDocument().toObject(Post.class);
                        post.setId(change.getDocument().getId());
                        switch(change.getType()){
                            case ADDED:
                                listener.onPostAdded(post);
                                break;
                            case MODIFIED:
                                listener.onPostChanged(post);
                                break;
                            case REMOVED:
                                listener.onPostDeleted(post);
                                break;
                        }
                    }
                }
            }
        });
    }

    //listen posts by user
    public void listen(Activity activity, String user_id, final PostEventListener listener) throws ControlException {
        if(user_id == null)
            throw new ControlException("a user ID must be provided to listen to its posts.");
        else{
            FirebaseFirestore.getInstance()
                    .collection(COLLECTION_NAME)
                    .whereEqualTo("user_id", user_id)
                    .addSnapshotListener(activity, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                    if(e != null)
                        Log.d(Controller.TAG, "PostDAO listen by user_id error: " + e);
                    else{
                        for(DocumentChange change : querySnapshot.getDocumentChanges()){
                            String type = (String)change.getDocument().get("type");
                            Post post;
                            if(type.equals(ImagePost.TYPE))
                                post = change.getDocument().toObject(ImagePost.class);
                            else if(type.equals(VideoPost.TYPE))
                                post = change.getDocument().toObject(VideoPost.class);
                            else //if post's type is Post.TYPE
                                post = change.getDocument().toObject(Post.class);
                            post.setId(change.getDocument().getId());
                            switch(change.getType()){
                                case ADDED:
                                    listener.onPostAdded(post);
                                    break;
                                case MODIFIED:
                                    listener.onPostChanged(post);
                                    break;
                                case REMOVED:
                                    listener.onPostDeleted(post);
                                    break;
                            }
                        }
                    }
                }
            });
        }
    }

    //queries posts which contain a given text
    public void query(Activity activity, final String text, final QueryListener<Post> listener){
        FirebaseFirestore.getInstance()
                .collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(activity, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    listener.onError(new ControlException("On PostDAO.query with text = " + text));
                } else {
                    for (DocumentChange change : task.getResult().getDocumentChanges()) {
                        String type = (String)change.getDocument().get("type");
                        Post post;
                        if(type.equals(ImagePost.TYPE))
                            post = change.getDocument().toObject(ImagePost.class);
                        else if(type.equals(VideoPost.TYPE))
                            post = change.getDocument().toObject(VideoPost.class);
                        else //if post's type is Post.TYPE
                            post = change.getDocument().toObject(Post.class);
                        post.setId(change.getDocument().getId());
                        if(post.getText().contains(text))
                            listener.onSuccess(post);
                    }
                }
            }
        });
    }
}
