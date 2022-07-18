package com.example.cardflix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("MainActivity got created");
        GlobalCardList myGlobalList = GlobalCardList.getInstance(this);
        myGlobalList.setCardsLoadedListener(() -> {
            Intent intent =  new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}