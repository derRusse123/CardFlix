package com.example.cardflix;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

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
                // Input shouldn't be null or else exception
                if(inputEmail.getText().length() != 0 && inputPassword.getText().length() != 0){
                    String eMail = inputEmail.getText().toString();
                    String password = inputPassword.getText().toString();

                    signIn(eMail, password);
                }
                else{
//TODO: PopUp: No Input
                    System.out.println("No Input");
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        final FirebaseUser user = mAuth.getCurrentUser();
        Log.d("user", String.valueOf(user));
        // Check if user is signed in (non-null) and update UI accordingly.
        if(user != null){
            // User already signed In
            System.out.println("Login: User already logged in");
            if(user.isEmailVerified()){
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            }
            else{
                mAuth.signOut();
            }
        }
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("signInWithEmail:success");
                            if(mAuth.getCurrentUser() != null){
                                System.out.println("Login Successful");
                                if(mAuth.getCurrentUser().isEmailVerified()){
                                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                }
                                else{
                                    System.out.println("Email not verified");
// TODO: Popup please verify your email
                                }
                            }
                        } else {

// TODO: Popup von task.getException() -> Says, doesn't exist or something
                            System.out.println("signInWithEmail:failure " + task.getException());
                        }
                    }
                });
    }

    public void onClickRegistration(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}