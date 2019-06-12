package com.friendlines.view.homefragments;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
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
import com.friendlines.view.profile.EducationActivity;
import com.google.firebase.Timestamp;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment{

    RecyclerView recyclerView;
    public PostsAdapter adapter;
    TextView nameTextView;
    CircularImageView profileImage;
    Button education;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTextView = view.findViewById(R.id.fullname);
        profileImage = view.findViewById(R.id.image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageoptions();
            }
        });

        education = view.findViewById(R.id.education_btn);
        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EducationActivity.class));
            }
        });

        List<Post> posts = new ArrayList<>();
        List<Post> allposts = Controller.getInstance().getDto().getPosts();
        for (Post p: allposts){
            if(p.getUser_id() == Controller.getInstance().getDto().getUser().getAuth_id())
                posts.add(p);
        }
        adapter = new PostsAdapter(posts,getContext());
        recyclerView = view.findViewById(R.id.timeline_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }


    public void addFlilteredPost(Post post){
        if(post.getUser_id().equals(Controller.getInstance().getDto().getUser().getId()))
        adapter.addPost(post);
    }

    public void changeFilteredPost(Post post){
        if(post.getUser_id().equals(Controller.getInstance().getDto().getUser().getId()))
            adapter.changePost(post);
    }

    public void deleteFilteredPost(Post post){
        if(post.getUser_id().equals(Controller.getInstance().getDto().getUser().getId()))
            adapter.deletePost(post);
    }

    public void filterUserPosts(List<Post> allposts){
        List<Post> posts = new ArrayList<>();
        for (Post p: allposts){
            if(p.getUser_id() == Controller.getInstance().getDto().getUser().getAuth_id())
                posts.add(p);
        }
    }

    private void showImageoptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("What do you want to do")
                .setCancelable(true)
                .setPositiveButton("Change image", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete in firebase
                    }
                })
                .setNegativeButton("view Image", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*@Override
    public void onUserChanged(User user) {
        Log.d(Controller.TAG, user.getAuth_id());
        Log.d(Controller.TAG, user.getFirstname());
        Log.d(Controller.TAG, user.getLastname());
        //nameTextView.setText(user.getFirstname()+ " " + user.getLastname());
        //Picasso.with(getContext()).load(user.getImage()).into(profileImage);
        

        *//*nameTextView = view.findViewById(R.id.fullname);
        profileImage = view.findViewById(R.id.image);



        User user  = Controller.getInstance().getDto().getUser();
        if(user.getImage() != null)
            Picasso.with(getContext()).load(user.getImage()).into(profileImage);

        View educationButton = view.findViewById(R.id.education_btn);

        educationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EducationActivity.class));
            }
        });

        return view;*//*
    }*/

    @Override
    public void onResume() {
        super.onResume();
        User user  = Controller.getInstance().getDto().getUser();
        nameTextView.setText(user.getFirstname() + " " + user.getLastname());
    }
}
