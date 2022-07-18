package com.example.cardflix.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardflix.MoreSuggestedCardsActivity;
import com.example.cardflix.MyCard;
import com.example.cardflix.R;
import com.example.cardflix.RecyclerViewInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Collection_RecyclerViewAdapter extends RecyclerView.Adapter<Collection_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<MyCard> collectionModels;
    private final RecyclerViewInterface recyclerViewInterface;
    public Collection_RecyclerViewAdapter(Context context, ArrayList<MyCard> collectionModels, RecyclerViewInterface recyclerViewInterface){
    this.context = context;
    this.collectionModels = collectionModels;
    this.recyclerViewInterface = recyclerViewInterface;


    }

    @NonNull
    @Override
    public Collection_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //aufbau des Layouts(Den look für den layout geben)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_home,parent,false);
        return new Collection_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull Collection_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Die werte in die einzelnen Views eintragen
        //basierend auf der Position des Recycler Views
        if(!(context instanceof MoreSuggestedCardsActivity)) {
            holder.tvBeschreibung.setText(collectionModels.get(position).getRarityCardsCode().get(collectionModels.get(position).getRarityIndex()));
        }else{
            holder.tvBeschreibung.setText(collectionModels.get(position).getPrice());
        }
        holder.tvTitle.setText(collectionModels.get(position).getName());
        Picasso.get().load(collectionModels.get(position).getPicture()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        //wie viele Items habe ich
        return collectionModels.size();
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
