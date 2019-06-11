package com.friendlines.view.homefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.friendlines.R;
import com.friendlines.controller.Controller;
import com.friendlines.controller.adapters.PostsAdapter;
import com.friendlines.controller.adapters.RequestsAdapter;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.adapters.PostsAdapter;
import com.friendlines.controller.adapters.RequestsAdapter;
import com.friendlines.controller.listeners.UserEventListener;
import com.friendlines.model.User;
import com.friendlines.model.post.Post;
import com.google.firebase.Timestamp;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements UserEventListener {

    RecyclerView recyclerView;
    PostsAdapter adapter;
    TextView nameTextView;
    CircularImageView profileImage;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        /*
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
        */
        nameTextView = getActivity().findViewById(R.id.profile_user_fullname);
        profileImage = getActivity().findViewById(R.id.image);
        return view;
    }

    @Override
    public void onUserChanged(User user) {
        Log.d(Controller.TAG, user.getAuth_id());
        Log.d(Controller.TAG, user.getFirstname());
        Log.d(Controller.TAG, user.getLastname());
        //nameTextView.setText(user.getFirstname()+ " " + user.getLastname());
        //Picasso.with(getContext()).load(user.getImage()).into(profileImage);
    }

    @Override
    public void onUserDeleted(User user) {
        //
    }
}
