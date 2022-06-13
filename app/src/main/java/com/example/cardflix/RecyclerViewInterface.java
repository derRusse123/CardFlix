package com.example.cardflix;

public interface RecyclerViewInterface {
    default void onRecyclerItemClick(int position) {

    }

    default void onRecyclerItemClick(int position, int recyclerViewType) {

    }
}
