package com.friendlines.view.homefragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.adapters.PostsAdapter;
import com.friendlines.controller.listeners.PostEventListener;
import com.friendlines.model.Friendship;
import com.friendlines.model.post.Post;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFeedFragment extends Fragment {

    PostsAdapter adapter;
    RecyclerView recyclerView;
    Controller controller;

    public UserFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_feed, container, false);
        Post post = new Post();
        post.setCreated(Timestamp.now());
        post.setUser_name("Albert Einstein");
        post.setText("Today was a good day");
        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            postList.add(post);
        }
        controller = Controller.getInstance();
        adapter = new PostsAdapter(controller.getDto().getPosts(), getContext());
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        loadPosts();
        return view;
    }

    private void loadPosts()
    {
        try
        {
            for(Friendship friendship : controller.getDto().getFriendships())
            {
                if(friendship.getStatus().equals(Friendship.ACCEPTED_STATUS))
                {
                    controller.listenPost(getActivity(), friendship.getId(), new PostEventListener() {
                        @Override
                        public void onPostAdded(Post post)
                        {
                            controller.getDto().getPosts().add(post);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onPostChanged(Post post)
                        {
                            controller.getDto().getPosts().set(controller.getDto().getPosts().indexOf(post), post);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onPostDeleted(Post post)
                        {
                            controller.getDto().getPosts().remove(post);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }
        catch (ControlException e)
        {
            Log.e("Error", e.getMessage());
        }
    }

    private void deletePost(int i)
    {
    }
}
