package com.friendlines.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.friendlines.R;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }


    public void goToLogin(View v){
        //TODO: startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void goToRegister(View v){
        //TODO: startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

}
