package com.friendlines.controller.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.model.Friendship;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.Holder>{

    private List<Friendship> friendshipList;
    private Context context;
    private View view;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend,null,false);
        return new Holder(view);
    }

    public FriendsAdapter(List<Friendship> friendshipList, Context context){
        this.context = context;
        this.friendshipList = friendshipList;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final Friendship f = friendshipList.get(i);
        if(!f.getSender_id().equals(Controller.getInstance().getDto().getUser().getId()))
        {
            holder.name.setText(f.getSender_name());
            Picasso.with(context).load(f.getSender_image()).into(holder.image);
        }
        else
        {
            holder.name.setText(f.getReceiver_name());
            Picasso.with(context).load(f.getReceiver_image()).into(holder.image);
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: activity del perfil
            }
        });
        holder.mutualFriends.setText("2 mutual friends");
        holder.remove.setOnClickListener(new View.OnClickListener() {
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
                                    Controller.getInstance().rejectFriendship(f.getId());
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

    @Override
    public int getItemCount() {
        return friendshipList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        CircularImageView image;
        TextView name;
        TextView mutualFriends;
        View instance, remove;

        public Holder(@NonNull View itemView) {
            super(itemView);
            instance = itemView;
            image = instance.findViewById(R.id.image);
            name = instance.findViewById(R.id.name);
            mutualFriends = instance.findViewById(R.id.mutual_friends);
            remove = instance.findViewById(R.id.remove);
        }
    }
}
