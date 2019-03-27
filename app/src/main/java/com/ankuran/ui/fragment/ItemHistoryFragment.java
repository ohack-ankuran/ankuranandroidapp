package com.ankuran.ui.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Item;
import com.ankuran.model.ItemHistory;
import com.ankuran.model.ItemHistoryList;
import com.ankuran.ui.adaptar.ItemHistoryListRecyclerViewAdapter;
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
public class ItemHistoryFragment extends BaseFragment implements OnRecyclerItemClickListener,View.OnClickListener ,DatePickerFragment.DatePickerDialogListener{


    RecyclerView mRecyclerView;
    ItemHistoryListRecyclerViewAdapter mAdapter;
    private List<ItemHistory> itemHistoryList;
    View mView;
    protected String TAG=PayoutReportFragment.class.getSimpleName();

    Button mBtnView;
    LinearLayout mLLStartDate,mLLEndDate;
    TextView mTVStartDate,mTVEndDate;
    Boolean isStartDate=true;

    LinearLayout mNoDataFoundContainer;
    Item currentItem;

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_history, container, false);
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
        getAllItemHistory();
    }

    private void initUI() {
        itemHistoryList = new ArrayList<>();

        mNoDataFoundContainer=mView.findViewById(R.id.noDataFoundContainer);
        mLLStartDate =mView.findViewById(R.id.llStartDateContainer);
        mLLStartDate.setOnClickListener(this);
        mLLEndDate =mView.findViewById(R.id.llEndDateContainer);
        mLLEndDate.setOnClickListener(this);

        mTVStartDate =mView.findViewById(R.id.tvStartDate);
        mTVEndDate =mView.findViewById(R.id.tvEndDate);


        mRecyclerView = mView.findViewById(R.id.itemHistoryRecyclerView);
        mBtnView=mView.findViewById(R.id.btnView);
        mBtnView.setOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapter(null);
    }


    private void getAllItemHistory() {
        Log.d(TAG, "getAllEmployee");
        String startDate = mTVStartDate.getText()!=null && !TextUtils.isEmpty(mTVStartDate.getText().toString()) ? mTVStartDate.getText().toString().trim() : "2019-02-21";
        String endDate = mTVEndDate.getText() != null && !TextUtils.isEmpty(mTVEndDate.getText().toString())? mTVEndDate.getText().toString().trim() : "2019-03-30";

        AppMain.getDefaultNetWorkClient().getItemHistory(currentItem.getId(),startDate,endDate).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == HttpsURLConnection.HTTP_OK){
                    //TODO put validation check
                    Log.d("getAllActivities",new Gson().toJson(response.body()));

                    ItemHistoryList list = new Gson().fromJson(response.body(),ItemHistoryList.class);
                    setAdapter(list);
                    hideProgressDialog();
                }else{
                    Log.d("Shikha","not 200"+new Gson().toJson(response));
                    showNoDataFoundContainer();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure get callv",new Gson().toJson(call));
                showNoDataFoundContainer();
                hideProgressDialog();
            }
        });

    }

    public void setAdapter(ItemHistoryList list) {
        mNoDataFoundContainer.setVisibility(View.GONE);
        if (mAdapter == null) {
            mAdapter = new ItemHistoryListRecyclerViewAdapter(itemHistoryList,this);
            mRecyclerView.setAdapter(mAdapter);
        } else if (list!=null && AppUtils.isValidList(list.getItemUpdateHistory())) {
            mAdapter.setItemHistoryList(list.getItemUpdateHistory());
            mAdapter.notifyDataSetChanged();

        }else{
            showNoDataFoundContainer();
        }
    }

    private void showNoDataFoundContainer() {
        mAdapter.setItemHistoryList(new ArrayList<ItemHistory>());
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
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.llStartDateContainer:
                showDatePicker(this);
                //TODO end date validation
                break;


            case R.id.llEndDateContainer:
                showDatePicker(this);
                break;

            case R.id.btnView:
                showProgressDialog();
                getAllItemHistory();
                break;
        }

    }



    @Override
    public void onDateSelected(DatePicker view, int day, int month, int year) {
        String date=year + "-" + month + "-" + day;
        //Todo set date
        if(isStartDate){
            mTVStartDate.setText(date);
            mLLEndDate.setClickable(true);
        }else{
            mTVEndDate.setText(date);
            mLLEndDate.setClickable(false);
        }

        isStartDate=!isStartDate;
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
