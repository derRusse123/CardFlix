package com.example.cardflix;

import com.example.cardflix.cardApi.ApiCaller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private ApiCaller apiCaller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        }, 1000);

    }
    private void init(){
        //Hier wird alles initialisiert. z.b. Datenbank, die automatische anmeldung, Api Call, etc...
        apiCaller = new ApiCaller();

    }
}