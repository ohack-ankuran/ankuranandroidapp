package com.ankuran.ui.fragment;


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

import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Activity;
import com.ankuran.model.Settlement;
import com.ankuran.model.SettlementList;
import com.ankuran.model.dao.ActivityList;
import com.ankuran.ui.activity.AddNewSettlementActivity;
import com.ankuran.ui.activity.MainActivity;
import com.ankuran.ui.activity.WorkersProfileListActivity;
import com.ankuran.ui.adaptar.PayoutActivityListRecyclerViewAdapter;
import com.ankuran.ui.adaptar.SettlementListRecyclerViewAdapter;
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
public class SettlementReportFragment extends Fragment implements OnRecyclerItemClickListener,View.OnClickListener {

    RecyclerView mRecyclerView;
    SettlementListRecyclerViewAdapter mAdapter;
    private List<Settlement> settlementList;
    View mView;
    protected String TAG=SettlementReportFragment.class.getSimpleName();

    Button mAddNewSettlement;


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
        getAllSettlement();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    private void initUI() {
        settlementList = new ArrayList<>();
        mRecyclerView = mView.findViewById(R.id.settlementRecyclerView);
        mAddNewSettlement=mView.findViewById(R.id.btnAddNewSettlement);
        mAddNewSettlement.setOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapter(null);
    }


    private void getAllSettlement() {
        Log.d(TAG, "getAllEmployee");

        AppMain.getDefaultNetWorkClient().allSettlement().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == HttpsURLConnection.HTTP_OK){
                    //TODO put validation check
                    Log.d("getAllActivities",new Gson().toJson(response.body()));

                    SettlementList list = new Gson().fromJson(response.body(),SettlementList.class);
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

    public void setAdapter(SettlementList list) {
        if (mAdapter == null) {
            mAdapter = new SettlementListRecyclerViewAdapter(settlementList,this);
            mRecyclerView.setAdapter(mAdapter);
        } else if (list!=null && AppUtils.isValidList(list.getSettlements())) {
            mAdapter.setSettlementList(list.getSettlements());
            mAdapter.notifyDataSetChanged();

        }
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

}
