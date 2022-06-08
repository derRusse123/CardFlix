package com.example.cardflix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputRepeatPassword;
    private Button btnRegistartion;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        inputEmail = findViewById(R.id.et_Email_Register);
        inputPassword = findViewById(R.id.et_Password_Register);
        inputRepeatPassword = findViewById(R.id.et_Password_Register_Repeat);
        btnRegistartion = findViewById(R.id.btn_Register);

        mAuth = FirebaseAuth.getInstance();

        btnRegistartion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mindestlänge von Email/Passwort erfüllen >=6 oder sonst was
                if(inputEmail.getText().length() >= 6 && inputPassword.getText().length() >= 6){
                    String eMail = inputEmail.getText().toString();
                    String password = inputPassword.getText().toString();
                    if(!inputPassword.getText().equals(inputRepeatPassword.getText())){
                        signUp(eMail, password);
                    }
                    else{
//TODO: PopUp: PWs stimmen nicht überein
                    }
                }
                else{
//TODO: PopUp: Walla mach mal bessere Passwort
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

    public boolean signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            System.out.println("createUserWithEmail:success");
                            startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                        } else {
                            // sign in fails
// TODO: Popup von task.getException()
                            System.out.println("createUserWithEmail:failed" + task.getException());
                        }

                    }
                });
        if(mAuth.getCurrentUser() != null){
            return true;
        }
        return false;
    }

    /*
    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
        // [END send_email_verification]
    }
    */
    private void reload() { }
}