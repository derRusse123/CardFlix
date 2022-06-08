package com.example.cardflix;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnLoginButton;
    private TextView registration;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        inputEmail = findViewById(R.id.et_Email);
        inputPassword = findViewById(R.id.et_Password);
        btnLoginButton = findViewById(R.id.btn_Login);
        registration = findViewById(R.id.tv_Register);

        mAuth = FirebaseAuth.getInstance();

        btnLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputEmail.getText().length() != 0 && inputPassword.getText().length() != 0){ // Sollte nicht leer sein, sonst kommt exception
                    String eMail = inputEmail.getText().toString();
                    String password = inputPassword.getText().toString();

                    signIn(eMail, password); // Wichtig und richtig
                }
                else{
//TODO: PopUp: Gib was ein du Faggit
                    System.out.println("Suck on dis nuts");
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(mAuth.getCurrentUser() != null){
            reload(); // User ist bereits eingeloggt
        }
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println("Sei der aal");
                        if (task.isSuccessful()) {
                            System.out.println("signInWithEmail:success");
                            if(mAuth.getCurrentUser() != null){
                                System.out.println("Login Successful");
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            }
                        } else {

// TODO: Popup von task.getException()
                            System.out.println("signInWithEmail:failure " + task.getException());
                        }
                    }
                });
    }

    public void onClickRegistration(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    private void reload() { }

    private void updateUI() {
        //Show PopUp or something
    }
}