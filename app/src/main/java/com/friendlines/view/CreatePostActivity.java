package com.friendlines.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.friendlines.R;
import com.friendlines.controller.Controller;
import com.friendlines.model.post.ImagePost;
import com.friendlines.model.post.Post;
import com.friendlines.model.post.VideoPost;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

import javax.annotation.Nonnull;

public class CreatePostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    String youtubeLink;
    ActionBar actionBar;
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Create Post");
        imageUri = null;
        youtubeLink = null;

        controller = Controller.getInstance();
    }

    //Extracted from https://stackoverflow.com/questions/22947713/make-the-up-button-behave-like-the-back-button-on-android
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    public void addImage(View view)
    {
        openGallery();
        youtubeLink = null;
    }

    public void addVideo(View view)
    {
        imageUri = null;
        //Extracted from https://stackoverflow.com/questions/10903754/input-text-dialog-android
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert your youtube link");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                youtubeLink = input.getText().toString();
                if(!youtubeLink.startsWith("https://www.youtube.com/"))
                {
                    youtubeLink = null;
                    Toast.makeText(getApplicationContext(), "Not valid youtube link",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void addEmoji(View view) {
        //Maybe optional
    }

    public void submit(View view)
    {
        EditText postText = findViewById(R.id.post_text);
        //Creating the post
        if(imageUri != null)
        {
            controller.getDto().setPost(new ImagePost());
        }
        else if(youtubeLink != null)
        {
            controller.getDto().setPost(new VideoPost());
        }
        else
        {
            controller.getDto().setPost(new Post());
        }
        controller.getDto().getPost().setText(postText.getText().toString());
        controller.getDto().getPost().setCreated(new Timestamp(Calendar.getInstance().getTime()));
        controller.getDto().getPost().setUser_id(controller.getDto().getUser().getId());
        controller.getDto().getPost().setUser_name(controller.getDto().getUser().getFirstname() + " " + controller.getDto().getUser().getLastname());
        controller.getDto().getPost().setUser_image(controller.getDto().getUser().getImage());
        if(imageUri == null)
        {
            if(youtubeLink == null)
            {
                controller.getDto().getPost().setType("TEXT");
                controller.addPost();
                Toast.makeText(getApplicationContext(), "Post created",Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                ((VideoPost)controller.getDto().getPost()).setVideo(youtubeLink);
                controller.getDto().getPost().setType("VIDEO");
                controller.addPost();
                Toast.makeText(getApplicationContext(), "Post created",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else
        {
            String imageName = "posts/" + controller.getDto().getUser().getId()+Calendar.getInstance().getTime().toString()+".jpg";
            final StorageReference ref = FirebaseStorage.getInstance().getReference().child(imageName);
            ref.putFile(imageUri)
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@Nonnull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@Nonnull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        ((ImagePost)controller.getDto().getPost()).setImage(downloadUri.toString());
                        controller.getDto().getPost().setType("IMAGE");
                        controller.addPost();
                        Toast.makeText(getApplicationContext(), "Post created",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error while uploading the file",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imageUri = data.getData();
        }
    }
}
