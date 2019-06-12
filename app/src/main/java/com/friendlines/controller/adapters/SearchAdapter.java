package com.friendlines.controller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.friendlines.R;
import com.friendlines.controller.Controller;
import com.friendlines.model.User;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Holder>{

    public List<User> list;
    Context context;
    View view;


    public SearchAdapter(Context context){
        this.list = new ArrayList();
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_study,null,false);
        return new SearchAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.Holder holder, int i) {
        final User user = list.get(i);
        holder.name.setText(user.getFirstname() + " " + user.getLastname());
        if(user.getImage() != null)
            Picasso.with(context).load(user.getImage()).into(holder.image);
        holder.instance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //manejar abrir perfil usuario
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CircularImageView image;
        TextView name;
        View instance;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.image = itemView.findViewById(R.id.image);
            instance = itemView;
        }
    }

    public void addUser(User user){
        Log.d(Controller.TAG, "SearchAdapter addUser before operation");
        list.add(user);
        for(User user_ : list){
            Log.d(Controller.TAG, user_.getFirstname());
            Log.d(Controller.TAG, user_.getLastname());
        }
        notifyDataSetChanged();
        Log.d(Controller.TAG, "SearchAdapter addUser after operation");
    }

    public void reset(){
        list.clear();
        notifyDataSetChanged();
    }
}