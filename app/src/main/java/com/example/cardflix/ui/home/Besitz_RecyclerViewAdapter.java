package com.example.cardflix.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardflix.R;

import java.util.ArrayList;

public class Besitz_RecyclerViewAdapter extends RecyclerView.Adapter<Besitz_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<BesitzModel> besitzModels;
    public Besitz_RecyclerViewAdapter(Context context, ArrayList<BesitzModel> besitzModels){
    this.context = context;
    this.besitzModels = besitzModels;
    }

    @NonNull
    @Override
    public Besitz_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //aufbau des Layouts(Den look für den layout geben)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_home,parent,false);
        return new Besitz_RecyclerViewAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Besitz_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Die werte in die einzelnen Views eintragen
        //basierend auf der Position des Recycler Views
        holder.tvTitle.setText(besitzModels.get(position).getBesitzName());
        holder.tvBeschreibung.setText(besitzModels.get(position).getBesitzBeschreibung());
        holder.imageView.setImageResource(besitzModels.get(position).getImage());
        holder.btnBesitz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = besitzModels.get(position).getBesitzBeschreibung();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
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
        Button btnBesitz;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnBesitz = itemView.findViewById(R.id.btn_Besitz_ViewMore);
            imageView = itemView.findViewById(R.id.iv_Besitz_Picture);
            tvTitle = itemView.findViewById(R.id.tv_Title);
            tvBeschreibung = itemView.findViewById(R.id.tv_Beschreibung);

        }
    }
}
