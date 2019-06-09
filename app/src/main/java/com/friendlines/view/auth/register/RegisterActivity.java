package com.friendlines.view.auth.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.friendlines.R;
import com.friendlines.controller.Controller;

public class RegisterActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    EditText password;
    EditText confirmPassword;
    Button btnConfirm;
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();
        controller = Controller.getInstance();
        password = findViewById(R.id.pass1_edit_text);
        confirmPassword = findViewById(R.id.pass2_edit_text);
        btnConfirm = findViewById(R.id.next_button);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(confirmPassword.getText().toString()))
                {
                    //TODO: show message error
                    btnConfirm.setEnabled(false);
                }
                else
                {
                    //TODO: show message ok
                    btnConfirm.setEnabled(true);
                }
            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(password.getText().toString()))
                {
                    //TODO: show message error
                    btnConfirm.setEnabled(false);
                }
                else
                {
                    //TODO: show message ok
                    btnConfirm.setEnabled(true);
                }
            }
        });
    }

    private void initComponents(){
        mActionBarToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void createAccount(View view) {
        if(password.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this, "Not valid password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            EditText email = findViewById(R.id.email_edit_text);
            controller.getDto().getUser().setEmail(email.getText().toString());
            controller.getDto().getUser().setPassword(password.getText().toString());
        }
    }
}
