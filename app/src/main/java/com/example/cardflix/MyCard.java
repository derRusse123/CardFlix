package com.example.cardflix;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MyCard implements Parcelable{
   private JSONObject jsonObj;
   private String name;
   private String type;
   private String desc;
   private String atk;
   private String defense;
   private String level;
   private String race;
   private String attribute;
   private String archetype;
   private String picture;
   private String price;
   private int amount = 0;
   private ArrayList<String> rarityCardsPrice = new ArrayList<>();
   private ArrayList<String> rarityCardsCode = new ArrayList<>();
   private int rarityIndex = 0;

   public MyCard(JSONObject obj) throws JSONException {
      if(obj.has("name")){this.name = obj.getString("name");}else{this.name = "None";}
      if(obj.has("type")){this.type = obj.getString("type");}else{this.type = "None";}
      if(obj.has("desc")){this.desc = obj.getString("desc");}else{this.desc = "None";}
      if(obj.has("atk")){this.atk = obj.getString("atk");}else{this.atk = "None";}
      if(obj.has("def")){this.defense = obj.getString("def");}else{this.defense = "None";}
      if(obj.has("level")){this.level = obj.getString("level");}else{this.level = "None";}
      if(obj.has("race")){this.race = obj.getString("race");}else{this.race = "None";}
      if(obj.has("attribute")){this.attribute = obj.getString("attribute");}else{this.attribute = "None";}
      if(obj.has("archetype")){this.archetype = obj.getString("archetype");}else{this.archetype = "None";}
      this.picture = obj.getJSONArray("card_images").getJSONObject(0).getString("image_url");
      if(obj.has("card_prices")){this.price = obj.getJSONArray("card_prices").getJSONObject(0).getString("cardmarket_price");}else{this.price = "None";}
      rarityCardsCode.add("default");
      rarityCardsPrice.add(Float.valueOf(price.replaceAll("[^\\d.]", "")).toString());
      if(obj.has("card_sets")){
         JSONArray a = obj.getJSONArray("card_sets");
         for(int i = 0; i< a.length(); i++){
            rarityCardsCode.add(a.getJSONObject(i).getString("set_code"));
            float priceValue = Float.valueOf(a.getJSONObject(i).getString("set_price").replaceAll("[^\\d.]", ""));
            if(priceValue < 0.01){
               priceValue = 0.01f;
            }
            priceValue = (float)(Math.round(priceValue * 100.0) / 100.0);
            rarityCardsPrice.add(String.valueOf(priceValue));
         }
      }
   }

   public MyCard(MyCard cloneCard){
      this.name = cloneCard.name;
      this.type = cloneCard.type;
      this.desc= cloneCard.desc;
      this.atk= cloneCard.atk;
      this.defense= cloneCard.defense;
      this.level= cloneCard.level;
      this.race= cloneCard.race;
      this.attribute= cloneCard.attribute;
      this.archetype= cloneCard.archetype;
      this.picture= cloneCard.picture;
      this.price= cloneCard.price;
      this.rarityCardsPrice= cloneCard.rarityCardsPrice;
      this.rarityCardsCode = cloneCard.rarityCardsCode;
      this.rarityIndex= cloneCard.rarityIndex;
      this.amount = cloneCard.amount;
   }

   protected MyCard(Parcel in) {
      name = in.readString();
      type = in.readString();
      desc = in.readString();
      atk = in.readString();
      defense = in.readString();
      level = in.readString();
      race = in.readString();
      attribute = in.readString();
      archetype = in.readString();
      picture = in.readString();
      price = in.readString();
      rarityIndex = in.readInt();
      amount = in.readInt();
      rarityCardsCode = in.readArrayList(null);
      rarityCardsPrice = in.readArrayList(null);

   }

   public static final Creator<MyCard> CREATOR = new Creator<MyCard>() {
      @Override
      public MyCard createFromParcel(Parcel in) {
         return new MyCard(in);
      }

      @Override
      public MyCard[] newArray(int size) {
         return new MyCard[size];
      }
   };

   public String getName() {
      return name;
   }

   public String getType() {
      return type;
   }

   public String getDesc() {
      return desc;
   }

   public String getAtk() {
      return atk;
   }

   public int getAmount(){return amount;}

   public String getDefense() {
      return defense;
   }

   public ArrayList<String> getRarityCardsCode(){
         return  rarityCardsCode;
   }

   public ArrayList<String> getRarityCardsPrice(){
      return  rarityCardsPrice;
   }

   public String getLevel() {
      return level;
   }

   public String getRace() {
      return race;
   }

   public String getAttribute() {
      return attribute;
   }

   public String getArchetype() {
      return archetype;
   }

   public String getPicture() {
      return picture;
   }

   public String getPrice() {
      return price;
   }

   public int getRarityIndex() {
      return rarityIndex;
   }


   public void setRarityIndex(int index) {
      rarityIndex = index;
   }

   public void setAmount(int val){
      amount = val;
   }
   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(name);
      parcel.writeString(type);
      parcel.writeString(desc);
      parcel.writeString(atk);
      parcel.writeString(defense);
      parcel.writeString(level);
      parcel.writeString(race);
      parcel.writeString(attribute);
      parcel.writeString(archetype);
      parcel.writeString(picture);
      parcel.writeString(price);
      parcel.writeInt(rarityIndex);
      parcel.writeInt(amount);
      parcel.writeList(rarityCardsCode);
      parcel.writeList(rarityCardsPrice);
   }
}
