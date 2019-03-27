package com.ankuran.ui.adaptar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankuran.model.Item;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private List<Item> itemList;
    OnRecyclerItemClickListener onRecyclerItemClickListener;


    public ItemRecyclerViewAdapter(List<Item> itemList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.itemList = itemList;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }


    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_grid, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.bind(item,onRecyclerItemClickListener,position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView itemImage;
        ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item);
            itemImage = itemView.findViewById(R.id.thumbnail);

        }

        public void bind(final Item item, final OnRecyclerItemClickListener listener, final int position) {
            //TODO add image using picasso
            itemName.setText(item.getName());
            if(!TextUtils.isEmpty(item.getPicture()))
            {

                Picasso.get().load(item.getPicture())
                        .into(itemImage);

            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(v,position,item);
                }
            });
        }
    }
}
