package com.ankuran.ui.adaptar;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankuran.R;
import com.ankuran.model.Item;
import com.ankuran.model.ItemFilterView;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.squareup.picasso.Picasso;



import java.util.List;

public class ItemFilterRecyclerViewAdapter extends RecyclerView.Adapter<ItemFilterRecyclerViewAdapter.ViewHolder> {

    private List<ItemFilterView> itemList;
    OnRecyclerItemClickListener onRecyclerItemClickListener;


    public ItemFilterRecyclerViewAdapter(List<ItemFilterView> itemList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.itemList = itemList;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }


    public void setItemList(List<ItemFilterView> itemList) {
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
        holder.bind(itemList.get(position),onRecyclerItemClickListener,position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView unitsAvailable;
        TextView itemCategory;
        TextView itemName;
        ImageView itemImage;
        ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item);
            unitsAvailable = itemView.findViewById(R.id.units);
            itemCategory = itemView.findViewById(R.id.category);
            itemImage = itemView.findViewById(R.id.thumbnail);
        }

        public void bind(final ItemFilterView itemFilterView, final OnRecyclerItemClickListener listener, final int position) {
            final Item item = itemFilterView.getItem();
            //TODO add image using picasso
            itemName.setText("Item: " + item.getName());
            int numAvailable = item.availableUnits;
            unitsAvailable.setText("Units: " + String.valueOf(numAvailable));
            itemCategory.setText("Category: " + item.category);
            if(!TextUtils.isEmpty(item.getPicture()))
            {

                Picasso.get().load(item.getPicture())
                        .into(itemImage);

            }
            itemView.setTag(item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(v,position,item);
                }
            });
        }
    }
}
