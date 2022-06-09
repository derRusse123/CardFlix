package com.example.cardflix;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.example.cardflix.cardApi.APICallbacks;
import com.example.cardflix.cardApi.APICalls;
import com.example.cardflix.cardApi.APIQueue;
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

public class HomeActivity extends AppCompatActivity implements APICallbacks {

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

        //Die folgenden 2 Zeilen überall benutzen wo du API calls brauchst und APICallbacks
        // als Interface implementieren
        APIQueue singletonQueue = APIQueue.getInstance(this.getApplicationContext());
        APICalls calls = new APICalls(this);
        singletonQueue.addToRequestQueue(calls.getFilteredCardsStringRequest("Cock"));

        //queue.add(calls.getFilteredCardsStringRequest("Dragon"));

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

    @Override
    public void cardsByNameCallback(JSONArray array) {
        System.out.println(array.toString());
    }

    @Override
    public void suggestedCardCallback(JSONObject object) {
        System.out.println(object.toString());
    }

    @Override
    public void filteredCardsCallback(JSONArray array) {
        System.out.println("Stay High");
        System.out.println(array.toString());
    }
}