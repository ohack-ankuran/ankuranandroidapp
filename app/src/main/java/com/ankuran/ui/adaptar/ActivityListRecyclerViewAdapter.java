package com.ankuran.ui.adaptar;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ankuran.model.Activity;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.R;
import com.ankuran.util.AppUtils;

import org.w3c.dom.Text;

import java.util.List;

public class ActivityListRecyclerViewAdapter extends RecyclerView.Adapter<ActivityListRecyclerViewAdapter.ViewHolder> {

    private List<Activity> activityList;
    OnRecyclerItemClickListener onRecyclerItemClickListener;
    Context mContext;



    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public ActivityListRecyclerViewAdapter(List<Activity> activityList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.activityList = activityList;
        this.onRecyclerItemClickListener=onRecyclerItemClickListener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_activity_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
                txtItem.setText("PAYOUT");
            }
            if(activity.getDueDetails()!=null) {
                if(activity.getDueDetails().getItem()!=null)
                    txtItem.setText(activity.getDueDetails().getItem().getName());
                else{
                    txtItem.setVisibility(View.INVISIBLE);
                }
                txtQuantity.setText(activity.getDueDetails().getQuantity().toString());
                txtDueAmount.setText("RS "+activity.getDueDetails().getAmount().toString());
                txtDueAmount.setTextColor(Color.parseColor("#c64319"));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(v,position);
                }
            });
        }
    }
}
