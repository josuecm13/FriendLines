package com.friendlines.controller.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.friendlines.CommentSection;
import com.friendlines.ProfileActivity;
import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.model.post.Post;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder>{

    private List<Post> postList;
    private Context context;
    private View view;


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post,null,false);
        return new PostViewHolder(view);
    }

    public PostsAdapter(List<Post> postList, Context context){
        this.postList = postList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        final Post post = postList.get(i);
        postViewHolder.name.setText(post.getUser_name());
        Picasso.with(context).load(post.getUser_image()).into(postViewHolder.image);
        postViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: crear activity de peril
                Bundle bundle = new Bundle();
                bundle.putString("fragment", "profile");
                bundle.putInt("layout_id",R.layout.fragment_profile);
                Intent intent = new Intent(context, ProfileActivity.class).putExtras(bundle);
                context.startActivity(intent);
            }
        });
        //TODO: procesa
        postViewHolder.date.setText("5 minutes ago.");
        if(!post.getUser_id().equals(Controller.getInstance().getDto().getUser().getId()))
        {
            postViewHolder.options.setVisibility(View.INVISIBLE);
        }
        else
        {
            postViewHolder.options.setVisibility(View.VISIBLE);
            postViewHolder.options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to delete it?")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try
                                    {
                                        Controller.getInstance().deletePost(post.getId());
                                    }
                                    catch (ControlException e)
                                    {
                                        Log.e("Error", e.getMessage());
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
        postViewHolder.description.setText(post.getText());
        postViewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Controller.getInstance().addLike(post.getId());
                }
                catch (ControlException e)
                {
                    Log.e("Error", e.getMessage());
                }
            }
        });
        postViewHolder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dislike post logic
            }
        });
        postViewHolder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentSection.class);
                intent.putExtra("POST_ID", post.getId());
                context.startActivity(intent);
            }
        });
        //TODO: set media content
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        CircularImageView image;
        TextView name, date, description;
        FrameLayout mediaContentContainer;
        ImageView options, like, comments, dislike;
        View instance;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            instance = itemView;
            image = instance.findViewById(R.id.image);
            name = instance.findViewById(R.id.name);
            date = instance.findViewById(R.id.date);
            description = instance.findViewById(R.id.content);
            options = instance.findViewById(R.id.options);
            mediaContentContainer = instance.findViewById(R.id.container);
            like = instance.findViewById(R.id.like);
            comments = instance.findViewById(R.id.comments);
            dislike = instance.findViewById(R.id.dislike);
        }
    }

    public void setContext(Context c){
        this.context = c;
    }

    public void addPost(Post post){
        postList.add(post);
        notifyDataSetChanged();
    }

    public void changePost(Post post){
        int i = postList.indexOf(post);
        postList.remove(i);
        postList.add(i, post);
        notifyItemChanged(i);
    }

    public void deletePost(Post post){

    }


    private void deletePost(int i)
    {
        try
        {
            Controller.getInstance().deletePost(Controller.getInstance().getDto().getPosts().get(i).getId());
        }
        catch (ControlException e)
        {
            Log.e("Error", e.getMessage());
        }
    }
}
