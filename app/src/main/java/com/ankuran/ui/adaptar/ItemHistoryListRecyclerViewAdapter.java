package com.ankuran.ui.adaptar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ankuran.R;
import com.ankuran.model.ItemHistory;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.util.AppUtils;

import java.util.List;

public class ItemHistoryListRecyclerViewAdapter extends RecyclerView.Adapter<ItemHistoryListRecyclerViewAdapter.ViewHolder> {

    private List<ItemHistory> itemHistoryList;
    OnRecyclerItemClickListener onRecyclerItemClickListener;
    Context mContext;


    public void setItemHistoryList(List<ItemHistory> itemHistoryList) {
        this.itemHistoryList = itemHistoryList;
    }

    public ItemHistoryListRecyclerViewAdapter(List<ItemHistory> itemHistoryList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.itemHistoryList = itemHistoryList;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;

    }


    @Override
    public ItemHistoryListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_activity_list, parent, false);

        return new ItemHistoryListRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemHistoryListRecyclerViewAdapter.ViewHolder holder, int position) {
        ItemHistory itemHistory = itemHistoryList.get(position);
        holder.bind(itemHistory, onRecyclerItemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return itemHistoryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate, txtItem, txtQuantity, txtDueAmount;

        ViewHolder(View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.date);
            txtItem = itemView.findViewById(R.id.item);
            txtQuantity = itemView.findViewById(R.id.quantity);
            txtDueAmount = itemView.findViewById(R.id.dueAmount);
        }

        public void bind(final ItemHistory itemHistory, final OnRecyclerItemClickListener listener, final int position) {

            if (!TextUtils.isEmpty(itemHistory.getTimeCreated()))
                txtDate.setText(AppUtils.getReadableDate(itemHistory.getTimeCreated()));

            txtDueAmount.setText("Rs"+itemHistory.getTotalAmount());
            txtItem.setVisibility(View.INVISIBLE);
            txtQuantity.setVisibility(View.INVISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, position);
                }
            });
        }
    }
}

