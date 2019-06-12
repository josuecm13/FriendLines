package com.friendlines.view.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.adapters.EducationAdapter;
import com.friendlines.controller.listeners.EducationEventListener;
import com.friendlines.model.Education;

public class EducationActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    EducationAdapter adapter;
    EditText textView;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new EducationAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //CAMBIAR
        textView = findViewById(R.id.create_post);

        try {
            userId = Controller.getInstance().getDto().getUser().getId();
            Controller.getInstance().listenEducation(EducationActivity.this, userId, new EducationEventListener() {
                @Override
                public void onEducationAdded(Education education) {
                    Log.d(Controller.TAG, "EducationActivity onEducationAdded");
                    adapter.addEducation(education);
                }

                @Override
                public void onEducationChanged(Education education) {
                    adapter.changeEducation(education);
                }

                @Override
                public void onEducationDeleted(Education education) {
                    adapter.deleteEducation(education);
                }
            });
        } catch(ControlException ex){
            Log.e(Controller.TAG, ex.getMessage());
        }
    }

    public void addStudy(View view) {
        String[] string = textView.getText().toString().split(" ");
        textView.setText("");
        String type = string[0];
        String institution = string[1];
        Education education = new Education(institution, type);
        Controller.getInstance().getDto().setEducation(education);
        try {
            Controller.getInstance().addEducation(userId);
        } catch(ControlException ex){
            Log.e(Controller.TAG, ex.getMessage());
        }
    }
}
