package com.friendlines.view.homefragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.adapters.PostsAdapter;
import com.friendlines.controller.listeners.PostEventListener;
import com.friendlines.model.Friendship;
import com.friendlines.model.post.Post;
import com.friendlines.view.CreatePostActivity;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFeedFragment extends Fragment {

    public PostsAdapter adapter;
    RecyclerView recyclerView;
    Controller controller;

    public UserFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_feed, container, false);
        controller = Controller.getInstance();
        Button btnCreatePost = view.findViewById(R.id.btn_create_post);
        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CreatePostActivity.class));
            }
        });
        adapter = new PostsAdapter(controller.getDto().getPosts(), getContext());
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}
