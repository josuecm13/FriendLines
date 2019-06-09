package com.friendlines.view.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.friendlines.R;

public class LoginActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    private void initComponents(){
        mActionBarToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void signIn(View view) {
        //Attemp sign In
    }

    public void goToRecoverPassword(View view) {
        startActivity(new Intent(getApplicationContext(), RecoverPasswordActivity.class));
    }
}
