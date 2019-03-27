package com.ankuran.ui.adaptar;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ankuran.R;
import com.ankuran.model.Activity;
import com.ankuran.model.Settlement;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.util.AppUtils;

import java.util.List;

public class SettlementListRecyclerViewAdapter extends RecyclerView.Adapter<SettlementListRecyclerViewAdapter.ViewHolder> {

    private List<Settlement> settlementList;
    OnRecyclerItemClickListener onRecyclerItemClickListener;
    Context mContext;


    public void setSettlementList(List<Settlement> settlementList) {
        this.settlementList = settlementList;
    }

    public SettlementListRecyclerViewAdapter(List<Settlement> settlementList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.settlementList = settlementList;
        this.onRecyclerItemClickListener=onRecyclerItemClickListener;

    }


    @Override
    public SettlementListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_activity_list, parent, false);

        return new SettlementListRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SettlementListRecyclerViewAdapter.ViewHolder holder, int position) {
        Settlement activity = settlementList.get(position);
        holder.bind(activity,onRecyclerItemClickListener,position);
    }

    @Override
    public int getItemCount() {
        return settlementList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate,txtItem,txtQuantity,txtDueAmount;
        ViewHolder(View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.date);
            txtItem = itemView.findViewById(R.id.item);
            txtQuantity = itemView.findViewById(R.id.quantity);
            txtDueAmount = itemView.findViewById(R.id.dueAmount);
        }

        public void bind(final Settlement settlement, final OnRecyclerItemClickListener listener, final int position) {
            //TODO format date and handle null pointers
            if(!TextUtils.isEmpty(settlement.getTimeCreated()))
                txtDate.setText(AppUtils.getReadableDate(settlement.getTimeCreated()));
            txtDueAmount.setText("RS "+settlement.getAmount());
            txtDueAmount.setTextColor(Color.parseColor("#009688"));
            txtItem.setText("RS "+settlement.getAmountBefore());
            txtQuantity.setText("RS "+settlement.getAmountAfter());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(v,position);
                }
            });
        }
    }
}
