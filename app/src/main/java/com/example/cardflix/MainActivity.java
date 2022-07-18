package com.example.cardflix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private GlobalCardList myGlobalList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myGlobalList = GlobalCardList.getInstance(this);
        myGlobalList.setCardsLoadedListener(new OnGetCardsCompleted() {
            @Override
            public void OnDataLoaded() {
                Intent intent =  new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
}