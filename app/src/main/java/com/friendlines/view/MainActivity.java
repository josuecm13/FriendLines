package com.friendlines.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.friendlines.R;
import com.friendlines.view.homefragments.FriendsFragment;
import com.friendlines.view.homefragments.NotificationsFragment;
import com.friendlines.view.homefragments.ProfileFragment;
import com.friendlines.view.homefragments.SearchFragment;
import com.friendlines.view.homefragments.UserFeedFragment;

public class MainActivity extends AppCompatActivity
{
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        ((ViewPagerAdapter) fragmentPagerAdapter).addFragment(new FriendsFragment(), "Friends");
        ((ViewPagerAdapter) fragmentPagerAdapter).addFragment(new NotificationsFragment(), "Notifications");
        ((ViewPagerAdapter) fragmentPagerAdapter).addFragment(new ProfileFragment(), "Profile");
        ((ViewPagerAdapter) fragmentPagerAdapter).addFragment(new SearchFragment(), "Search");
        ((ViewPagerAdapter) fragmentPagerAdapter).addFragment(new UserFeedFragment(), "User feed");
        //ViewPager
        viewPager = findViewById(R.id.view_pager_home);
        viewPager.setAdapter(fragmentPagerAdapter);
        //TabLayout
        tabLayout = findViewById(R.id.tab_layout_home);
        tabLayout.setupWithViewPager(viewPager);
    }
}
