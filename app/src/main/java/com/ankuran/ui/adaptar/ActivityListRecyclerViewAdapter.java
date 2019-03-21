package com.ankuran.ui.adaptar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ankuran.model.Activity;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.R;

import java.util.List;

public class ActivityListRecyclerViewAdapter extends RecyclerView.Adapter<ActivityListRecyclerViewAdapter.ViewHolder> {

    private List<Activity> activityList;
    OnRecyclerItemClickListener onRecyclerItemClickListener;



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
            txtDate.setText(activity.getTimeCreated());
            if(activity.getDueDetails()!=null) {
                if(activity.getDueDetails().getItem()!=null)
                    txtItem.setText(activity.getDueDetails().getItem().getName());
                txtQuantity.setText(activity.getDueDetails().getQuantity().toString());
                txtDueAmount.setText("RS "+activity.getDueDetails().getDuePerItem().toString());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(v,position);
                }
            });
        }
    }
}
