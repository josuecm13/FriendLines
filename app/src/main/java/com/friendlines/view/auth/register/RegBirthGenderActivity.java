package com.friendlines.view.auth.register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.friendlines.R;
import com.friendlines.controller.Controller;
import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class RegBirthGenderActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    Button birthday;

    //Date Variables
    int year, month, day;
    String gender;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_birth_gender);
        initComponents();
        controller = Controller.getInstance();
    }

    private void initComponents(){
        mActionBarToolbar =  findViewById(R.id.toolbar);
        birthday = findViewById(R.id.date_picker);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void selectDate(View view) {
        //Dialog
        final Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthday.setText(String.format("%02d / %02d / %d",dayOfMonth,month + 1 ,year));
                        RegBirthGenderActivity.this.year = year;
                        RegBirthGenderActivity.this.month = month;
                        RegBirthGenderActivity.this.day = dayOfMonth;
                    }
                }
                , year, month, day);
        dialog.show();
    }

    public void goNext(View view)
    {
        if(!checkDate())
        {
            Date date = new Date(year - 1900, month, day);
            Log.e("Error", date.toString());
            Toast.makeText(this, "Not a valid birthday", Toast.LENGTH_SHORT).show();
        }
        else
        {
            controller.getDto().getUser().setBirthday(new Timestamp(new Date(year, month, day)));
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }
    }

    public boolean checkDate()
    {
        Date date = new Date(year - 1900, month, day);
        Date nowMinus12 = Calendar.getInstance().getTime();
        nowMinus12.setYear(nowMinus12.getYear() - 12);
        return date.before(nowMinus12);
    }
}
