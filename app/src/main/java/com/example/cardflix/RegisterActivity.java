package com.example.cardflix;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputRepeatPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();
        inputEmail = findViewById(R.id.et_Email_Register);
        inputPassword = findViewById(R.id.et_Password_Register);
        inputRepeatPassword = findViewById(R.id.et_Password_Register_Repeat);
        Button btnRegistration = findViewById(R.id.btn_Register);

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("en");

        btnRegistration.setOnClickListener(view -> {
            // Min-length is 6
            if(inputEmail.getText().length() >= 6 && inputPassword.getText().length() >= 6){
                String eMail = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                if(!inputPassword.getText().equals(inputRepeatPassword.getText())){
                    signUp(eMail, password);
                }
                else{
                    createAlert("Passwords don't Match",0);
                }
            }
            else{
                createAlert("Password too short",0);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser() != null){
            // User already signed In
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        }
    }

    public void signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        System.out.println("createUserWithEmail: success");
                        sendEmailVerification();
                    } else {
                        // sign in fails
                        createAlert(Objects.requireNonNull(task.getException()).getMessage(),0);
                        System.out.println("createUserWithEmail:failed " + task.getException());
                    }
                });
    }


    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        Objects.requireNonNull(user).sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        createAlert("Verification Email has been sent",1);
                        System.out.println("Email send to " + user.getEmail());
                    }
                });
    }


    private void createAlert(String message, int type){
        if(type == 0) {
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();
        }
        else if(type == 1) {
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("OK", (dialogInterface, i) -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)))
                    .show();
        }
    }
}