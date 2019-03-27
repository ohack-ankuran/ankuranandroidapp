package com.ankuran.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ankuran.AddNewProduct;
import com.ankuran.AppConstant;
import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Item;
import com.ankuran.model.ItemHistoryList;
import com.ankuran.ui.activity.InventoryListActivity;
import com.ankuran.ui.activity.ItemDetailsActivity;
import com.ankuran.ui.activity.RemoveNewProduct;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment implements View.OnClickListener {


    Button mAdd,mDelete;
    TextView mName,mQuantity,mCategory;
    View mView;

    Item currentItem;


    public ItemDetailFragment() {
        // Required empty public constructor
    }


    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_detail, container, false);
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

    private void initUI() {
        mName=mView.findViewById(R.id.txtName);
        mQuantity=mView.findViewById(R.id.txtQuantity);
        mCategory=mView.findViewById(R.id.txtCategory);

        mAdd=mView.findViewById(R.id.btnAdd);
        mDelete=mView.findViewById(R.id.btnDelete);

        mAdd.setOnClickListener(this);
        mDelete.setOnClickListener(this);

        setProductInfo();

    }

    private void setProductInfo() {
        if(currentItem!=null) {
            mName.setText(currentItem.getName());
            mQuantity.setText("Quantity : " + currentItem.getAvailableUnits());
            mCategory.setText(currentItem.getCategory());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Shikha","OnResume");
        getProductById();
    }


    private void getProductById() {
        AppMain.getDefaultNetWorkClient().getProductByID(currentItem.getId()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == HttpsURLConnection.HTTP_OK){
                    //TODO put validation check
                    Log.d("getAllActivities",new Gson().toJson(response.body()));

                    Item item = new Gson().fromJson(response.body(),Item.class);
                    currentItem=item;
                    setProductInfo();
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
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:

                Intent intent = new Intent(getActivity(),AddNewProduct.class);
                intent.putExtra(AppConstant.KEY_CURRENT_ITEM, currentItem);
                startActivity(intent);
                break;

            case R.id.btnDelete:
                Intent intentDelete = new Intent(getActivity(),RemoveNewProduct.class);
                intentDelete.putExtra(AppConstant.KEY_CURRENT_ITEM, currentItem);
                startActivity(intentDelete);
                break;
        }
    }
}
