package com.friendlines.view.homefragments;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.adapters.PostsAdapter;
import com.friendlines.controller.listeners.TaskListener;
import com.friendlines.model.User;
import com.friendlines.model.post.Post;
import com.friendlines.view.SplashActivity;
import com.friendlines.view.profile.EducationActivity;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment{

    private static final int GALLERY = 1;

    RecyclerView recyclerView;
    public PostsAdapter adapter;
    TextView nameTextView;
    CircularImageView profileImage;
    View options;
    Button education;

    public ProfileFragment() {
        adapter = new PostsAdapter(new ArrayList<Post>(),getContext());
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
                showDialog("What do you want to do?",
        "Change Image", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GALLERY);
                    }
                },
        "View Image", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
            }
        });
        options = view.findViewById(R.id.options);
        options.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showDialog("Would you like to sign out?",
                        "Sign Out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Controller.getInstance().signOut();
                                getActivity().finishAffinity();
                                startActivity(new Intent(getActivity().getApplicationContext(), SplashActivity.class));
                            }
                        }, "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
            }
        });

        education = view.findViewById(R.id.education_btn);
        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EducationActivity.class));
            }
        });
        recyclerView = view.findViewById(R.id.timeline_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY && resultCode == RESULT_OK && data != null){
            final Uri imageUri = data.getData();
            Controller.getInstance().uploadImage(ProfileFragment.this.getActivity(), "images", imageUri, new TaskListener<String>() {
                @Override
                public void onSuccess(String imageURL) {
                    try {
                        Controller.getInstance().getDto().getUser().setImage(imageURL);
                        Controller.getInstance().updateUser();
                    } catch(ControlException ex){
                        Toast.makeText(ProfileFragment.this.getContext(), ex.getMessage(), Toast.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(ControlException exception) {
                    Toast.makeText(ProfileFragment.this.getContext(), exception.getMessage(), Toast.LENGTH_LONG);
                }
            });
        }
    }

    private void showDialog(String question,
                            String positiveString, DialogInterface.OnClickListener positiveListener,
                            String negativeString, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(question)
                .setCancelable(true)
                .setPositiveButton(positiveString, positiveListener)
                .setNegativeButton(negativeString, negativeListener);
        AlertDialog dialog = builder.create();
        dialog.show();
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

    @Override
    public void onResume() {
        super.onResume();
        User user  = Controller.getInstance().getDto().getUser();
        nameTextView.setText(user.getFirstname() + " " + user.getLastname());
        Picasso.with(getContext()).load(user.getImage()).into(profileImage);
    }
}
