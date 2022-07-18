package com.example.cardflix.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardflix.MyCard;
import com.example.cardflix.R;
import com.example.cardflix.RecyclerViewInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Suggestion_RecyclerViewAdapter extends RecyclerView.Adapter<Suggestion_RecyclerViewAdapter.MyViewHolder>{
    Context context;
    ArrayList<MyCard> suggestionModels;
    private final RecyclerViewInterface recyclerViewInterface;

    public Suggestion_RecyclerViewAdapter(Context context, ArrayList<MyCard> suggestionModels,RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.suggestionModels = suggestionModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Suggestion_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_suggestion,parent,false);
        return new Suggestion_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Suggestion_RecyclerViewAdapter.MyViewHolder holder, int position) {
        if(suggestionModels.get(position).getName().length() >25) {
            String shortName = "";
            for(int i = 0; i <25; i++){
                shortName = shortName + suggestionModels.get(position).getName().charAt(i);
            }
            holder.suggestionName.setText(shortName);
        }else{
            holder.suggestionName.setText(suggestionModels.get(position).getName());
        }
        String textOutput = suggestionModels.get(position).getPrice() + "$";
        holder.suggestionPrice.setText(textOutput);
        Picasso.get().load(suggestionModels.get(position).getPicture()).into(holder.suggestionPicture);
    }

    @Override
    public int getItemCount() {
        return suggestionModels.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView suggestionName, suggestionPrice;
        ImageView suggestionPicture;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            suggestionName = itemView.findViewById(R.id.tv_Suggestion_Card_Name);
            suggestionPrice = itemView.findViewById(R.id.tv_Suggestion_Card_Price);
            suggestionPicture = itemView.findViewById(R.id.iv_Suggestion_Picture);

            itemView.setOnClickListener(view -> {
                if(recyclerViewInterface != null){
                    int pos = getBindingAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onRecyclerItemClick(pos,1);
                    }
                }
            });
        }
    }
}
