package com.friendlines.view.profile;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.model.User;
import com.friendlines.view.auth.register.RegBirthGenderActivity;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditProfileActivity extends AppCompatActivity
{

    private Controller controller;
    int year, month, day;

    EditText email, number, firstName, lastName, city, gender, birth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initComponents();
    }

    private void initComponents() {
        controller = Controller.getInstance();
        User user = controller.getDto().getUser();
        email = findViewById(R.id.email_edit_text);
        number = findViewById(R.id.number_edit_text);
        firstName = findViewById(R.id.name_edit_text);
        lastName = findViewById(R.id.lastname_edit_text);
        city = findViewById(R.id.city_edit_text);
        gender = findViewById(R.id.gender_edit_text);
        birth = findViewById(R.id.bday_edit_text);
        email.setText(user.getEmail());
        Date date = user.getBirthday().toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        birth.setText(dateString);
        //number.setText(user.getPhone());
        firstName.setText(user.getFirstname());
        lastName.setText(user.getLastname());
        city.setText(user.getCity());
        gender.setText(user.getGender());
    }

    public void updateUserProfileListener(View view)
    {
        User user = controller.getDto().getUser();
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String mailText = email.getText().toString();
        //Date birthday = new Date();//birth.getText().toString();
        int phone = Integer.parseInt(number.getText().toString());
        String city_ = city.getText().toString();

        user.setFirstname(fName);
        user.setLastname(lName);
        user.setEmail(mailText);
        //user.birthday = new Timestamp(birthday);
        user.setPhone(phone);
        user.setCity(city_);
        try
        {
            controller.updateUser();
        }
        catch (ControlException e)
        {
            Log.e("Error", e.getMessage());
        }
    }

}
