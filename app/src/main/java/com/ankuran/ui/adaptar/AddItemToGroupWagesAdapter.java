package com.ankuran.ui.adaptar;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ankuran.R;
import com.ankuran.model.GroupWageEmployee;

import java.util.List;


public class AddItemToGroupWagesAdapter extends RecyclerView.Adapter<AddItemToGroupWagesAdapter.ViewHolder> {

    private List<GroupWageEmployee> mSelectedEmployees;
    private GroupWageAdapterListener mListener;
    private Double totalWages;
    private Handler mHandler = new Handler();

    public AddItemToGroupWagesAdapter(List<GroupWageEmployee> mSelectedEmployees, Double totalWages) {
        this.mSelectedEmployees = mSelectedEmployees;
        this.totalWages = totalWages;
    }

    public void updateTotalWages(Double totWages) {
        totalWages = totWages;
        Double iwd = totalWages / getItemCount();
        for (GroupWageEmployee gwe : mSelectedEmployees) {
            gwe.setWage(iwd);
        }
        notifyDataSetChanged();
    }

    public interface GroupWageAdapterListener {
        public void listen(Double wage);
    }

    public void setGroupWageAdapterListner(GroupWageAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_group_wages, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position, mListener);

    }

    @Override
    public int getItemCount() {
        return mSelectedEmployees != null ? mSelectedEmployees.size() : 0;
    }

    public Double totalGroupWages() {
        Double totalWage = 0.0;
        for (GroupWageEmployee gwe : mSelectedEmployees) {
            totalWage += gwe.getWage();
        }
        return totalWage;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewName;
        TextView mTextViewMobile;
        EditText mEditTextWage;

        ViewHolder(View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.employeeName);
            mTextViewMobile = itemView.findViewById(R.id.mobile);
            mEditTextWage = itemView.findViewById(R.id.workerWageEditText);
        }

        public void bind(final int position, final GroupWageAdapterListener listener) {
            final GroupWageEmployee gwe = mSelectedEmployees.get(position);
            mTextViewName.setText(gwe.getEmployee().getFullName());
            mTextViewMobile.setText(gwe.getEmployee().getMobile());
            mEditTextWage.setText(gwe.getWage().toString());
            mEditTextWage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        String text = v.getText().toString();
                        if (mEditTextWage.isFocused() && text.length() > 0) {
                            gwe.setWage(Double.parseDouble(text));
                            notifyDataSetChanged();
                            listener.listen(totalGroupWages());
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}