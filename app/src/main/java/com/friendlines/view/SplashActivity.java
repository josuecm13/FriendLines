package com.friendlines.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.listeners.TaskListener;
import com.friendlines.model.Friendship;
import com.google.firebase.Timestamp;

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
                        if(!Controller.getInstance().singedIn())
                            startActivity(LaunchActivity.class);
                        else{
                            Controller.getInstance().signIn(SplashActivity.this, new TaskListener<Void>() {
                                @Override
                                public void onSuccess(Void object) {
                                    startActivity(MainActivity.class);
                                }

                                @Override
                                public void onFailure(ControlException exception) {
                                    startActivity(LaunchActivity.class);
                                }
                            });
                        }
                    }
                }
                , DURATION_SPLASH);
    }

    private void startActivity(Class activityClass){
        Intent i = new Intent(getApplicationContext(), activityClass);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
