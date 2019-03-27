package com.ankuran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankuran.AppConstant;
import com.ankuran.AppMain;
import com.ankuran.model.Activity;
import com.ankuran.model.Employee;
import com.ankuran.model.dao.ActivityList;
import com.ankuran.ui.adaptar.ActivityListRecyclerViewAdapter;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.ui.fragment.DatePickerFragment;
import com.ankuran.util.AppUtils;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.ankuran.R;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerActivityList extends BaseActivity implements OnRecyclerItemClickListener,View.OnClickListener,DatePickerFragment.DatePickerDialogListener {


    RecyclerView mRecyclerView;
    ActivityListRecyclerViewAdapter mAdapter;
    private List<Activity> activityList;

    TextView addWage,addPayment,mTVStartDate,mTVEndDate;

    TextView txtName,txtMobile,txtDate,txtDueAmount;

    LinearLayout mLLStartDate,mLLEndDate;

    Boolean isStartDate=true;
    Intent intent;
    Employee currentEmployee;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_worker_list;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();
     //  addDummyDataTo();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAllActivities();
    }

    private void getAllActivities() {
        Log.d(TAG, "getAllEmployee");

        AppMain.getDefaultNetWorkClient().getAllActivities(currentEmployee.getId(),"2019-02-21","2019-03-28","").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == HttpsURLConnection.HTTP_OK){
                    //TODO put validation check
                    Log.d("getAllActivities",new Gson().toJson(response.body()));
//                    Type listType = new TypeToken<List<Activity>>() {}.getType();
//                    List<Activity>list = new Gson().fromJson(response.body(),listType);


                    ActivityList list = new Gson().fromJson(response.body(),ActivityList.class);
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



    private void initUI() {
        addWage =findViewById(R.id.tvAddWage);
        addWage.setOnClickListener(this);
        addPayment =findViewById(R.id.tvAddPayout);
        addPayment.setOnClickListener(this);

        mLLStartDate =findViewById(R.id.llStartDateContainer);
        mLLStartDate.setOnClickListener(this);
        mLLEndDate =findViewById(R.id.llEndDateContainer);
        mLLEndDate.setOnClickListener(this);

        mTVStartDate =findViewById(R.id.tvStartDate);
        mTVEndDate =findViewById(R.id.tvEndDate);


        txtName =findViewById(R.id.txtName);
        txtMobile =findViewById(R.id.txtMobile);
        txtDate =findViewById(R.id.txtDate);
        txtDueAmount =findViewById(R.id.txtDueAmount);



        activityList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.activityRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapter(null);

        intent = getIntent();
        if (intent != null) {
            currentEmployee = (Employee) intent.getSerializableExtra(AppConstant.KEY_CURRENT_EMPLOYEE);
            setEmployeeProfile(currentEmployee);
        }


    }

    private void setEmployeeProfile(Employee employee) {
        if(employee!=null){
            if(!TextUtils.isEmpty(employee.getFullName())){
                txtName.setText(employee.getFullName());
            }else{
                txtName.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(employee.getFullName())){
                txtName.setText(employee.getFullName());
            }else{
                txtName.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(employee.getMobile())){
                txtMobile.setText(employee.getMobile());
            }else{
                txtMobile.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(employee.getTimeOfJoining()))
                txtDate.setText("Date Added "+AppUtils.getReadableDate(employee.getTimeOfJoining()));
            else
                txtDate.setVisibility(View.GONE);

           txtDueAmount.setText("Due amount "+getString(R.string.Rs)+" "+employee.getOutstandingDue());
        }else{
            finish();
        }
    }

    private void addDummyDataTo() {
        String activitysList = "{\"activities\":[{\"id\":\"DQJAZ629PLDF4M\",\"timeCreated\":\"2019-02-7T00:01:40.030Z\",\"type\":\"DUE\",\"status\":\"CORRECT\",\"changeHistory\":false,\"dueDetails\":{\"id\":\"NBAWT746MO91CA\",\"distributionType\":\"INDIVIDUAL\",\"item\":{\"id\":102,\"name\":\"Green Apron\",\"type\":\"Apron\",\"picture\":\"https://linktoimage.png\",\"labels\":[\"green\",\"apron\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876,\"fullName\":\"Sunita Singh\"},\"active\":true},\"quantity\":5,\"duePerItem\":40,\"amount\":200},\"paymentDetails\":null},{\"id\":\"PQLS947JNAD23J\",\"timeCreated\":\"2019-02-05T09:12:01.030Z\",\"type\":\"DUE\",\"status\":\"CORRECT\",\"changeHistory\":true,\"dueDetails\":{\"id\":\"PKJLIO30480HOC\",\"distributionType\":\"INDIVIDUAL\",\"item\":{\"id\":101,\"name\":\"Pink Butterfly Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage.png\",\"labels\":[\"pink\",\"butterfly\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876,\"fullName\":\"Sunita Singh\"},\"active\":true},\"quantity\":3,\"duePerItem\":40,\"amount\":120},\"paymentDetails\":null},{\"id\":\"DLJUIE67HJK768J\",\"timeCreated\":\"2019-02-01T00:15:21.030Z\",\"type\":\"PAYMENT\",\"status\":\"CORRECT\",\"changeHistory\":false,\"dueDetails\":null,\"paymentDetails\":{\"id\":\"OWMXBSK8763YWT\",\"note\":\"advance payment\",\"recipient\":{\"id\":234567898765,\"fullName\":\"Sunita Singh\",\"active\":true,\"centre\":1},\"amount\":1000,\"currentDue\":1500,\"remainingDue\":500}},{\"id\":\"LWDUIE67HJK768J\",\"timeCreated\":\"2019-01-31T00:15:21.030Z\",\"type\":\"DUE\",\"status\":\"INCORRECT\",\"changeHistory\":true,\"dueDetails\":{\"id\":\"PKJLIO30480HOC\",\"distributionType\":\"INDIVIDUAL\",\"item\":{\"id\":101,\"name\":\"Red Flower Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage.png\",\"labels\":[\"red\",\"flower\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876,\"fullName\":\"Sunita Singh\"},\"active\":true},\"quantity\":2,\"duePerItem\":50,\"amount\":100},\"paymentDetails\":null},{\"id\":\"LWDUIE67HJK768J\",\"timeCreated\":\"2019-01-15T00:21:45.387Z\",\"type\":\"DUE\",\"status\":\"CORRECT\",\"changeHistory\":false,\"dueDetails\":{\"id\":\"MWQLIO936807SV\",\"distributionType\":\"GROUP\",\"item\":{\"id\":136,\"name\":\"Coach School Bag\",\"type\":\"Bag\",\"picture\":\"https://linktoimage.png\",\"labels\":[\"red\",\"flower\",\"bag\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876,\"fullName\":\"Sunita Singh\"},\"active\":true},\"quantity\":15,\"duePerItem\":120,\"amount\":600},\"paymentDetails\":null},{\"id\":\"QOSID29384NKJD\",\"timeCreated\":\"2019-01-11T00:01:40.030Z\",\"type\":\"DUE\",\"status\":\"DELETED\",\"changeHistory\":false,\"dueDetails\":{\"id\":\"PQMZYS7381KSWGF\",\"distributionType\":\"INDIVIDUAL\",\"item\":{\"id\":102,\"name\":\"Green Apron\",\"type\":\"Apron\",\"picture\":\"https://linktoimage.png\",\"labels\":[\"green\",\"apron\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876,\"fullName\":\"Sunita Singh\"},\"active\":true},\"quantity\":2,\"duePerItem\":75,\"amount\":150},\"paymentDetails\":null}]}";
        ActivityList list = new Gson().fromJson(activitysList,ActivityList.class);
        LogUtils.debug("Shikha",list.getActivities().size()+"");
//        activityList.addAll(list.getActivities());
//        mAdapter.notifyDataSetChanged();
//        setAdapter(list.getActivities());


    }

    @Override
    public void onItemClick(View view, int position) {
//        LogUtils.debug("Shikha","onItemClick"+position);
    }

    @Override
    public void onItemClick(View view, int position, Object object) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAddWage:
                Intent wageIntent = new Intent(WorkerActivityList.this,AddPaymentWageActivity.class);
                wageIntent.putExtra(AppConstant.KEY_ACTIVITY_TYPE, AppConstant.ACTIVITY_TYPE.DUE);
                wageIntent.putExtra(AppConstant.KEY_CURRENT_EMPLOYEE, currentEmployee);
                startActivity(wageIntent);
                break;

            case R.id.tvAddPayout:
                Intent paymentIntent = new Intent(WorkerActivityList.this,AddPaymentWageActivity.class);
                paymentIntent.putExtra(AppConstant.KEY_ACTIVITY_TYPE, AppConstant.ACTIVITY_TYPE.PAYOUT);
                paymentIntent.putExtra(AppConstant.KEY_CURRENT_EMPLOYEE, currentEmployee);
                startActivity(paymentIntent);
                break;


            case R.id.llStartDateContainer:
                showDatePicker(this);
                //TODO end date validation
                break;


            case R.id.llEndDateContainer:
                showDatePicker(this);
                break;

        }
    }

    public void setAdapter(ActivityList list) {
        if (mAdapter == null) {
            mAdapter = new ActivityListRecyclerViewAdapter(activityList,this);
            mRecyclerView.setAdapter(mAdapter);
        } else if (list!=null && AppUtils.isValidList(list.getActivities())) {
            mAdapter.setActivityList(list.getActivities());
            mAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onDateSelected(DatePicker view, int day, int month, int year) {
        String date=year + " / " + month + " / " + day;
        //Todo set date
        if(isStartDate){
            mTVStartDate.setText(date);
        }else{
            mTVEndDate.setText(date);
        }

        isStartDate=!isStartDate;
    }
}
