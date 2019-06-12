package com.friendlines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.adapters.PostsAdapter;
import com.friendlines.controller.listeners.PostEventListener;
import com.friendlines.model.post.Post;
import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class CommentSection extends AppCompatActivity {

    EditText commentEditText;
    RecyclerView recyclerView;
    PostsAdapter adapter;
    String post_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_section);
        post_id = getIntent().getStringExtra("POST_ID");
        commentEditText = findViewById(R.id.comment_edit_text);
        recyclerView = findViewById(R.id.recyclerview);
        Controller.getInstance().getDto().getComments().clear();
        adapter = new PostsAdapter(Controller.getInstance().getDto().getComments(), this);
        recyclerView.setAdapter(adapter);
        loadComments();
    }

    public void addComment(View view) {
        String text = commentEditText.getText().toString();
        Post comment = new Post();
        comment.setCreated(Timestamp.now());
        comment.setText(text);
        comment.setType("TEXT");
        comment.setUser_id(Controller.getInstance().getDto().getUser().getId());
        comment.setUser_image(Controller.getInstance().getDto().getUser().getImage());
        comment.setUser_name(Controller.getInstance().getDto().getUser().getFirstname() + " " + Controller.getInstance().getDto().getUser().getLastname());
        Controller.getInstance().getDto().setComment(comment);
        try
        {
            Controller.getInstance().addComment(post_id);
            Toast.makeText(this, "Comment uploaded", Toast.LENGTH_SHORT).show();
        }
        catch (ControlException e)
        {
            Toast.makeText(this, "There was a problem while uploading de comment", Toast.LENGTH_SHORT).show();
            Log.e("Error", e.getMessage());
        }
    }

    private void loadComments()
    {
        try
        {
            Controller.getInstance().listenComment(this, post_id, new PostEventListener() {
                @Override
                public void onPostAdded(Post post) {
                    Controller.getInstance().getDto().getComments().add(post);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onPostChanged(Post post) {
                    Controller.getInstance().getDto().getComments().set(Controller.getInstance().getDto().getComments().indexOf(post), post);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onPostDeleted(Post post) {
                    Controller.getInstance().getDto().getComments().remove(post);
                    adapter.notifyDataSetChanged();
                }
            });
        }
        catch (ControlException e)
        {
            Log.e("Error", e.getMessage());
        }
    }
}
