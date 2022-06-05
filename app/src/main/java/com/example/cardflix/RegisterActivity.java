package com.example.cardflix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputRepeatPassword;
    private Button btnRegistartion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        inputEmail = findViewById(R.id.et_Email_Register);
        inputPassword = findViewById(R.id.et_Password_Register);
        inputRepeatPassword = findViewById(R.id.et_Password_Register_Repeat);
        btnRegistartion = findViewById(R.id.btn_Register);

        btnRegistartion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(createAccount()){
                    startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                }
            }
        });

    }

    private boolean createAccount(){
        if(!inputPassword.getText().equals(inputRepeatPassword.getText())){
            return false;
        }
        return true; // später ändern auf false wenn man alles implementiert hat
    }
}