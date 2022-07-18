package com.example.cardflix;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        inputEmail = findViewById(R.id.et_Email);
        inputPassword = findViewById(R.id.et_Password);
        Button btnLoginButton = findViewById(R.id.btn_Login);

        btnLoginButton.setOnClickListener(view -> {
            // Input shouldn't be null or else exception
            if(inputEmail.getText().length() != 0 && inputPassword.getText().length() != 0){
                String eMail = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                signIn(eMail, password);
            }
            else{
                createAlert("No Input");
                System.out.println("No Input");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Log.d("user", String.valueOf(user));
        if(user != null) { user.reload(); }
        // Check if user is signed in (non-null) and update UI accordingly.
        if(user != null){
            // User already signed In
            System.out.println("Login: User already logged in");
            if(user.isEmailVerified()){
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
            else{
                mAuth.signOut();
            }
        }
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( this, task -> {
                    if (task.isSuccessful()) {
                        System.out.println("signInWithEmail:success");
                        if(mAuth.getCurrentUser() != null){
                            System.out.println("Login Successful");
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                System.out.println("Email is verified");
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            }
                            else{
                                System.out.println("Email not verified");
                                createAlert("Please verify your Email");
                            }
                        }
                    } else {

                        createAlert(Objects.requireNonNull(task.getException()).getMessage());
                        System.out.println("signInWithEmail:failure " + task.getException());
                    }
                });
    }

    public void onClickRegistration(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    private void createAlert(String message){
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}