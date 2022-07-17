package com.example.cardflix;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.core.text.HtmlCompat;

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
        //Card
        myObj = getIntent().getParcelableExtra("objectValues");
        Picasso.get().load(myObj.getPicture()).into(this.picture);
        name.setText(myObj.getName());
        price.setText(myObj.getPrice());
        //Bottom Text
        type.setText(HtmlCompat.fromHtml("<b>Type: </b>" + myObj.getType(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        desc.setText(HtmlCompat.fromHtml("<h3><b>Description</b></h3> \n" + myObj.getDesc(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        atk_Def_Level.setText(HtmlCompat.fromHtml("<b>Atk: </b>" + myObj.getAtk() + " <b>Def: </b>" + myObj.getDefense() + " <b>Level: </b>" + myObj.getLevel(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        race.setText(HtmlCompat.fromHtml("<b>Race: </b>" + myObj.getRace(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        attribute.setText(HtmlCompat.fromHtml("<b>Attribute: </b>" + myObj.getAttribute(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        archetype.setText(HtmlCompat.fromHtml("<b>Archetype: </b>" + myObj.getArchetype(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        checkIfUserHaveCard(myObj.getRarityIndex());
        ArrayAdapter<CharSequence> adapter =new ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item ,myObj.getRarityCardsCode());
        raritySpinner.setAdapter(adapter);
        raritySpinner.setSelection(myObj.getRarityIndex());

        btnAddToMyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counterGroup.getVisibility() == View.INVISIBLE) {
                    globalList.cardList.add(myObj);
                    //ToDo: REPLACE myObj.getType() with actual selected Type
                    globalList.saveCard(myObj.getName(), myObj.getType(), myObj.getAmount());
                }else{
                    globalList.deleteCard(myObj.getKey());
                    globalList.cardList.remove(myObj);
                }
                checkIfUserHaveCard(myObj.getRarityIndex());
            }
        });

        btnAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myObj.setAmount(myObj.getAmount()+1);
                etAmountText.setText(String.valueOf(myObj.getAmount()));
                if(myObj.getKey()!= null){
                    System.out.println(1);
                    globalList.updateCard(myObj.getKey(), myObj.getAmount());
                }
            }
        });

        btnDecreaseAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAmountText.setText(String.valueOf(myObj.getAmount()));
                if(myObj.getKey()!= null){
                    System.out.println(2);
                    globalList.updateCard(myObj.getKey(), myObj.getAmount());
                }
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
                        myObj.setAmount(Integer.parseInt(charSequence.toString()));
                    }else{
                       etAmountText.setText(String.valueOf(1));
                       myObj.setAmount(1);
                    }
                    //HIS SHIT BREAKS EVERYTHING xD
                    if(myObj.getKey()!= null){
                       System.out.println(3);
                       globalList.updateCard(myObj.getKey(), myObj.getAmount());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        raritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                price.setText(myObj.getRarityCardsPrice().get(i) + "$");
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
            myObj.setAmount(1);
            myObj = new MyCard(myObj);
            myObj.setRarityIndex(rarityIndex);
            myObj.setKey(null);
        }

    }
}