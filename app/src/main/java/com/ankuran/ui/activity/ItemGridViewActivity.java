package com.ankuran.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ankuran.model.Item;
import com.ankuran.model.dao.ItemList;
import com.ankuran.ui.adaptar.ItemRecyclerViewAdapter;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.ankuran.R;

import java.util.ArrayList;
import java.util.List;

public class ItemGridViewActivity extends BaseActivity implements OnRecyclerItemClickListener {

    RecyclerView mRecyclerView;
    ItemRecyclerViewAdapter mAdapter;
    private List<Item> itemList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_item_grid_view;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();
        addDummyDataTo();
    }

    private void initUI() {
        itemList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.itemRecyclerView);
        mAdapter = new ItemRecyclerViewAdapter(itemList,this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);



    }

    private void addDummyDataTo() {
        String itemsList = "{\"products\":[{\"id\":1323244,\"name\":\"Red Flower Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"red\",\"flower\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":1323244,\"name\":\"Blue Flower Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"red\",\"flower\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":1323244,\"name\":\"Green Flower Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"red\",\"flower\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":1323244,\"name\":\"Yellow Flower Bedsheet\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"red\",\"flower\",\"bedsheet\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":111123,\"name\":\"Pillow cover set blue\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"pillow\",\"cover\",\"blue\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":111123,\"name\":\"Pillow cover set green\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"pillow\",\"cover\",\"blue\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":111123,\"name\":\"Pillow cover set pink\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"pillow\",\"cover\",\"blue\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":3234424,\"name\":\"Pillow cover set red\",\"type\":\"Bedsheet\",\"picture\":\"https://linktoimage\",\"labels\":[\"pillow\",\"cover\",\"red\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":132344,\"name\":\"Coach bag Large\",\"type\":\"Bag\",\"picture\":\"https://linktoimage\",\"labels\":[\"coach\",\"bag\",\"large\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":132344,\"name\":\"Coach bag small\",\"type\":\"Bag\",\"picture\":\"https://linktoimage\",\"labels\":[\"coach\",\"bag\",\"large\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":132344,\"name\":\"Coach bag medium\",\"type\":\"Bag\",\"picture\":\"https://linktoimage\",\"labels\":[\"coach\",\"bag\",\"large\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}},{\"id\":132344,\"name\":\"Coach bag extra Large\",\"type\":\"Bag\",\"picture\":\"https://linktoimage\",\"labels\":[\"coach\",\"bag\",\"large\"],\"timeCreated\":\"2016-01-11T12:10:59.000Z\",\"addedBy\":{\"id\":137912876}}]}";
        ItemList list = new Gson().fromJson(itemsList,ItemList.class);
        LogUtils.debug("Shikha",list.getProducts().size()+"");
        itemList.addAll(list.getProducts());
        mAdapter.notifyDataSetChanged();


    }


    @Override
    public void onItemClick(View view, int position) {
        LogUtils.debug("Shikha","onItemClick"+position);
    }
}
