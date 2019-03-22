package com.ankuran.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.DueDetail;
import com.ankuran.model.GroupWageEmployee;
import com.ankuran.model.Item;
import com.ankuran.model.dao.DistributionEmployee;
import com.ankuran.model.dao.GroupWage;
import com.ankuran.ui.activity.WorkersProfileListActivity;
import com.ankuran.ui.adaptar.AddItemToGroupWagesAdapter;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemToGroupWagesFragment extends Fragment implements AddItemToGroupWagesAdapter.GroupWageAdapterListener, DialogInterface.OnClickListener {

    private RecyclerView mRecyclerView;
    private AddItemToGroupWagesAdapter mAdapter;
    private EditText mEditTextWage;
    private EditText mEditTextItem;
    private Button mButton;
    private List<GroupWageEmployee> mGroupWageEmployees;

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

        mGroupWageEmployees = getArguments().getParcelableArrayList("selected_employees");
        String totalWageAmount = mEditTextWage.getText().toString();
        Double totalAmount = totalWageAmount.isEmpty() ? 0 : Double.valueOf(totalWageAmount);
        mAdapter = new AddItemToGroupWagesAdapter(mGroupWageEmployees, totalAmount);
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
                saveGroupWages();
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

    public void showInfoDialog(String title, String msg) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent newWorkerIntent = new Intent(getContext(), WorkersProfileListActivity.class);
                        startActivity(newWorkerIntent);
                    }
                }).show();
    }

    private GroupWage buildGroupWages() {
        GroupWage gw = new GroupWage();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        gw.setTimeCreated(dateFormat.format(calendar.getTime()));
        gw.setType("DUE");
        DueDetail dueDetail = new DueDetail();
        dueDetail.setDistributionType("GROUP");
        Item item = new Item();
        item.setName(mEditTextItem.getText().toString());
        dueDetail.setItem(item);
        dueDetail.setQuantity(1);
        dueDetail.setDuePerItem(Double.valueOf(mEditTextWage.getText().toString()));
        dueDetail.setAmount(Double.valueOf(mEditTextWage.getText().toString()));
        List<DistributionEmployee> distributionEmployeeList = new ArrayList<>();
        for (GroupWageEmployee gwe : mGroupWageEmployees) {
            DistributionEmployee distributionEmployee = new DistributionEmployee();
            distributionEmployee.setEmployee(gwe.getMEmployee());
            distributionEmployee.setAmount(gwe.getMWage());
            distributionEmployeeList.add(distributionEmployee);
        }
        dueDetail.setDistribution(distributionEmployeeList);
        gw.setDueDetails(dueDetail);
        return gw;
    }

    private void saveGroupWages() {
        GroupWage groupWage = buildGroupWages();
        AppMain.getDefaultNetWorkClient().saveGroupWages(groupWage).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                LogUtils.debug("Network call OnResponse post call", new Gson().toJson(response));
                processServerResponse(response, "create");

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure post call", new Gson().toJson(call));
            }
        });
    }

    private void processServerResponse(Response<JsonObject> response, String operation) {
        if (response.code() == HttpsURLConnection.HTTP_OK || response.code() == HttpsURLConnection.HTTP_CREATED || response.code() == HttpsURLConnection.HTTP_ACCEPTED) {
            Log.d("Shikha", new Gson().toJson(response.body()));
            showInfoDialog("", "Group Wages added successfully!!", this);

        } else {
            Log.d("Shikha", "not 200" + new Gson().toJson(response));
            showInfoDialog("", "Error occurred while " + operation + "ing.Please try again later!!", this);
        }
    }

    public void showInfoDialog(String title, String msg, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Ok", listener).show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        Intent newWorkerIntent = new Intent(getContext(), WorkersProfileListActivity.class);
        startActivity(newWorkerIntent);
    }
}
