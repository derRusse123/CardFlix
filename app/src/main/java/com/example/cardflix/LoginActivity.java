package com.example.cardflix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        inputEmail = findViewById(R.id.et_Email);
        inputPassword = findViewById(R.id.et_Password);
        loginButton = findViewById(R.id.btn_Login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eMail = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                if(validateAccount()){

                }
            }
        });

    }

    boolean validateAccount(){
        //Hier kommt die kontrolle mit der Datenbank
        return true; // noch zu false wechseln.
    }
}