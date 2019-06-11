package com.friendlines.view.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.friendlines.R;
import com.friendlines.controller.listeners.EducationEventListener;
import com.friendlines.model.Education;

public class EducationActivity extends AppCompatActivity implements EducationEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
    }

    public void addStudy(View view) {
        //
    }

    @Override
    public void onEducationAdded(Education education) {
        //acaba de agregarse una nueva educación
    }

    @Override
    public void onEducationChanged(Education education) {
        //acaba de cambiar alguna educación de alguien
    }

    @Override
    public void onEducationDeleted(Education education) {

    }
}
