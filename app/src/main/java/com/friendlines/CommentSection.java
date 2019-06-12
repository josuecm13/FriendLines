package com.friendlines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.friendlines.controller.adapters.PostsAdapter;
import com.friendlines.model.post.Post;

import java.util.ArrayList;

public class CommentSection extends AppCompatActivity {

    EditText commentEditText;
    RecyclerView recyclerView;
    PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_section);
        commentEditText = findViewById(R.id.comment_edit_text);
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new PostsAdapter(new ArrayList<Post>(), this);

    }

    public void addComment(View view) {
        String comment = commentEditText.getText().toString();
        //TODO: submit comment
    }
}
