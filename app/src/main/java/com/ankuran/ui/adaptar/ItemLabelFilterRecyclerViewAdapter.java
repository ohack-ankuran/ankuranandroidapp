package com.ankuran.ui.adaptar;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankuran.R;
import com.ankuran.model.Item;
import com.ankuran.model.ItemLabel;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemLabelFilterRecyclerViewAdapter extends RecyclerView.Adapter<ItemLabelFilterRecyclerViewAdapter.ViewHolder> {

    private List<ItemLabel> mLabels;

    OnRecyclerItemClickListener onRecyclerItemLabelClickListener;


    public ItemLabelFilterRecyclerViewAdapter(List<ItemLabel> labels, OnRecyclerItemClickListener onRecyclerItemLabelClickListener) {
        mLabels = labels;
        this.onRecyclerItemLabelClickListener = onRecyclerItemLabelClickListener;
    }


    public void setLabels(List<ItemLabel> labels) {
        mLabels = labels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLabelView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_label, parent, false);

        return new ViewHolder(itemLabelView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mLabels.get(position), onRecyclerItemLabelClickListener,position);
    }

    @Override
    public int getItemCount() {
        return mLabels.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button mBtnLabel;
        ViewHolder(View labelView) {
            super(labelView);
            mBtnLabel = labelView.findViewById(R.id.btnItemLabel);

        }

        public void bind(final ItemLabel itemLabel, final OnRecyclerItemClickListener listener, final int position) {
            mBtnLabel.setText(itemLabel.getLabel());
            mBtnLabel.setTag(itemLabel);
            mBtnLabel.setHighlightColor(itemLabel.isSelected() ? Color.CYAN : Color.BLUE);
            mBtnLabel.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Button btn = (Button) v;
                    ItemLabel label = (ItemLabel) btn.getTag();
                    label.setSelected(btn.isSelected());
                    mBtnLabel.setHighlightColor(itemLabel.isSelected() ? Color.CYAN : Color.BLUE);
                    mLabels.get(position).setSelected(btn.isSelected());
                }
            });
        }
    }
}
