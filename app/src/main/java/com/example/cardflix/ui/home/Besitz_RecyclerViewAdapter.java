package com.example.cardflix.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardflix.MoreSuggestedCards;
import com.example.cardflix.MyCard;
import com.example.cardflix.R;
import com.example.cardflix.RecyclerViewInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Besitz_RecyclerViewAdapter extends RecyclerView.Adapter<Besitz_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<MyCard> besitzModels;
    private final RecyclerViewInterface recyclerViewInterface;
    public Besitz_RecyclerViewAdapter(Context context, ArrayList<MyCard> besitzModels, RecyclerViewInterface recyclerViewInterface){
    this.context = context;
    this.besitzModels = besitzModels;
    this.recyclerViewInterface = recyclerViewInterface;


    }

    @NonNull
    @Override
    public Besitz_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //aufbau des Layouts(Den look für den layout geben)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_home,parent,false);
        return new Besitz_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull Besitz_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Die werte in die einzelnen Views eintragen
        //basierend auf der Position des Recycler Views
        if(!(context instanceof MoreSuggestedCards)) {
            holder.tvBeschreibung.setText(besitzModels.get(position).getRarityCardsCode().get(besitzModels.get(position).getRarityIndex()));
        }else{
            holder.tvBeschreibung.setText(besitzModels.get(position).getPrice());
        }
        holder.tvTitle.setText(besitzModels.get(position).getName());
        Picasso.get().load(besitzModels.get(position).getPicture()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        //wie viele Items habe ich
        return besitzModels.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        // die sachen aus der layout file holen
        // fast wie die onCreate methode
        ImageView imageView;
        TextView tvTitle, tvBeschreibung;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_Besitz_Picture);
            tvTitle = itemView.findViewById(R.id.tv_Title);
            tvBeschreibung = itemView.findViewById(R.id.tv_Beschreibung);

            itemView.setOnClickListener(view -> {
                if(recyclerViewInterface != null){
                    int pos = getBindingAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onRecyclerItemClick(pos,0);
                    }
                }
            });

        }
    }
}
