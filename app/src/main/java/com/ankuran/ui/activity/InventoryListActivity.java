package com.ankuran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.ankuran.AppConstant;
import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Employee;
import com.ankuran.model.Item;
import com.ankuran.model.dao.ItemList;
import com.ankuran.ui.adaptar.ItemRecyclerViewAdapter;
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

public class InventoryListActivity extends BaseActivity implements OnRecyclerItemClickListener{

    private List<Item> itemList;
    RecyclerView mRecyclerView;
    ItemRecyclerViewAdapter mAdapter;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_inventory_list;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();

    }

    private void initUI() {
        itemList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.itemRecyclerView);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapter(null);
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
                    //TODO 
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

    public void setAdapter(ItemList list) {
        if (mAdapter == null) {
            mAdapter = new ItemRecyclerViewAdapter(itemList,this);
            mRecyclerView.setAdapter(mAdapter);
        } else if (list!=null && AppUtils.isValidList(list.getItems())) {
            mAdapter.setItemList(list.getItems());
            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemClick(View view, int position, Object object) {
        Item currentItem= (Item) object;
        Intent intent = new Intent(InventoryListActivity.this,ItemDetailsActivity.class);
        intent.putExtra(AppConstant.KEY_CURRENT_ITEM, currentItem);
        startActivity(intent);
    }
}
