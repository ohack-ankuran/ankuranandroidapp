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
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.util.AppUtils;

import java.util.List;

public class PayoutActivityListRecyclerViewAdapter extends RecyclerView.Adapter<PayoutActivityListRecyclerViewAdapter.ViewHolder> {

    private List<Activity> activityList;
    OnRecyclerItemClickListener onRecyclerItemClickListener;
    Context mContext;



    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public PayoutActivityListRecyclerViewAdapter(List<Activity> activityList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.activityList = activityList;
        this.onRecyclerItemClickListener=onRecyclerItemClickListener;

    }


    @Override
    public PayoutActivityListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_activity_list, parent, false);

        return new PayoutActivityListRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PayoutActivityListRecyclerViewAdapter.ViewHolder holder, int position) {
        Activity activity = activityList.get(position);
        holder.bind(activity,onRecyclerItemClickListener,position);
    }

    @Override
    public int getItemCount() {
        return activityList.size();
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

        public void bind(final Activity activity, final OnRecyclerItemClickListener listener, final int position) {
            //TODO format date and handle null pointers
            if(!TextUtils.isEmpty(activity.getTimeCreated()))
                txtDate.setText(AppUtils.getReadableDate(activity.getTimeCreated()));

            if(activity.getPaymentDetails()!=null){
                txtDueAmount.setText("RS "+activity.getPaymentDetails().getAmount());
                txtDueAmount.setTextColor(Color.parseColor("#009688"));
                txtQuantity.setVisibility(View.INVISIBLE);
                if(activity.getPaymentDetails().getRecipient()!=null)
                    txtItem.setText(activity.getPaymentDetails().getRecipient().getFullName());
                else
                    txtItem.setVisibility(View.INVISIBLE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(v,position);
                }
            });
        }
    }
}
