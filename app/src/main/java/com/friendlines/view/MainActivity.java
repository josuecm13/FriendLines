package com.friendlines.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.listeners.FriendshipEventListener;
import com.friendlines.controller.listeners.UserEventListener;
import com.friendlines.model.Friendship;
import com.friendlines.model.User;
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
    private UserFeedFragment userFeedFragment;
    private FriendsFragment friendsFragment;
    private ProfileFragment profileFragment;
    private NotificationsFragment notificationsFragment;
    private SearchFragment searchFragment;
    private boolean firstTime;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = Controller.getInstance();
        loadFriendships();
        userFeedFragment = new UserFeedFragment();
        friendsFragment = new FriendsFragment();
        profileFragment = new ProfileFragment();
        notificationsFragment = new NotificationsFragment();
        searchFragment = new SearchFragment();
        fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        ((ViewPagerAdapter) fragmentPagerAdapter).addFragment(userFeedFragment, "User feed");
        ((ViewPagerAdapter) fragmentPagerAdapter).addFragment(friendsFragment, "Friends");
        ((ViewPagerAdapter) fragmentPagerAdapter).addFragment(profileFragment, "Profile");
        ((ViewPagerAdapter) fragmentPagerAdapter).addFragment(searchFragment, "Search");
        ((ViewPagerAdapter) fragmentPagerAdapter).addFragment(notificationsFragment, "Notifications");
        //ViewPager
        viewPager = findViewById(R.id.view_pager_home);
        viewPager.setAdapter(fragmentPagerAdapter);
        //TabLayout
        tabLayout = findViewById(R.id.tab_layout_home);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Controller.getInstance().listenUser(this, new UserEventListener() {
                @Override
                public void onUserChanged(User user) {
                    gotUserData(user);
                }

                @Override
                public void onUserDeleted(User user) {
                    userWasDeleted();
                }
            });
        } catch(ControlException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void gotUserData(User user){
        if(firstTime){
            firstTime = false;
            //ejecutar todo lo necesario durante la primera carga del usuario
            //como crear otros listeners por ejemplo

        }
        controller.getDto().setUser(user);
    }

    private void userWasDeleted(){

    }

    private void loadFriendships()
    {
        try
        {
            controller.listenFriendship(this, controller.getDto().getUser().getId(), new FriendshipEventListener() {
                @Override
                public void onFriendshipAdded(Friendship friendship) {
                    controller.getDto().getFriendships().add(friendship);
                    Log.e("Friend", "New friend");
                }

                @Override
                public void onFriendshipAccepted(Friendship friendship) {
                    controller.getDto().getFriendships().set(controller.getDto().getFriendships().indexOf(friendship), friendship);
                    Log.e("Friend", "Accepted friend");
                }

                @Override
                public void onFriendshipRejected(Friendship friendship) {
                    controller.getDto().getFriendships().remove(friendship);
                    Log.e("Friend", "Rejected friend");
                }
            });
        }
        catch (ControlException e)
        {
            Log.e("Error", e.getMessage());
        }
    }
}
