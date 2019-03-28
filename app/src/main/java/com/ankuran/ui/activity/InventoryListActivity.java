package com.ankuran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ankuran.AppConstant;
import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Item;
import com.ankuran.model.ItemCategory;
import com.ankuran.model.ItemFilterView;
import com.ankuran.model.ItemLabel;
import com.ankuran.model.dao.ItemList;
import com.ankuran.ui.adaptar.ItemCategoryFilterRecyclerViewAdapter;
import com.ankuran.ui.adaptar.ItemFilterRecyclerViewAdapter;
import com.ankuran.ui.adaptar.ItemLabelFilterRecyclerViewAdapter;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.util.AppUtils;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryListActivity extends BaseActivity implements OnRecyclerItemClickListener {

    private Set<ItemFilterView> mItemFilterViews;
    private Set<ItemCategory> mCategories;
    private Set<ItemLabel> mLabels;
    private RecyclerView mCategoryRecyclerView;
    private RecyclerView mLabelRecyclerView;
    private RecyclerView mItemRecyclerView;
    private ItemCategoryFilterRecyclerViewAdapter mItemCategoryRecyclerViewAdapter;
    private ItemLabelFilterRecyclerViewAdapter mItemLabelRecyclerViewAdapter;
    private ItemFilterRecyclerViewAdapter mItemFilterRecyclerViewAdapter;
    private Button mButton;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_inventory_list;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initFilterUI();
        initUI();
    }

    private void initFilterUI() {
        mItemFilterViews = new HashSet<>();
        mCategories = new HashSet<>();
        mLabels = new HashSet<>();

        mCategoryRecyclerView = findViewById(R.id.categoryList);
        RecyclerView.LayoutManager mCategoryLayoutManager = new LinearLayoutManager(this);
        mCategoryRecyclerView.setLayoutManager(mCategoryLayoutManager);
        mItemCategoryRecyclerViewAdapter = new ItemCategoryFilterRecyclerViewAdapter(new ArrayList<ItemCategory>(), this);
        mCategoryRecyclerView.setAdapter(mItemCategoryRecyclerViewAdapter);

        mLabelRecyclerView = findViewById(R.id.labelList);
        RecyclerView.LayoutManager mLabelLayoutManager = new GridLayoutManager(this, 2);
        mLabelRecyclerView.setLayoutManager(mLabelLayoutManager);
        mItemLabelRecyclerViewAdapter = new ItemLabelFilterRecyclerViewAdapter(new ArrayList<ItemLabel>(), this);
        mLabelRecyclerView.setAdapter(mItemLabelRecyclerViewAdapter);

        mButton = findViewById(R.id.btnApplyFilters);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                applyFilters();
            }
        });
    }

    private void initUI() {
        mItemRecyclerView = findViewById(R.id.item_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mItemRecyclerView.setLayoutManager(mLayoutManager);
        mItemRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mItemFilterRecyclerViewAdapter = new ItemFilterRecyclerViewAdapter(new ArrayList<ItemFilterView>(), this);
        mItemRecyclerView.setAdapter(mItemFilterRecyclerViewAdapter);
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
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    //TODO 
                    Log.d("Shikha", new Gson().toJson(response.body()));
                    ItemList list = new Gson().fromJson(response.body(), ItemList.class);
                    buildItemFilterView(list);
                } else {
                    Log.d("Shikha", "not 200" + new Gson().toJson(response));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure get callv", new Gson().toJson(call));
            }
        });
    }

    private void buildItemFilterView(ItemList list) {
        if (AppUtils.isValidList(list.getItems())) {
            List<Item> itemList = list.getItems(); // gets initial items from db

            // build view for item recycler view
            for (Item item : itemList) {
                mItemFilterViews.add(new ItemFilterView(item, buildItemCategory(item), buildItemLabel(item)));
            }

            mItemCategoryRecyclerViewAdapter.setCategories(new ArrayList<ItemCategory>(mCategories));
            mItemCategoryRecyclerViewAdapter.notifyDataSetChanged();
            assert (mItemCategoryRecyclerViewAdapter.getItemCount() == mCategories.size());

            mItemLabelRecyclerViewAdapter.setLabels(new ArrayList<ItemLabel>(mLabels));
            mItemLabelRecyclerViewAdapter.notifyDataSetChanged();
            assert (mItemLabelRecyclerViewAdapter.getItemCount() == mLabels.size());

            mItemFilterRecyclerViewAdapter.setItemList(new ArrayList<ItemFilterView>(mItemFilterViews));
            mItemFilterRecyclerViewAdapter.notifyDataSetChanged();
            assert (mItemFilterRecyclerViewAdapter.getItemCount() == mItemFilterViews.size());

        }
    }

    private ItemCategory buildItemCategory(Item item) {
        ItemCategory itemCategory = new ItemCategory(item.getCategory(), false);
        mCategories.add(itemCategory);
        return itemCategory;
    }

    private List<ItemLabel> buildItemLabel(Item item) {
        List<ItemLabel> itemLabels = new ArrayList<>();
        List<String> labels = item.getLabels();
        if (AppUtils.isValidList(labels)) {
            for (String label : labels) {
                ItemLabel itemLabel = new ItemLabel(label, false);
                itemLabels.add(itemLabel);
            }
            mLabels.addAll(itemLabels);
        }
        return itemLabels;
    }

    private void applyFilters() {
        boolean filterSelected = false;
        Set<ItemFilterView> itemsFiltered = new HashSet<>();
        for (ItemFilterView itemFilterView : mItemFilterViews) {
            if (itemFilterView.category.isSelected()) {
                filterSelected = true;
                itemsFiltered.add(itemFilterView);
            }
            for (ItemLabel label : itemFilterView.getLabels()) {
                if (label.isSelected()) {
                    filterSelected = true;
                    itemsFiltered.add(itemFilterView);
                }
            }
        }

        mItemFilterRecyclerViewAdapter.setItemList(new ArrayList<>(filterSelected ? itemsFiltered : new ArrayList<ItemFilterView>(mItemFilterViews)));
        mItemFilterRecyclerViewAdapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemClick(View view, int position, Object object) {
        Item currentItem = (Item) object;
        Intent intent = new Intent(InventoryListActivity.this, ItemDetailsActivity.class);
        intent.putExtra(AppConstant.KEY_CURRENT_ITEM, currentItem);
        startActivity(intent);
    }
}
