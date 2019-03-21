package com.ankuran.ui.adaptar;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.ankuran.R;
import com.ankuran.model.Employee;
import com.ankuran.model.GroupWageEmployee;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class GroupWagesRecyclerViewAdapter extends RecyclerView.Adapter<GroupWagesRecyclerViewAdapter.ViewHolder> implements SectionIndexer {

    private List<GroupWageEmployee> mEmployees;
    OnRecyclerItemClickListener onRecyclerItemClickListener;
    private ArrayList<Integer> mSectionPositions;

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = mEmployees.size(); i < size; i++) {
            String section = String.valueOf(mEmployees.get(i).getEmployee().getFullName().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    public GroupWagesRecyclerViewAdapter(List<GroupWageEmployee> mEmployees, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mEmployees = mEmployees;
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
        itemView.findViewById(R.id.checkBox).setVisibility(View.VISIBLE);
        itemView.findViewById(R.id.icon_caret).setVisibility(View.GONE);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;
        GroupWageEmployee gwe = mEmployees.get(pos);
        holder.mTextView.setText(gwe.getEmployee().getFullName());
        holder.mCheckBox.setChecked(gwe.getSelected());
        holder.mCheckBox.setTag(gwe);
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                GroupWageEmployee gwe = (GroupWageEmployee) cb.getTag();
                gwe.setSelected(cb.isChecked());
                mEmployees.get(pos).setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }

    public List<GroupWageEmployee> getSelectedEmployees() {
        List<GroupWageEmployee> selectedEmployees = new ArrayList<>();
        for (GroupWageEmployee e : mEmployees) {
            if (e.getSelected()) {
                selectedEmployees.add(e);
            }
        }
        return selectedEmployees;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public CheckBox mCheckBox;
        public CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.name);
            mCheckBox = itemView.findViewById(R.id.checkBox);
            mCardView = itemView.findViewById(R.id.item_card_view);
        }
    }
}