package com.friendlines.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.friendlines.R;
import com.friendlines.view.auth.LoginActivity;
import com.friendlines.view.auth.register.RegNamesActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }


    public void goToLogin(View v){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void goToRegister(View v){
        startActivity(new Intent(getApplicationContext(), RegNamesActivity.class));
    }

}
