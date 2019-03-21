package com.ankuran.ui.adaptar;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.ankuran.R;
import com.ankuran.model.Employee;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class WorkerListRecyclerViewAdapter extends RecyclerView.Adapter<WorkerListRecyclerViewAdapter.ViewHolder> implements SectionIndexer {

    private List<Employee> employeeList;
    OnRecyclerItemClickListener onRecyclerItemClickListener;
    private ArrayList<Integer> mSectionPositions;


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

    public WorkerListRecyclerViewAdapter(List<Employee> employeeList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.employeeList = employeeList;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_worker_list, parent, false);
        itemView.findViewById(R.id.checkBox).setVisibility(View.GONE);
        itemView.findViewById(R.id.icon_caret).setVisibility(View.VISIBLE);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.bind(employee, onRecyclerItemClickListener, position);

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        //TODo add imageview for profile screen
        TextView txtName;
        TextView txtMobile;
        TextView txtAmount;

        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.name);
            txtMobile = itemView.findViewById(R.id.mobile);
            txtAmount = itemView.findViewById(R.id.dueAmount);
        }

        public void bind(final Employee employee, final OnRecyclerItemClickListener listener, final int position) {
            if (!TextUtils.isEmpty(employee.getFullName())) {
                txtName.setText(employee.getFullName());
            } else {
                txtName.setText("");
            }
            if (!TextUtils.isEmpty(employee.getMobile())) {
                txtMobile.setText(employee.getMobile());
            } else {
                txtMobile.setText("");
            }
            if (employee.getOutstandingDue() != 0) {
                int amount = (int) employee.getOutstandingDue();
                txtAmount.setText("RS " + amount);
            } else {
                txtAmount.setText("");
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, position);
                }
            });
        }
    }
}