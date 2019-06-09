package com.friendlines.view.auth.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.friendlines.R;
import com.friendlines.controller.Controller;

public class RegNamesActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_names);
        initComponents();
        controller = Controller.getInstance();
    }

    private void initComponents(){
        mActionBarToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void goNext(View view) {
        EditText name = findViewById(R.id.name_edit_text);
        EditText lastName = findViewById(R.id.lastname_edit_text);
        controller.getDto().getUser().setName(name.getText().toString());
        controller.getDto().getUser().setSurname(lastName.getText().toString());
        startActivity(new Intent(getApplicationContext(), RegBirthGenderActivity.class));
    }
}
