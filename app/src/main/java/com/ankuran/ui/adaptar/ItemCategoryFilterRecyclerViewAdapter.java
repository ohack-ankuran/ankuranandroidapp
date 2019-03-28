package com.ankuran.ui.adaptar;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankuran.R;
import com.ankuran.model.Item;
import com.ankuran.model.ItemCategory;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemCategoryFilterRecyclerViewAdapter extends RecyclerView.Adapter<ItemCategoryFilterRecyclerViewAdapter.ViewHolder> {

    private List<ItemCategory> mCategories;
    OnRecyclerItemClickListener onRecyclerItemCategoryClickListener;

    public ItemCategoryFilterRecyclerViewAdapter(List<ItemCategory> categories, OnRecyclerItemClickListener onRecyclerItemCategoryClickListener) {
        mCategories = categories;
        this.onRecyclerItemCategoryClickListener = onRecyclerItemCategoryClickListener;
    }


    public void setCategories(List<ItemCategory> categories) {
        mCategories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemCategoryView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_category, parent, false);
        return new ViewHolder(itemCategoryView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.bind(mCategories.get(position), onRecyclerItemCategoryClickListener,position);
        final int pos = position;
        ItemCategory itemCategory = mCategories.get(pos);
        holder.mCheckBox.setText(itemCategory.getCategory());
        holder.mCheckBox.setChecked(itemCategory.isSelected());
        holder.mCheckBox.setTag(itemCategory);
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                ItemCategory category = (ItemCategory) cb.getTag();
                category.setSelected(cb.isChecked());
                mCategories.get(pos).setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCheckBox;
        ViewHolder(View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.cb_item_category);
        }

//        public void bind(final ItemCategory itemCategory, final OnRecyclerItemClickListener listener, final int position) {
//            mCheckBox.setText(itemCategory.getCategory());
//            mCheckBox.setChecked(itemCategory.isSelected());
//            mCheckBox.setTag(itemCategory);
//            mCheckBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CheckBox cb = (CheckBox) v;
//                    ItemCategory category = (ItemCategory) cb.getTag();
//                    category.setSelected(cb.isChecked());
//                    mCategories.get(position).setSelected(cb.isChecked());
//                }
//            });
//        }
    }
}
