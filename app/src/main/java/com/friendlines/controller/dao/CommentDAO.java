package com.friendlines.controller.dao;

import android.app.Activity;
import android.util.Log;

import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.listeners.PostEventListener;
import com.friendlines.model.post.ImagePost;
import com.friendlines.model.post.Post;
import com.friendlines.model.post.VideoPost;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import javax.annotation.Nullable;

public class CommentDAO {
    public static final String COLLECTION_NAME = "comments";

    public CommentDAO(){}

    public void add(String post_id, Post comment) throws ControlException {
        if(post_id == null)
            throw new ControlException("A parent post ID must be provided to add a comment to.");
        else {
            FirebaseFirestore.getInstance()
                    .collection(PostDAO.COLLECTION_NAME)
                    .document(post_id)
                    .collection(COLLECTION_NAME)
                    .add(comment);
        }
    }

    public void update(String post_id, Post comment) throws ControlException {
        if(post_id == null)
            throw new ControlException("A parent post ID must be provided to update its comment.");
        else if(comment.getId() == null)
            throw new ControlException("A comment ID must be provided to update.");
        else{
            HashMap<String, Object> map = new HashMap();
            map.put("user_id", comment.getUser_id());
            map.put("user_name", comment.getUser_name());
            map.put("user_image", comment.getUser_image());
            map.put("type", comment.getType());
            map.put("text", comment.getText());
            map.put("created", comment.getCreated());
            if(comment instanceof ImagePost)
                map.put("image", ((ImagePost)comment).getImage());
            else if(comment instanceof VideoPost)
                map.put("video", ((VideoPost)comment).getVideo());
            FirebaseFirestore.getInstance()
                    .collection(PostDAO.COLLECTION_NAME)
                    .document(post_id)
                    .collection(COLLECTION_NAME)
                    .document(comment.getId())
                    .update(map);
        }
    }

    public void delete(String post_id, String comment_id) throws ControlException {
        if(post_id == null)
            throw new ControlException("A parent post ID must be provided to delete its comment.");
        else if(comment_id == null)
            throw new ControlException("A comment ID must be provided to delete a comment.");
        else {
            FirebaseFirestore.getInstance()
                    .collection(PostDAO.COLLECTION_NAME)
                    .document(post_id)
                    .collection(COLLECTION_NAME)
                    .document(comment_id)
                    .delete();
        }
    }

    //NOTE: post_id refers to the parent post id, not the comment itself.
    public void listen(Activity activity, String post_id, final PostEventListener listener) throws ControlException{
        if(post_id == null)
            throw new ControlException("A parent post ID must be provided to listen to its comments.");
        else{
            FirebaseFirestore.getInstance()
                    .collection(PostDAO.COLLECTION_NAME)
                    .document(post_id)
                    .collection(COLLECTION_NAME)
                    .addSnapshotListener(activity, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                    if(e != null)
                        Log.d(Controller.TAG, "CommentDAO listen error: " + e);
                    else{
                        for(DocumentChange change : querySnapshot.getDocumentChanges()){
                            String type = (String)change.getDocument().get("type");
                            Post comment;
                            if(type.equals(ImagePost.TYPE))
                                comment = change.getDocument().toObject(ImagePost.class);
                            else if(type.equals(VideoPost.TYPE))
                                comment = change.getDocument().toObject(VideoPost.class);
                            else //if comment's type is Post.TYPE
                                comment = change.getDocument().toObject(Post.class);
                            comment.setId(change.getDocument().getId());
                            switch(change.getType()){
                                case ADDED:
                                    listener.onPostAdded(comment);
                                    break;
                                case MODIFIED:
                                    listener.onPostChanged(comment);
                                    break;
                                case REMOVED:
                                    listener.onPostDeleted(comment);
                                    break;
                            }
                        }
                    }
                }
            });
        }
    }
}
