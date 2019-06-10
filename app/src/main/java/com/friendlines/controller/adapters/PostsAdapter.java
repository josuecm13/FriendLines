package com.friendlines.controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.friendlines.CommentSection;
import com.friendlines.R;
import com.friendlines.controller.Controller;
import com.friendlines.model.post.Post;
import com.mikhaellopez.circularimageview.CircularImageView;

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
        //TODO: set iamge url picasso
        postViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: crear activity de peril
                Intent intent = new Intent();
                context.startActivity(intent);
            }
        });
        //TODO: procesa
        postViewHolder.date.setText("5 minutes ago.");
        postViewHolder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Abrir menu de opciones o eliminar la publicacion

            }
        });
        postViewHolder.description.setText(post.getText());
        postViewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //like post logic
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
                context.startActivity(new Intent(context, CommentSection.class));
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

}
