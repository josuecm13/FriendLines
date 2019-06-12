package com.friendlines;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.friendlines.view.homefragments.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    int layout_id;
    Fragment fragment;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        frameLayout = findViewById(R.id.container);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            layout_id = bundle.getInt("layout_id");
            String fragmentName = bundle.getString("fragment");
            assert fragmentName != null;
            if(fragmentName.equals("profile")){
                fragment = new ProfileFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(layout_id, fragment);
                transaction.commit();
            }
        }
        /*
        fragment = new ProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();
        */

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
