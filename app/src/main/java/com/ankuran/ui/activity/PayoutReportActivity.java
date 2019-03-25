package com.ankuran.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Activity;
import com.ankuran.model.dao.ActivityList;
import com.ankuran.ui.adaptar.ActivityListRecyclerViewAdapter;
import com.ankuran.ui.adaptar.PayoutActivityListRecyclerViewAdapter;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.util.AppUtils;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayoutReportActivity extends BaseActivity implements OnRecyclerItemClickListener {

    RecyclerView mRecyclerView;
    PayoutActivityListRecyclerViewAdapter mAdapter;
    private List<Activity> activityList;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_payout_report;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();

    }

    private void initUI() {
        activityList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.payoutActivityRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapter(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllActivities();
    }

    private void getAllActivities() {
        Log.d(TAG, "getAllEmployee");

        AppMain.getDefaultNetWorkClient().allPayout("2019-02-21","2019-03-28").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == HttpsURLConnection.HTTP_OK){
                    //TODO put validation check
                    Log.d("getAllActivities",new Gson().toJson(response.body()));

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

    public void setAdapter(ActivityList list) {
        if (mAdapter == null) {
            mAdapter = new PayoutActivityListRecyclerViewAdapter(activityList,this);
            mRecyclerView.setAdapter(mAdapter);
        } else if (list!=null && AppUtils.isValidList(list.getActivities())) {
            mAdapter.setActivityList(list.getActivities());
            mAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemClick(View view, int position, Object object) {

    }
}
