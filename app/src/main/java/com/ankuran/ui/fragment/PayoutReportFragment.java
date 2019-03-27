package com.ankuran.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Activity;
import com.ankuran.model.Settlement;
import com.ankuran.model.dao.ActivityList;
import com.ankuran.ui.adaptar.PayoutActivityListRecyclerViewAdapter;
import com.ankuran.ui.adaptar.listener.IFilterClick;
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

public class PayoutReportFragment extends Fragment implements OnRecyclerItemClickListener,IFilterClick {

    RecyclerView mRecyclerView;
    PayoutActivityListRecyclerViewAdapter mAdapter;
    private List<Activity> activityList;
    View mView;
    protected String TAG=PayoutReportFragment.class.getSimpleName();

    String startDate="2019-02-21";
    String endDate="2019-03-30";

    LinearLayout mNoDataFoundContainer;

    IFilterClick callback;

    public void setFilterClick(IFilterClick callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Shikha","Inside payout fragment::onCreateView");
        return inflater.inflate(R.layout.fragment_payout_report, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mView=view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllActivities(startDate,endDate);
    }

    private void initUI() {
        activityList = new ArrayList<>();
        mNoDataFoundContainer=mView.findViewById(R.id.noDataFoundContainer);
        mRecyclerView = mView.findViewById(R.id.payoutActivityRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapter(null);
    }


    private void getAllActivities(String startDate,String endDate) {
        Log.d(TAG, "getAllEmployee");

        AppMain.getDefaultNetWorkClient().allPayout(startDate,endDate).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == HttpsURLConnection.HTTP_OK){
                    //TODO put validation check
                    Log.d("getAllActivities",new Gson().toJson(response.body()));

                    ActivityList list = new Gson().fromJson(response.body(),ActivityList.class);
                    setAdapter(list);
                    hideProgressDialog();
                }else{
                    Log.d("Shikha","not 200"+new Gson().toJson(response));
                    showNoDataFoundContainer();;
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure get callv",new Gson().toJson(call));
                showNoDataFoundContainer();;
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

        }else{
            showNoDataFoundContainer();
        }
    }

    private void showNoDataFoundContainer() {
        mAdapter.setActivityList(new ArrayList<Activity>());
        mAdapter.notifyDataSetChanged();
        mNoDataFoundContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemClick(View view, int position, Object object) {

    }

    @Override
    public void onFilterClick(String startDate, String endDate) {
        LogUtils.debug("onFilterClick","payout");
        this.startDate=startDate;
        this.endDate=endDate;
        showProgressDialog();
        getAllActivities(startDate,endDate);
    }

    private ProgressDialog mProgressDialog;
    public void showProgressDialog() {
        mProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Loading...", true);
        mProgressDialog.setCancelable(false);
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }
}
