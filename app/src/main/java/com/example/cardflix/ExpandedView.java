package com.example.cardflix;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cardflix.cardApi.APIQueue;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ExpandedView extends AppCompatActivity {

    private JSONObject jsonObj;
    private TextView name, type, desc, atk_Def_Level, race, attribute, archetype, price;
    private ImageView picture;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_view);
        ActionBar actionBar = getSupportActionBar();
        GlobalCardList myList = GlobalCardList.getInstance(getApplicationContext());
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        btnAdd = findViewById(R.id.btn_ExpandadView_AddRemove);
        price = findViewById(R.id.tv_ExpandedView_Price);
        name = findViewById(R.id.tv_ExpandedView_Name);
        type = findViewById(R.id.tv_ExpandedView_Type);
        desc = findViewById(R.id.tv_ExpandedView_Desc);
        atk_Def_Level = findViewById(R.id.tv_ExpandedView_AtcDefLevel);
        race = findViewById(R.id.tv_ExpandedView_Race);
        attribute = findViewById(R.id.tv_ExpandedView_Attribute);
        archetype = findViewById(R.id.tv_ExpandedView_Archetype);
        picture = findViewById(R.id.iv_ExpandedView_Picture);


        try {
            jsonObj = new JSONObject(getIntent().getStringExtra("objectValues"));
            Picasso.get().load(jsonObj.getJSONArray("card_images").getJSONObject(0).getString("image_url")).into(picture);
            name.setText(jsonObj.getString("name"));
            price.setText(jsonObj.getJSONArray("card_prices").getJSONObject(0).getString("cardmarket_price"));
            type.setText(jsonObj.getString("type"));
            desc.setText(jsonObj.getString("desc"));
            atk_Def_Level.setText("atk: " + jsonObj.getString("atk") + ", def: " + jsonObj.getString("def") + ", Level: "+ jsonObj.getString("level"));
            race.setText(jsonObj.getString("race"));
            attribute.setText(jsonObj.getString("attribute"));
            archetype.setText(jsonObj.getString("archetype"));
            if(myList.checkIfCardExists(jsonObj.getString("name"))){
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd.setText("Remove");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}