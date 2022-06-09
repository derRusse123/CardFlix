package com.example.cardflix;

import android.os.Bundle;
import com.example.cardflix.cardApi.ApiCaller;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cardflix.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        ApiCaller apiCaller = new ApiCaller(this);
        System.out.println("ASYNC CALLS INCOMING");
        System.out.println("Call getCardsByName()");
        apiCaller.getCardsByName("Tornado Dragon");
        System.out.println("Call getFilteredCards()");
        apiCaller.getFilteredCards("Dragon");
        System.out.println("Call getSuggestedCard()");
        apiCaller.getSuggestedCard();

        System.out.println(this.toString());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
           // Go back to Login since User == null
        }
        else{
            //Feel free to get some Data <3
            // Logout with: mAuth.signOut(); ez
        }
    }

    public void receiveCardsByName(JSONArray array) {
        System.out.println("receiveCardsByName " + array.toString());
        System.out.println(this.toString());
    }

    public void receiveSuggestedCard(JSONObject object) {
        System.out.println("receiveSuggestedCard " + object.toString());
        System.out.println(this.toString());
    }

    public void receiveFilteredCards(JSONArray array) {
        System.out.println("receiveFilteredCards " + array.toString());
        System.out.println(this.toString());
    }

}