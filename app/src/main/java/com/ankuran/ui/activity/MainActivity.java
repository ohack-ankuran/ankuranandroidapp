package com.ankuran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ankuran.ui.fragment.DatePickerFragment;
import com.ankuran.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends BaseActivity implements View.OnClickListener,DatePickerFragment.DatePickerDialogListener {

    LinearLayout mTVWorkerProfile;
    LinearLayout mTVCalculateWage;
    LinearLayout mTVInventory;
    LinearLayout mTVSocialImpact;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initToolbar();
        initUI();
//        dummyGetCall("1");
//        dummyPostCall();



    }

    private void initToolbar() {
       //TODO add toolbar enabling code

    }


    private void initUI() {
        mTVWorkerProfile = findViewById(R.id.llWorkerProfile);
        mTVCalculateWage = findViewById(R.id.llCalculateWage);
        mTVInventory = findViewById(R.id.llInventory);
        mTVSocialImpact = findViewById(R.id.llSocialReport);

        mTVWorkerProfile.setOnClickListener(this);
        mTVCalculateWage.setOnClickListener(this);
        mTVInventory.setOnClickListener(this);
        mTVSocialImpact.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llWorkerProfile:
                Intent workerProfileIntent = new Intent(MainActivity.this,WorkersProfileListActivity.class);
                startActivity(workerProfileIntent);
                break;

            case R.id.llCalculateWage:
                Intent calculateIntent = new Intent(MainActivity.this,PayoutReportActivity.class);
                startActivity(calculateIntent);
                break;


            case R.id.llInventory:
                Intent inventoryIntent = new Intent(MainActivity.this,ItemGridViewActivity.class);
                startActivity(inventoryIntent);
                break;


            case R.id.llSocialReport:
//                showDatePicker();
                Intent socialReportIntent = new Intent(MainActivity.this,InventoryListActivity.class);
                startActivity(socialReportIntent);
                break;
        }

    }


//    private void dummyGetCall(String todoId) {
//        Log.d(TAG, "fetchIssueDetails");
//
//        AppMain.getDefaultNetWorkClient().todos(todoId).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
////                if(response.code() == HTTP_CODE_SUCCESS){
////                    setIncidentValues(response.body());
////                }
//                LogUtils.debug("Network call OnResponse get call",new Gson().toJson(response));
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                //TODO: Show retrofit error dialog
//                LogUtils.debug("Network call onFailure get callv",new Gson().toJson(call));
//            }
//        });
//
//    }


//    private void dummyPostCall(){
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("title", "foo");
//        jsonObject.addProperty("body", "bar");
//        jsonObject.addProperty("userId", 1);
//
//        AppMain.getDefaultNetWorkClient().posts(jsonObject).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
////                if(response.code() == HTTP_CODE_SUCCESS){
////                    setIncidentValues(response.body());
////                }
//                LogUtils.debug("Network call OnResponse post call",new Gson().toJson(response));
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                //TODO: Show retrofit error dialog
//                LogUtils.debug("Network call onFailure post call",new Gson().toJson(call));
//            }
//        });
//    }

    @Override
    public void onDateSelected(DatePicker view,int day, int month, int year) {
        Toast.makeText(this, "selected date is " + year +
                " / " + month +
                " / " + day, Toast.LENGTH_SHORT).show();
    }



}
