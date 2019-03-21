package com.ankuran.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ankuran.AppConstant;
import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Employee;
import com.ankuran.model.EmployeeHusband;
import com.ankuran.util.AppUtils;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewWorker extends BaseActivity implements View.OnClickListener,DialogInterface.OnClickListener{

    EditText mETName;
    EditText mETHusbandName;
    EditText mETHusbandId;
    EditText mETPhone;
    Button mSave;
    Context mContext;
    Intent intent;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_new_worker;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();
    }

    private void initUI() {
        mContext=this;
        mETName=findViewById(R.id.etName);
        mETHusbandName=findViewById(R.id.etHusbandName);
        mETHusbandId=findViewById(R.id.etHusbandId);
        mETPhone=findViewById(R.id.etPhone);
        mSave=findViewById(R.id.btnSave);
        mSave.setOnClickListener(this);


        //TODO add capture image feature

        intent = getIntent();
        if (intent != null) {
            Employee employee = (Employee) intent.getSerializableExtra(AppConstant.KEY_CURRENT_EMPLOYEE);
            setUI(employee);
        }
    }

    private void setUI(Employee employee) {
        if(employee!=null){
            if(!TextUtils.isEmpty(employee.getFullName()))
                 mETName.setText(employee.getFullName());
            if(employee.getHusband()!=null && !TextUtils.isEmpty(employee.getHusband().getFullName()))
                mETHusbandName.setText(employee.getHusband().getFullName());
            if(employee.getHusband()!=null && !TextUtils.isEmpty(employee.getHusband().getBslEmployeeId()))
                 mETHusbandId.setText(employee.getHusband().getBslEmployeeId());
            if(!TextUtils.isEmpty(employee.getMobile()))
                mETPhone.setText(employee.getMobile());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                 performSave();
                break;
        }

    }

    private void performSave() {
        Employee employee = new Employee();
        String fullName = mETName.getText() != null ? mETName.getText().toString().trim() : "";
        if (TextUtils.isEmpty(fullName)) {
            showToast("Please enter your name");
            return;
        } else {
            employee.setFullName(fullName);
            String mobile = mETPhone.getText() != null ? mETPhone.getText().toString().trim() : "";
            if (!TextUtils.isEmpty(mobile) && mobile.length() == 10) {
                employee.setMobile(mobile);
            } else {
                showToast("Please enter valid mobile number");
            }
            EmployeeHusband husband =new EmployeeHusband();
            String husbandName = mETHusbandName.getText() != null ? mETHusbandName.getText().toString().trim() : "";
            if(!TextUtils.isEmpty(husbandName))
                husband.setFullName(husbandName);
            String husbandId= mETHusbandId.getText() != null ? mETHusbandId.getText().toString().trim() : "";
            if(!TextUtils.isEmpty(husbandId))
                husband.setBslEmployeeId(husbandId);

            employee.setHusband(husband);

            if (!AppUtils.isConnectedToInternet()) {
                showToast(getString(R.string.network_error));
                return;
            }
            performNetworkCall(employee);
        }
    }

    private void performNetworkCall(Employee employee) {
        if(employee.getId()!=0){
            updateEmployee(employee);
        }else {
            addEmployee(employee);
        }

    }

    private void addEmployee(Employee employee) {
        AppMain.getDefaultNetWorkClient().addEmployee(employee).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                LogUtils.debug("Network call OnResponse post call",new Gson().toJson(response));
                processServerResponse(response,"creat");

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure post call",new Gson().toJson(call));
            }
        });
    }

    private void updateEmployee(Employee employee) {
        AppMain.getDefaultNetWorkClient().updateEmployee(employee.getId(),employee).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                LogUtils.debug("Network call OnResponse post call",new Gson().toJson(response));
                processServerResponse(response,"updat");
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure post call",new Gson().toJson(call));
            }
        });
    }

    private void processServerResponse(Response<JsonObject> response, String operation) {
        if(response.code() == HttpsURLConnection.HTTP_OK ||response.code() == HttpsURLConnection.HTTP_CREATED||response.code() == HttpsURLConnection.HTTP_ACCEPTED){
            Log.d("Shikha",new Gson().toJson(response.body()));
            showInfoDialog("", "Information "+operation+"ed successfully!!",this);

        }else{
            Log.d("Shikha","not 200"+new Gson().toJson(response));
            showInfoDialog("","Error occurred while "+operation+"ing.Please try again later!!",this);
        }
    }

    private void clearViews() {
        mETName.setText("");
        mETHusbandName.setText("");
        mETHusbandId.setText("");
        mETPhone.setText("");
    }


    @Override
    public void onClick(DialogInterface dialog, int i) {
        dialog.dismiss();
        clearViews();
        finish();

    }
}
