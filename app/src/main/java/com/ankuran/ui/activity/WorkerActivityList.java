package com.ankuran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ankuran.AppConstant;
import com.ankuran.model.Activity;
import com.ankuran.model.dao.ActivityList;
import com.ankuran.ui.adaptar.ActivityListRecyclerViewAdapter;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.paypal.ankuran.R;

import java.util.ArrayList;
import java.util.List;

public class WorkerActivityList extends BaseActivity implements OnRecyclerItemClickListener,View.OnClickListener {


    RecyclerView mRecyclerView;
    ActivityListRecyclerViewAdapter mAdapter;
    private List<Activity> activityList;

    TextView addWage,addPayment;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_worker_list;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();
        addDummyDataTo();
    }

    private void initUI() {
        addWage =findViewById(R.id.tvAddWage);
        addWage.setOnClickListener(this);
        addPayment =findViewById(R.id.tvAddPayout);
        addPayment.setOnClickListener(this);
        activityList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.activityRecyclerView);
        mAdapter = new ActivityListRecyclerViewAdapter(activityList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

    }

    private void addDummyDataTo() {
        String activitysList = "{\"activities\":[{\"id\":\"DQJAZ629PLDF4M\",\"timeCreated\":\"2019-02-7T00:01:40.030Z\",\"type\":\"DUE\",\"status\":\"CORRECT\",\"changeHistory\":false,\"dueDetails\":{\"id\":\"NBAWT746MO91CA\",\"distributionType\":\"INDIVIDUAL\",\"item\":{\"id\":102,\"name\":\"Green Apron\",\"type\":\"Apron\",\"picture\":\"https://linktoimage.png\",\"labels\":[\"green\",\"apron\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876,\"fullName\":\"Sunita Singh\"},\"active\":true},\"quantity\":5,\"duePerItem\":40,\"amount\":200},\"paymentDetails\":null},{\"id\":\"PQLS947JNAD23J\",\"timeCreated\":\"2019-02-05T09:12:01.030Z\",\"type\":\"DUE\",\"status\":\"CORRECT\",\"changeHistory\":true,\"dueDetails\":{\"id\":\"PKJLIO30480HOC\",\"distributionType\":\"INDIVIDUAL\",\"item\":{\"id\":101,\"name\":\"Pink Butterfly Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage.png\",\"labels\":[\"pink\",\"butterfly\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876,\"fullName\":\"Sunita Singh\"},\"active\":true},\"quantity\":3,\"duePerItem\":40,\"amount\":120},\"paymentDetails\":null},{\"id\":\"DLJUIE67HJK768J\",\"timeCreated\":\"2019-02-01T00:15:21.030Z\",\"type\":\"PAYMENT\",\"status\":\"CORRECT\",\"changeHistory\":false,\"dueDetails\":null,\"paymentDetails\":{\"id\":\"OWMXBSK8763YWT\",\"note\":\"advance payment\",\"recipient\":{\"id\":234567898765,\"fullName\":\"Sunita Singh\",\"active\":true,\"centre\":1},\"amount\":1000,\"currentDue\":1500,\"remainingDue\":500}},{\"id\":\"LWDUIE67HJK768J\",\"timeCreated\":\"2019-01-31T00:15:21.030Z\",\"type\":\"DUE\",\"status\":\"INCORRECT\",\"changeHistory\":true,\"dueDetails\":{\"id\":\"PKJLIO30480HOC\",\"distributionType\":\"INDIVIDUAL\",\"item\":{\"id\":101,\"name\":\"Red Flower Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage.png\",\"labels\":[\"red\",\"flower\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876,\"fullName\":\"Sunita Singh\"},\"active\":true},\"quantity\":2,\"duePerItem\":50,\"amount\":100},\"paymentDetails\":null},{\"id\":\"LWDUIE67HJK768J\",\"timeCreated\":\"2019-01-15T00:21:45.387Z\",\"type\":\"DUE\",\"status\":\"CORRECT\",\"changeHistory\":false,\"dueDetails\":{\"id\":\"MWQLIO936807SV\",\"distributionType\":\"GROUP\",\"item\":{\"id\":136,\"name\":\"Coach School Bag\",\"type\":\"Bag\",\"picture\":\"https://linktoimage.png\",\"labels\":[\"red\",\"flower\",\"bag\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876,\"fullName\":\"Sunita Singh\"},\"active\":true},\"quantity\":15,\"duePerItem\":120,\"amount\":600},\"paymentDetails\":null},{\"id\":\"QOSID29384NKJD\",\"timeCreated\":\"2019-01-11T00:01:40.030Z\",\"type\":\"DUE\",\"status\":\"DELETED\",\"changeHistory\":false,\"dueDetails\":{\"id\":\"PQMZYS7381KSWGF\",\"distributionType\":\"INDIVIDUAL\",\"item\":{\"id\":102,\"name\":\"Green Apron\",\"type\":\"Apron\",\"picture\":\"https://linktoimage.png\",\"labels\":[\"green\",\"apron\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876,\"fullName\":\"Sunita Singh\"},\"active\":true},\"quantity\":2,\"duePerItem\":75,\"amount\":150},\"paymentDetails\":null}]}";
        ActivityList list = new Gson().fromJson(activitysList,ActivityList.class);
        LogUtils.debug("Shikha",list.getActivities().size()+"");
        activityList.addAll(list.getActivities());
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(View view, int position) {
//        LogUtils.debug("Shikha","onItemClick"+position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAddWage:
                Intent wageIntent = new Intent(WorkerActivityList.this,AddPaymentWageActivity.class);
                wageIntent.putExtra(AppConstant.KEY_ACTIVITY_TYPE, AppConstant.ACTIVITY_TYPE.DUE);
                startActivity(wageIntent);
                break;

            case R.id.tvAddPayout:
                Intent paymentIntent = new Intent(WorkerActivityList.this,AddPaymentWageActivity.class);
                paymentIntent.putExtra(AppConstant.KEY_ACTIVITY_TYPE, AppConstant.ACTIVITY_TYPE.PAYOUT);
                startActivity(paymentIntent);
                break;

        }
    }
}
