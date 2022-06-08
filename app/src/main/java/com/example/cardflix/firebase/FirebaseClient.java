package com.example.cardflix.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;

import java.util.concurrent.Executor;

public class FirebaseClient {
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public FirebaseClient(){
        this.mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth() {
        return this.mAuth;
    }

    public void updateUser() {
        this.user = mAuth.getCurrentUser();
    }

    public FirebaseUser getUser() {
        return this.user;
    }
}
