package com.friendlines.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.friendlines.R;

public class CreatePostActivity extends AppCompatActivity {

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Create Post");
    }

    public void addImage(View view) {
    }

    public void addVideo(View view) {
    }

    public void addEmoji(View view) {
        //Maybe optional
    }

    public void submit(View view) {
    }
}
