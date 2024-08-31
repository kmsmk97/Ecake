package com.example.ecake;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class TouchHelper extends ItemTouchHelper.SimpleCallback {

    private feedAdapter adapter;
    //swiper
    public TouchHelper(feedAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }
    //No touch this method
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        //left it goes to update form
        if(direction==ItemTouchHelper.LEFT){
            adapter.updateFeed(position);
            adapter.notifyDataSetChanged();

        }else{
           adapter.deleteFeed(position);
        }
    }

}
