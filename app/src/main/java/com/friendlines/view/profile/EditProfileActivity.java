package com.friendlines.view.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.google.firebase.Timestamp;

import java.util.Date;

public class EditProfileActivity extends AppCompatActivity
{

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        controller = Controller.getInstance();
    }

    public void updateUserProfileListener(View view)
    {
        //TODO obtener valores de la vista
        /*String firstname;
        String lastname;
        String email;
        String image;
        Date birthday;
        int phone;
        String city;
        String country;
        controller.getDto().getUser().firstname = firstname;
        controller.getDto().getUser().lastname = lastname;
        controller.getDto().getUser().email = email;
        controller.getDto().getUser().image = image;
        controller.getDto().getUser().birthday = new Timestamp(birthday);
        controller.getDto().getUser().phone = phone;
        controller.getDto().getUser().city = city;
        controller.getDto().getUser().country = country;
        try
        {
            controller.updateUser();
        }
        catch (ControlException e)
        {
            Log.e("Error", e.getMessage());
        }*/
    }
}
