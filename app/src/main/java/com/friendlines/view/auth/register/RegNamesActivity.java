package com.friendlines.view.auth.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.friendlines.R;

public class RegNamesActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_names);
        initComponents();
    }

    private void initComponents(){
        mActionBarToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void goNext(View view) {
        // TODO: guardar datos de editTexts o enviarlos a la pantalla siguiente
        startActivity(new Intent(getApplicationContext(), RegBirthGenderActivity.class));
    }
}
