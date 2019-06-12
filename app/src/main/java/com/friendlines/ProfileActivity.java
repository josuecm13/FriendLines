package com.friendlines;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.friendlines.controller.Controller;
import com.friendlines.controller.listeners.UserEventListener;
import com.friendlines.model.User;
import com.friendlines.view.homefragments.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    int layout_id;
    ProfileFragment fragment;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        frameLayout = findViewById(R.id.container);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragment = new ProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.image_container, fragment);
        transaction.commit();
        try{
            Controller.getInstance().listenUser(this, Controller.getInstance().getDto().getSelectedUser().getId(), new UserEventListener() {

                @Override
                public void onUserChanged(User user) {
                    //fragment
                    setData(user);
                }

                @Override
                public void onUserDeleted(User user) {
                    userWasDeleted();
                }
            });
        }catch (Exception e){}


        /*
        fragment = new ProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();
        */

    }

    private void userWasDeleted() {
    }

    private void setData(User user) {
        fragment.updateUserData(user);
    }

    //Extracted from https://stackoverflow.com/questions/22947713/make-the-up-button-behave-like-the-back-button-on-android
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }


}
