package com.example.cardflix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnLoginButton;
    private TextView registration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        inputEmail = findViewById(R.id.et_Email);
        inputPassword = findViewById(R.id.et_Password);
        btnLoginButton = findViewById(R.id.btn_Login);
        registration = findViewById(R.id.tv_Register);

        btnLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eMail = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                if(validateAccount()){
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                }
            }
        });

    }

    private boolean validateAccount(){
        //Hier kommt die kontrolle mit der Datenbank
        return true; // noch zu false wechseln.
    }

    public void onClickRegistration(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}