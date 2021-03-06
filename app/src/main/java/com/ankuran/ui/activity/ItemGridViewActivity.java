package com.ankuran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ankuran.AppMain;
import com.ankuran.model.Item;
import com.ankuran.model.dao.EmployeeList;
import com.ankuran.model.dao.ItemList;
import com.ankuran.ui.adaptar.ItemRecyclerViewAdapter;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.util.AppUtils;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.ankuran.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemGridViewActivity extends BaseActivity implements OnRecyclerItemClickListener,View.OnClickListener {

    RecyclerView mRecyclerView;
    ItemRecyclerViewAdapter mAdapter;
    private List<Item> itemList;

    Button mBtnAddNewItem;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_item_grid_view;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllItems();
    }

    private void getAllItems() {
        Log.d(TAG, "getAllEmployee");

        AppMain.getDefaultNetWorkClient().allAllProducts().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == HttpsURLConnection.HTTP_OK){
                    //TODO put validation check
                    Log.d("Shikha",new Gson().toJson(response.body()));
                    ItemList list = new Gson().fromJson(response.body(),ItemList.class);
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
        itemList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.itemRecyclerView);

        mBtnAddNewItem=findViewById(R.id.btnAddNewItem);
        mBtnAddNewItem.setOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapter(null);



    }

    public void setAdapter(ItemList list) {
        if (mAdapter == null) {
            mAdapter = new ItemRecyclerViewAdapter(itemList,this);
            mRecyclerView.setAdapter(mAdapter);
        } else if (list!=null && AppUtils.isValidList(list.getItems())) {
            mAdapter.setItemList(list.getItems());
            mAdapter.notifyDataSetChanged();

        }
    }

    private void addDummyDataTo() {
        String itemsList = "{\"products\":[{\"id\":1323244,\"name\":\"Red Flower Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"red\",\"flower\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":1323244,\"name\":\"Blue Flower Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"red\",\"flower\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":1323244,\"name\":\"Green Flower Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"red\",\"flower\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":1323244,\"name\":\"Yellow Flower Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"red\",\"flower\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":111123,\"name\":\"Pillow cover set blue\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"pillow\",\"cover\",\"blue\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":111123,\"name\":\"Pillow cover set green\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"pillow\",\"cover\",\"blue\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":111123,\"name\":\"Pillow cover set pink\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"pillow\",\"cover\",\"blue\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":3234424,\"name\":\"Pillow cover set red\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"pillow\",\"cover\",\"red\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":132344,\"name\":\"Coach bag Large\",\"type\":\"Bag\",\"picture\":\"https://linktoimage\",\"labels\":[\"coach\",\"bag\",\"large\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":132344,\"name\":\"Coach bag small\",\"type\":\"Bag\",\"picture\":\"https://linktoimage\",\"labels\":[\"coach\",\"bag\",\"large\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":132344,\"name\":\"Coach bag medium\",\"type\":\"Bag\",\"picture\":\"https://linktoimage\",\"labels\":[\"coach\",\"bag\",\"large\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":132344,\"name\":\"Coach bag extra Large\",\"type\":\"Bag\",\"picture\":\"https://linktoimage\",\"labels\":[\"coach\",\"bag\",\"large\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}}]}";
        ItemList list = new Gson().fromJson(itemsList,ItemList.class);
        LogUtils.debug("Shikha",list.getItems().size()+"");
        itemList.addAll(list.getItems());
        mAdapter.notifyDataSetChanged();


    }


    @Override
    public void onItemClick(View view, int position) {
        LogUtils.debug("Shikha","onItemClick"+position);
    }

    @Override
    public void onItemClick(View view, int position, Object object) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddNewItem:
                Intent addIntent = new Intent(ItemGridViewActivity.this,AddNewItem.class);
                startActivity(addIntent);
                break;
        }
    }
}
