package com.example.cardflix.ui.search;

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

public class Search_RecyclerViewAdapter extends RecyclerView.Adapter<Search_RecyclerViewAdapter.MyViewHolder>{
    Context context;
    ArrayList<MyCard> searchModels;
    private final RecyclerViewInterface recyclerViewInterface;

    public Search_RecyclerViewAdapter(Context context, ArrayList<MyCard> searchModels, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.searchModels = searchModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public Search_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_search,parent,false);
        return new Search_RecyclerViewAdapter.MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.searchName.setText(searchModels.get(position).getName());
        Picasso.get().load(searchModels.get(position).getPicture()).into(holder.searchPicture);
    }

    @Override
    public int getItemCount() {
        return searchModels.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView searchName;
        ImageView searchPicture;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            searchName = itemView.findViewById(R.id.tv_Search_Card_Name);
            searchPicture = itemView.findViewById(R.id.iv_Search_Card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getBindingAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onRecyclerItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
