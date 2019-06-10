package com.friendlines.view.homefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.friendlines.R;
import com.friendlines.controller.adapters.PostsAdapter;
import com.friendlines.controller.adapters.RequestsAdapter;
import com.friendlines.model.post.Post;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    RecyclerView recyclerView;
    PostsAdapter adapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Post post = new Post();
        post.setCreated(Timestamp.now());
        post.setUser_name("Albert E.");
        String test = getActivity().getString(R.string.sample_post_text);
        post.setText(test);
        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            postList.add(post);
        }
        adapter = new PostsAdapter(postList, getContext());
        recyclerView = view.findViewById(R.id.timeline_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

}
