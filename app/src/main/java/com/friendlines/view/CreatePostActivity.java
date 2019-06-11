package com.friendlines.view;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.friendlines.R;
import com.friendlines.controller.Controller;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
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

    public void addImage(View view)
    {
        openGallery();
    }

    public void addVideo(View view) {
    }

    public void addEmoji(View view) {
        //Maybe optional
    }

    public void submit(View view)
    {
        EditText postText = findViewById(R.id.post_text);
        //Creating the post
        controller.getDto().getPost().setText(postText.getText().toString());
        controller.getDto().getPost().setCreated(new Timestamp(Calendar.getInstance().getTime()));
        controller.getDto().getPost().setUser_id(controller.getDto().getUser().getId());
        controller.getDto().getPost().setUser_name(controller.getDto().getUser().getFirstname() + " " + controller.getDto().getUser().getLastname());
        if(imageUri == null)
        {

        }
        else
        {
            try
            {
                String imageName = "posts/" + controller.getDto().getUser().getId()+Calendar.getInstance().getTime().toString();
                final StorageReference ref = FirebaseStorage.getInstance().getReference().child(imageName);
                ref.putStream(new FileInputStream(new File(imageUri.getPath())))
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
                            controller.getDto().getPost().setUser_image(downloadUri.toString());
                            controller.addPost();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Error while uploading the file",Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
            catch (FileNotFoundException e)
            {
                Log.e("Error", e.getMessage());
            }
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
