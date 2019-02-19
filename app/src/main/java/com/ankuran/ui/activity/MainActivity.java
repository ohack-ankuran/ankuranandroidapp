package com.ankuran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.ankuran.AppMain;
import com.ankuran.ui.fragment.DatePickerFragment;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paypal.ankuran.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener,DatePickerFragment.DatePickerDialogListener {

    TextView mTVWorkerProfile;
    TextView mTVCalculateWage;
    TextView mTVInventory;
    TextView mTVSocialImpact;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initToolbar();
        initUI();
        fetchDummyTODODetails("1");
    }


    private void initToolbar() {
       //TODO add toolbar enabling code

    }


    private void initUI() {
        mTVWorkerProfile = findViewById(R.id.tvWorkerProfile);
        mTVCalculateWage = findViewById(R.id.tvCalculateWage);
        mTVInventory = findViewById(R.id.tvInventory);
        mTVSocialImpact = findViewById(R.id.tvSocialReport);

        mTVWorkerProfile.setOnClickListener(this);
        mTVCalculateWage.setOnClickListener(this);
        mTVInventory.setOnClickListener(this);
        mTVSocialImpact.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvWorkerProfile:
                Intent workerProfileIntent = new Intent(MainActivity.this,WorkersProfileListActivity.class);
                startActivity(workerProfileIntent);
                break;

            case R.id.tvCalculateWage:
                Intent calculateIntent = new Intent(MainActivity.this,CalculateWageActivity.class);
                startActivity(calculateIntent);
                break;


            case R.id.tvInventory:
                Intent inventoryIntent = new Intent(MainActivity.this,InventoryDetailsActivity.class);
                startActivity(inventoryIntent);
                break;


            case R.id.tvSocialReport:
                showDatePicker();
//                Intent socialReportIntent = new Intent(MainActivity.this,SocialReportActivity.class);
//                startActivity(socialReportIntent);
                break;
        }

    }




    private void fetchDummyTODODetails(String todoId) {
        Log.d(TAG, "fetchIssueDetails");

        AppMain.getDefaultNetWorkClient().todos(todoId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if(response.code() == HTTP_CODE_SUCCESS){
//                    setIncidentValues(response.body());
//                }
                LogUtils.debug("OnResponse",new Gson().toJson(response));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("onFailure",new Gson().toJson(call));
            }
        });

    }

    @Override
    public void onDateSelected(DatePicker view,int day, int month, int year) {
        Toast.makeText(this, "selected date is " + year +
                " / " + month +
                " / " + day, Toast.LENGTH_SHORT).show();
    }
}
