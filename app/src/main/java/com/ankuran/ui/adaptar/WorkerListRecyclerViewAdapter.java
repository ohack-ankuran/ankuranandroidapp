package com.ankuran.ui.adaptar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.ankuran.model.Employee;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.R;

import java.util.ArrayList;
import java.util.List;


public class WorkerListRecyclerViewAdapter extends RecyclerView.Adapter<WorkerListRecyclerViewAdapter.ViewHolder> implements SectionIndexer {

    private List<Employee> employeeList;
    OnRecyclerItemClickListener onRecyclerItemClickListener;
    private ArrayList<Integer> mSectionPositions;
    private Context mContext;


    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = employeeList.size(); i < size; i++) {
            String section = String.valueOf(employeeList.get(i).getFullName().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    public WorkerListRecyclerViewAdapter(List<Employee> employeeList, OnRecyclerItemClickListener onRecyclerItemClickListener,Context context) {
        this.mContext=context;
        this.employeeList = employeeList;
        this.onRecyclerItemClickListener=onRecyclerItemClickListener;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_worker_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.bind(employee,onRecyclerItemClickListener,position);

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //TODo add imageview for profile screen
        TextView txtName;
        TextView txtMobile;
        TextView txtAmount;
        ViewHolder(View itemView) {
            super(itemView);
            txtName =itemView.findViewById(R.id.name);
            txtMobile =itemView.findViewById(R.id.mobile);
            txtAmount =itemView.findViewById(R.id.dueAmount);
        }

        public void bind(final Employee employee, final OnRecyclerItemClickListener listener, final int position) {
            if(!TextUtils.isEmpty(employee.getFullName())){
                txtName.setText(employee.getFullName());
            }else{
                txtName.setText("");
            }
            if(!TextUtils.isEmpty(employee.getMobile())){
                txtMobile.setText(employee.getMobile());
            }else{
                txtMobile.setText("");
            }
            txtAmount.setText(mContext.getString(R.string.Rs)+employee.getOutstandingDue());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, position,employee);
                }
            });
        }
    }
}