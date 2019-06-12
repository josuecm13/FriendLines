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
import android.widget.Toast;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.model.Education;

import java.util.ArrayList;
import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.Holder> {

    public List<Education> list;
    Context context;
    View view;


    public EducationAdapter(Context context){
        this.list = new ArrayList();
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
        final Education education = list.get(i);
        holder.institution.setText(education.getInstitution());
        holder.type.setText(education.getType());
        holder.instance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete it?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    String uid = Controller.getInstance().getDto().getUser().getId();
                                    Controller.getInstance().deleteEducation(uid, education.getId());
                                } catch(ControlException ex){
                                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
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
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView institution;
        TextView type;
        View instance;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.institution = itemView.findViewById(R.id.institution);
            this.type = itemView.findViewById(R.id.charge);
            instance = itemView;
        }
    }

    public void addEducation(Education education){
        Log.d(Controller.TAG, "EducationAdapter addEducation before operation");
        list.add(education);
        for(Education education_ : list){
            Log.d(Controller.TAG, education_.getInstitution());
        }
        notifyDataSetChanged();
        Log.d(Controller.TAG, "EducationAdapter addEducation after operation");
    }

    public void changeEducation(Education education){
        //ESTE INDICE SE PUEDE QUEBRAR, REVISAR
        int i = list.indexOf(education);
        list.remove(i);
        list.add(i, education);
        notifyDataSetChanged();
    }

    public void deleteEducation(Education education){
        int i = list.indexOf(education);
        list.remove(i);
        notifyDataSetChanged();
    }
}
