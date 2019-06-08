package com.friendlines.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.friendlines.R;

public class SplashActivity extends AppCompatActivity {

    //TOMADO DE https://www.youtube.com/watch?v=GM8sc0ZjopU

    private final int DURATION_SPLASH = 1700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getApplicationContext(), LaunchActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }
                , DURATION_SPLASH);
    }
}
