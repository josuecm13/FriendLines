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
import com.friendlines.view.MainActivity;

public class LoginActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    private void initComponents(){
        mActionBarToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void signIn(View view)
    {
        //Attemp sign In
        EditText email = findViewById(R.id.email_edit_text);
        EditText password = findViewById(R.id.password_edit_text);
        try
        {
            Controller.getInstance().signInUserAuthentication(email.getText().toString(), password.getText().toString());
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        catch (ControlException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void goToRecoverPassword(View view) {
        startActivity(new Intent(getApplicationContext(), RecoverPasswordActivity.class));
    }
}
