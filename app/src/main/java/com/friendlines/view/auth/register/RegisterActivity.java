package com.friendlines.view.auth.register;

import android.support.design.widget.TextInputLayout;
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
                    btnConfirm.setEnabled(false);
                    TextInputLayout til = findViewById(R.id.pass2_layout);
                    til.setError("Must be the same password");
                }
                else
                {
                    TextInputLayout til = findViewById(R.id.pass2_layout);
                    til.setError(null);
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
                    btnConfirm.setEnabled(false);
                    TextInputLayout til = findViewById(R.id.pass2_layout);
                    til.setError("Must be the same password");
                }
                else
                {
                    TextInputLayout til = findViewById(R.id.pass2_layout);
                    til.setError(null);
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
            Toast.makeText(this, "Password mustn't be empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            EditText email = findViewById(R.id.email_edit_text);
            controller.getDto().getUser().setEmail(email.getText().toString());
            controller.getDto().getUser().setPassword(password.getText().toString());
        }
    }
}
