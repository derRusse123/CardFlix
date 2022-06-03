package com.example.cardflix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initialisation();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        }, 1000);

    }
    private void initialisation(){
        //Hier wird alles initialisiert. z.b. Datenbank, die automatische anmeldung, Api Call, etc...
    }
}