package com.friendlines.controller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.friendlines.R;
import com.friendlines.model.Friendship;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RequestsAdapter  extends RecyclerView.Adapter<RequestsAdapter.Holder>{

    private List<Friendship> friendshipList;
    private Context context;
    private View view;

    public RequestsAdapter(){}

    public RequestsAdapter(List<Friendship> friendshipList, Context context){
        this.context = context;
        this.friendshipList = friendshipList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend_request,null,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        Friendship f = friendshipList.get(i);
        holder.name.setText(f.getSender_name());
        Picasso.with(context).load(f.getSender_image()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: activity del perfil
            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendshipList.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        CircularImageView image;
        TextView name;
        View instance, accept, decline;

        public Holder(@NonNull View itemView) {
            super(itemView);
            instance = itemView;
            image = instance.findViewById(R.id.image);
            name = instance.findViewById(R.id.name);
            accept = instance.findViewById(R.id.accept);
            decline = instance.findViewById(R.id.decline);
        }
    }
}
