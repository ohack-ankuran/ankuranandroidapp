package com.ankuran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ankuran.AppMain;
import com.ankuran.model.Employee;
import com.ankuran.model.dao.EmployeeList;
import com.ankuran.ui.adaptar.WorkerListRecyclerViewAdapter;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.util.AppUtils;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ankuran.R;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkersProfileListActivity extends BaseActivity implements OnRecyclerItemClickListener,View.OnClickListener {

    IndexFastScrollRecyclerView mRecyclerView;
    WorkerListRecyclerViewAdapter mAdapter;
    Button mAddNewWorker,mGroupWages;
    List<Employee> employeeList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_workers_profile_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllEmployee();
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();
    }


    private void initUI() {
        mAddNewWorker=findViewById(R.id.btnAddNewWorker);
        mGroupWages=findViewById(R.id.btnGroupWages);
        mAddNewWorker.setOnClickListener(this);
        mGroupWages.setOnClickListener(this);
        employeeList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.fast_scroller_recycler);
        setAdapter(null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setIndexbarHighLateTextColor("#020202");
        mRecyclerView.setIndexBarHighLateTextVisibility(true);
        mRecyclerView.setIndexBarColor("#FFFFFF");
        mRecyclerView.setIndexBarTextColor("#888383");
        mRecyclerView.setPreviewVisibility(true);
//        mRecyclerView.setIndexbarWidth(50);
//        mRecyclerView.setPreviewPadding(10);
        mRecyclerView.setIndexbarMargin(10);
        mRecyclerView.setIndexTextSize(15);
    }



    private void addDummyDataToPref() {
        String workerList = "{\"employees\":[{\"id\":234567898766,\"fullName\":\"Anita Jain\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"bbbbbbbbbbbjnjjkk\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"bbbbbbbbbbbbblkjk\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"bbbbbbbbbbdjfj\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"ccccbbbbbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"ccccbbljlbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"dddccccbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"eeeccccbbbbbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"ffffccccbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"ggsrgtfrccccbbbbbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"hjhjccccbbbbbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"iohjccccbbbbbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898768,\"fullName\":\"Kanta Singh\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-02-03T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Singh\",\"bslEmployeeId\":\"BSL-236799837\"},\"centre\":1,\"outstandingDue\":-500},{\"id\":234567898769,\"fullName\":\"Ramakanta Sharma\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-02-03T00:15:21.030Z\",\"active\":true,\"centre\":1,\"outstandingDue\":0},{\"id\":234567898765,\"fullName\":\"Sunita Singh\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-01T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Shyam Singh\",\"bslEmployeeId\":\"BSL-236799834\"},\"centre\":1,\"outstandingDue\":2356},{\"id\":234567898767,\"fullName\":\"Sushila Verma\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-07T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Verma\",\"bslEmployeeId\":\"BSL-236799836\"},\"centre\":1,\"outstandingDue\":1500},{\"id\":234567898770,\"fullName\":\"Stuti Mishra\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-02-03T00:15:21.030Z\",\"active\":false,\"husband\":{\"fullName\":\"Mr Singh\"},\"centre\":1,\"outstandingDue\":300}]}";
        EmployeeList list = new Gson().fromJson(workerList,EmployeeList.class);
        employeeList.addAll(list.getEmployees());
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(View view, int position) {
        //TODO pass worker data
        Intent workerProfileIntent = new Intent(WorkersProfileListActivity.this,WorkerActivityList.class);
        startActivity(workerProfileIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddNewWorker:
                Intent newWorkerIntent = new Intent(WorkersProfileListActivity.this,AddNewWorker.class);
                startActivity(newWorkerIntent);
                break;
            case R.id.btnGroupWages:
                break;
        }

    }

    private void getAllEmployee() {
        Log.d(TAG, "getAllEmployee");

        AppMain.getDefaultNetWorkClient().allEmployee().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == HttpsURLConnection.HTTP_OK){
                    //TODO put validation check
                    Log.d("Shikha",new Gson().toJson(response.body()));
                    EmployeeList list = new Gson().fromJson(response.body(),EmployeeList.class);
//                    employeeList.addAll(list.getEmployees());
//                    mAdapter.notifyDataSetChanged();
                    setAdapter(list);
                }else{
                    Log.d("Shikha","not 200"+new Gson().toJson(response));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure get callv",new Gson().toJson(call));
            }
        });

    }

    public void setAdapter(EmployeeList list) {
            if (mAdapter == null) {
                mAdapter = new WorkerListRecyclerViewAdapter(employeeList,this);
                mRecyclerView.setAdapter(mAdapter);
            } else if (list!=null && AppUtils.isValidList(list.getEmployees())) {
                    mAdapter.setEmployeeList(list.getEmployees());
                    mAdapter.notifyDataSetChanged();

            }
    }
}
