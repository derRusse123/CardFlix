package com.example.cardflix;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ExpandedView extends AppCompatActivity {

    private MyCard myObj;
    private TextView name, type, desc, atk_Def_Level, race, attribute, archetype, price;
    private ImageView picture;
    private Button btnAddToMyCard, btnAddAmount, btnDecreaseAmount;
    private EditText etAmountText;
    private Group counterGroup;
    private GlobalCardList globalList;
    private Spinner raritySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialisation

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_view);
        ActionBar actionBar = getSupportActionBar();
        globalList = GlobalCardList.getInstance(getApplicationContext());
        btnAddToMyCard = findViewById(R.id.btn_ExpandadView_AddRemove);
        price = findViewById(R.id.tv_ExpandedView_Price);
        name = findViewById(R.id.tv_ExpandedView_Name);
        type = findViewById(R.id.tv_ExpandedView_Type);
        desc = findViewById(R.id.tv_ExpandedView_Desc);
        atk_Def_Level = findViewById(R.id.tv_ExpandedView_AtcDefLevel);
        race = findViewById(R.id.tv_ExpandedView_Race);
        attribute = findViewById(R.id.tv_ExpandedView_Attribute);
        archetype = findViewById(R.id.tv_ExpandedView_Archetype);
        picture = findViewById(R.id.iv_ExpandedView_Picture);
        counterGroup = findViewById(R.id.gr_ExpandedView_Counter);
        btnAddAmount = findViewById(R.id.btn_ExpandedView_CounterAdd);
        btnDecreaseAmount = findViewById(R.id.btn_ExpandedView_Counter_Decrease);
        etAmountText = findViewById(R.id.et_ExpandedView_Counter);
        raritySpinner = findViewById(R.id.sp_ExpandedView_Rarity);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Declaration
        myObj = getIntent().getParcelableExtra("objectValues");
        Picasso.get().load(myObj.getPicture()).into(this.picture);
        name.setText(myObj.getName());
        price.setText(myObj.getPrice());
        type.setText(myObj.getType());
        desc.setText(myObj.getDesc());
        atk_Def_Level.setText("atk: " + myObj.getAtk() + ", def: " + myObj.getDefense() + ", Level: "+ myObj.getLevel());
        race.setText(myObj.getRace());
        attribute.setText(myObj.getAttribute());
        archetype.setText(myObj.getArchetype());
        checkIfUserHaveCard(myObj.getRarityIndex());
        ArrayAdapter<CharSequence> adapter =new ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item ,myObj.getRarityCardsCode());
        raritySpinner.setAdapter(adapter);
        raritySpinner.setSelection(myObj.getRarityIndex());

        btnAddToMyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counterGroup.getVisibility() == View.INVISIBLE) {

                    globalList.cardList.add(myObj);
                }else{
                    globalList.cardList.remove(myObj);
                }
                checkIfUserHaveCard(myObj.getRarityIndex());
            }
        });

        btnAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnDecreaseAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        etAmountText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    if ((Character.getNumericValue(charSequence.charAt(0))) != 0) {
                    }else{
                       etAmountText.setText(String.valueOf(1));
                       myObj.setAmount(1);
                    }
                    etAmountText.setText(String.valueOf(Integer.parseInt(charSequence.toString())));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        raritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    checkIfUserHaveCard(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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

    private void checkIfUserHaveCard(int rarityIndex){
        int cardIndexInGlobal = 0;
        if((cardIndexInGlobal = globalList.checkIfCardExistsString(myObj.getName(),rarityIndex)) != -1) {
            myObj = globalList.cardList.get(cardIndexInGlobal);
            counterGroup.setVisibility(View.VISIBLE);
            etAmountText.setText(String.valueOf(myObj.getAmount()));
            btnAddToMyCard.setText("Remove");
        }else{
            counterGroup.setVisibility(View.INVISIBLE);
            btnAddToMyCard.setText("ADD");
            myObj = new MyCard(myObj);
            myObj.setRarityIndex(rarityIndex);
        }

    }
}