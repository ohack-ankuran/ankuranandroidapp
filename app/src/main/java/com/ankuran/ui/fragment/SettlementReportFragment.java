package com.ankuran.ui.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Activity;
import com.ankuran.model.ItemHistory;
import com.ankuran.model.Settlement;
import com.ankuran.model.SettlementList;
import com.ankuran.model.dao.ActivityList;
import com.ankuran.ui.activity.AddNewSettlementActivity;
import com.ankuran.ui.activity.MainActivity;
import com.ankuran.ui.activity.WorkersProfileListActivity;
import com.ankuran.ui.adaptar.PayoutActivityListRecyclerViewAdapter;
import com.ankuran.ui.adaptar.SettlementListRecyclerViewAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SettlementReportFragment extends BaseFragment implements OnRecyclerItemClickListener,View.OnClickListener,IFilterClick {

    RecyclerView mRecyclerView;
    SettlementListRecyclerViewAdapter mAdapter;
    private List<Settlement> settlementList;
    View mView;
    protected String TAG=SettlementReportFragment.class.getSimpleName();

    Button mAddNewSettlement;
    LinearLayout mNoDataFoundContainer;

    String startDate="2019-02-21";
    String endDate="2019-03-30";

    IFilterClick callback;
    TextView mOutstandingAmount;

    public void setFilterClick(IFilterClick callback) {
        this.callback = callback;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settlement_report, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mView=view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllSettlement(startDate,endDate);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    private void initUI() {
        settlementList = new ArrayList<>();
        mNoDataFoundContainer=mView.findViewById(R.id.noDataFoundContainer);
        mRecyclerView = mView.findViewById(R.id.settlementRecyclerView);
        mAddNewSettlement=mView.findViewById(R.id.btnAddNewSettlement);
        mOutstandingAmount=mView.findViewById(R.id.outstandingAmount);
        mAddNewSettlement.setOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapter(null);
    }


    private void getAllSettlement(String startDate,String endData) {
        Log.d(TAG, "getAllEmployee");

        AppMain.getDefaultNetWorkClient().allSettlement(startDate,endData).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == HttpsURLConnection.HTTP_OK){
                    //TODO put validation check
                    Log.d("getAllActivities",new Gson().toJson(response.body()));
                    SettlementList list = new Gson().fromJson(response.body(),SettlementList.class);
                    mOutstandingAmount.setText("Outstanding "+getString(R.string.Rs)+list.getOutstandingSettlement());
                    setAdapter(list);
                    hideProgressDialog();
                }else{
                    Log.d("Shikha","not 200"+new Gson().toJson(response));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure get callv",new Gson().toJson(call));
                showNoDataFoundContainer();
            }
        });

    }

    public void setAdapter(SettlementList list) {
        if (mAdapter == null) {
            mAdapter = new SettlementListRecyclerViewAdapter(settlementList,this);
            mRecyclerView.setAdapter(mAdapter);
        } else if (list!=null && AppUtils.isValidList(list.getSettlements())) {
            mAdapter.setSettlementList(list.getSettlements());
            mAdapter.notifyDataSetChanged();

        }else{
            showNoDataFoundContainer();
        }
    }

    private void showNoDataFoundContainer() {
        mAdapter.setSettlementList(new ArrayList<Settlement>());
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddNewSettlement:
                proceedNext();
                break;
        }
    }

    private void proceedNext() {
        Intent intent = new Intent(getActivity(),AddNewSettlementActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFilterClick(String startDate, String endDate) {
        LogUtils.debug("onFilterClick","payout");
        this.startDate=startDate;
        this.endDate=endDate;
        showProgressDialog();
        getAllSettlement(startDate,endDate);
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
