package com.ankuran.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ankuran.R;
import com.ankuran.model.GroupWageEmployee;
import com.ankuran.model.Item;
import com.ankuran.model.dao.ItemList;
import com.ankuran.ui.activity.AddNewWorker;
import com.ankuran.ui.activity.GroupWagesActivity;
import com.ankuran.ui.activity.WorkersProfileListActivity;
import com.ankuran.ui.adaptar.AddItemToGroupWagesAdapter;
import com.ankuran.ui.adaptar.ItemRecyclerViewAdapter;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddItemToGroupWagesFragment extends Fragment implements AddItemToGroupWagesAdapter.GroupWageAdapterListener {

    private RecyclerView mRecyclerView;
    private AddItemToGroupWagesAdapter mAdapter;
    private EditText mEditTextWage;
    private EditText mEditTextItem;
    private Button mButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item_to_group_wages, container, false);

        mEditTextItem = view.findViewById(R.id.itemEditText);
        mEditTextItem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    if (text.length() > 0) {
                        updateItem(text); // distribute to all employees
                    }
                    return true;
                }
                return false;
            }
        });

        mEditTextWage = view.findViewById(R.id.totalWages);
        mRecyclerView = view.findViewById(R.id.employeeRecyclerView);

        List<GroupWageEmployee> selectedEmployees = getArguments().getParcelableArrayList("selected_employees");
        String totalWageAmount = mEditTextWage.getText().toString();
        Double totalAmount = totalWageAmount.isEmpty() ? 0 : Double.valueOf(totalWageAmount);
        mAdapter = new AddItemToGroupWagesAdapter(selectedEmployees, totalAmount);
        mAdapter.setGroupWageAdapterListner(AddItemToGroupWagesFragment.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mEditTextWage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    if (text.length() > 0) {
                        mAdapter.updateTotalWages(Double.parseDouble(text)); // distribute to all employees
                    }
                    return true;
                }
                return false;
            }
        });

        mButton = view.findViewById(R.id.saveGroupWages);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(getContext(), mEditTextItem + " :: " + mEditTextWage, Toast.LENGTH_LONG).show();
                Intent newWorkerIntent = new Intent(getContext(),WorkersProfileListActivity.class);
                startActivity(newWorkerIntent);
            }
        });
        return view;
    }

    @Override
    public void listen(Double totalWage) {
        if (!mEditTextWage.isFocused()) {
            mEditTextWage.setText(totalWage.toString()); // update total wage
        }
    }

    private void updateItem(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }
}
