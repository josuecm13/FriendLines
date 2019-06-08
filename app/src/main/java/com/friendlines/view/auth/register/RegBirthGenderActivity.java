package com.friendlines.view.auth.register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.friendlines.R;

import java.util.Calendar;

public class RegBirthGenderActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    Button birthday;

    //Date Variables
    int year, month, day;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_birth_gender);
        initComponents();
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
        year = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthday.setText(String.format("%02d / %02d / %d",dayOfMonth,month ,year));
                    }
                }
                , year, month, day);
        dialog.show();
    }

    public void goNext(View view) {
        //TODO: validar, obtener datos;
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }
}
