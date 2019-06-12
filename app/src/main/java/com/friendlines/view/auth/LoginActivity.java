package com.friendlines.view.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.friendlines.R;
import com.friendlines.controller.ControlException;
import com.friendlines.controller.Controller;
import com.friendlines.controller.listeners.TaskListener;
import com.friendlines.view.MainActivity;

public class LoginActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
    }

    private void initComponents(){
        mActionBarToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void signIn(View view)
    {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Controller.getInstance().signIn(this, email, password, new TaskListener<Void>() {
            @Override
            public void onSuccess(Void object) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

            @Override
            public void onFailure(ControlException exception) {
                Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToRecoverPassword(View view) {
        startActivity(new Intent(getApplicationContext(), RecoverPasswordActivity.class));
    }
}
