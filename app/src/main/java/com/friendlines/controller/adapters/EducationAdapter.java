package com.friendlines.controller.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.friendlines.R;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.Holder> {

    public List<String> list;
    Context context;
    View view;


    public EducationAdapter(List<String> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_study,null,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        String title = list.get(i);
        holder.textView.setText(title);
        holder.instance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete it?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete in firebase
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
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        View instance;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.text);
            instance = itemView;
        }
    }
}
