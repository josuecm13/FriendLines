package com.friendlines.controller.dao;

import android.app.Activity;

import com.friendlines.controller.ControlException;
import com.friendlines.controller.listeners.LikeEventListener;
import com.friendlines.model.Like;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LikeDAO {
    public static final String COLLECTION_NAME = "likes";

    public LikeDAO(){}

    public void add(String post_id, Like like) throws ControlException {
        if(post_id == null)
            throw new ControlException("A post ID must be provided to like.");
        else if(like.getValue() != Like.LIKED_VALUE || like.getValue() != Like.DISLIKED_VALUE)
            throw new ControlException("A like must only have a value of 1 (for a like) and -1 (for a dislike).");
        else {
            FirebaseFirestore.getInstance()
                    .collection(PostDAO.COLLECTION_NAME)
                    .document(post_id)
                    .collection(COLLECTION_NAME)
                    .add(like);
        }
    }

    public void add(String post_id, String comment_id, Like like) throws ControlException{
        if(post_id == null)
            throw new ControlException("A parent post ID must be provided to like a comment.");
        else if(comment_id == null)
            throw new ControlException("A comment ID must be provided to like.");
        else if(like.getValue() != Like.LIKED_VALUE || like.getValue() != Like.DISLIKED_VALUE)
            throw new ControlException("A like must only have a value of 1 (for a like) and -1 (for a dislike).");
        else {
            FirebaseFirestore.getInstance()
                    .collection(PostDAO.COLLECTION_NAME)
                    .document(post_id)
                    .collection(CommentDAO.COLLECTION_NAME)
                    .document(comment_id)
                    .collection(COLLECTION_NAME)
                    .add(like);
        }
    }

    public void update(String post_id, Like like) throws ControlException{
        if(post_id == null)
            throw new ControlException("A post ID must be provided to update its like.");
        else if(like.getId() == null)
            throw new ControlException("A like ID must be provided to update.");
        else{
            HashMap<String, Object> map = new HashMap();
            map.put("user_id", like.getUser_id());
            map.put("value", like.getValue());
            FirebaseFirestore.getInstance()
                    .collection(PostDAO.COLLECTION_NAME)
                    .document(post_id)
                    .collection(COLLECTION_NAME)
                    .document(like.getId())
                    .update(map);
        }
    }

    public void update(String post_id, String comment_id, Like like) throws ControlException{
        if(post_id == null)
            throw new ControlException("A parent post ID must be provided to update its comment's likes.");
        else if(comment_id == null)
            throw new ControlException("A comment ID must be provided to update its like.");
        else if(like.getId() == null)
            throw new ControlException("A like ID must be provided to update.");
        else{
            HashMap<String, Object> map = new HashMap();
            map.put("user_id", like.getUser_id());
            map.put("value", like.getValue());
            FirebaseFirestore.getInstance()
                    .collection(PostDAO.COLLECTION_NAME)
                    .document(post_id)
                    .collection(CommentDAO.COLLECTION_NAME)
                    .document(comment_id)
                    .collection(COLLECTION_NAME)
                    .document(like.getId())
                    .update(map);
        }
    }

    public void delete(String post_id, String like_id) throws ControlException{
        if(post_id == null)
            throw new ControlException("A post ID must be provided to delete its like.");
        else if(like_id == null)
            throw new ControlException("A like ID must be provided to delete.");
        else{
            FirebaseFirestore.getInstance()
                    .collection(PostDAO.COLLECTION_NAME)
                    .document(post_id)
                    .collection(COLLECTION_NAME)
                    .document(like_id)
                    .delete();
        }
    }

    public void delete(String post_id, String comment_id, String like_id) throws ControlException{
        if(post_id == null)
            throw new ControlException("A parent post ID must be provided to delete one of its comment's likes.");
        else if(comment_id == null)
            throw new ControlException("A comment ID must be provided to delete its like.");
        else if(like_id == null)
            throw new ControlException("A like ID must be provided to delete.");
        else{
            FirebaseFirestore.getInstance()
                    .collection(PostDAO.COLLECTION_NAME)
                    .document(post_id)
                    .collection(CommentDAO.COLLECTION_NAME)
                    .document(comment_id)
                    .collection(COLLECTION_NAME)
                    .document(like_id)
                    .delete();
        }
    }
}
