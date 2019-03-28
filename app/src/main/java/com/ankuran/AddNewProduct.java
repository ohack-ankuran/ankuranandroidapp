package com.ankuran;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ankuran.model.Item;
import com.ankuran.model.ItemHistory;
import com.ankuran.model.ItemHistoryEnum;
import com.ankuran.ui.activity.BaseActivity;
import com.ankuran.util.AppUtils;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewProduct extends BaseActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener ,DialogInterface.OnClickListener{


    Button mConfirm;
    TextView mName,mQuantity,mCategory;
    AppCompatSpinner mQuantitySpinner;
    Intent intent;
    Item currentItem;
    long quantity=0;


    RadioGroup mRadioGroup;
    RadioButton mRadioButton;
    EditText mNote;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_new_product;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();
    }

    private void initUI() {
        intent = getIntent();
        if (intent != null) {
            currentItem = (Item) intent.getSerializableExtra(AppConstant.KEY_CURRENT_ITEM);
            if(currentItem==null){
                LogUtils.debug(TAG,"NO current item found");
                finish();

            }

        }

        mName=findViewById(R.id.txtName);
        mQuantity=findViewById(R.id.txtQuantity);
        mCategory=findViewById(R.id.txtCategory);

        mConfirm=findViewById(R.id.btnAddConfirm);
        mConfirm.setOnClickListener(this);

        mRadioGroup = findViewById(R.id.rgReason);
        mNote=findViewById(R.id.etNote);

        setProductInfo();
        setNumberSpinner();




    }

    private void setProductInfo() {
        mName.setText(currentItem.getName());
        mQuantity.setText("Quantity : "+currentItem.getAvailableUnits());
        mCategory.setText(currentItem.getCategory());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddConfirm:
                    performSave();
                break;
        }

    }

    private void performSave() {
        ItemHistory history = new ItemHistory();
        history.setType(ItemHistoryEnum.HistoryType.ADD);
        int selectedId=mRadioGroup.getCheckedRadioButtonId();
        mRadioButton=findViewById(selectedId);
        String reason= mRadioButton.getText().toString();
        history.setReason(AppUtils.getHistoryReason(reason));
        String note = mNote.getText() != null ? mNote.getText().toString().trim() : "";
        history.setNotes(note);
        history.setUnits(quantity);
        history.setTimeCreated(AppUtils.getCurrentDate());
        history.setItemId(Long.valueOf(currentItem.getId()));
        history.setCentreId(1L);

        if (!AppUtils.isConnectedToInternet()) {
            showToast(getString(R.string.network_error));
            return;
        }
        addHistory(history);
    }

    private void addHistory(ItemHistory history) {
        AppMain.getDefaultNetWorkClient().addHistory(currentItem.getId(),history).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                LogUtils.debug("Network call OnResponse post call",new Gson().toJson(response));
                processServerResponse(response,"creat");

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure post call",new Gson().toJson(call));
            }
        });
    }

    private void setNumberSpinner() {
        mQuantitySpinner=findViewById(R.id.npAddQuantity);
        Integer[] items = new Integer[50];
        for (int i=0;i<50;i++){
            items[i]=i+1;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        mQuantitySpinner.setOnItemSelectedListener(this);
        mQuantitySpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        quantity = (int) adapterView.getItemAtPosition(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void clearViews() {
        //TODO reset radio group
        mNote.setText("");
        mQuantitySpinner.setSelection(0);
    }

    private void processServerResponse(Response<JsonObject> response, String operation) {
        if(response.code() == HttpsURLConnection.HTTP_OK ||response.code() == HttpsURLConnection.HTTP_CREATED||response.code() == HttpsURLConnection.HTTP_ACCEPTED){
            Log.d("Shikha",new Gson().toJson(response.body()));
            showInfoDialog("", "Information added successfully!!",this);

        }else{
            Log.d("Shikha","not 200"+new Gson().toJson(response));
            showInfoDialog("","Error occurred while "+operation+"ing.Please try again later!!",this);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int i) {
        dialog.dismiss();
        clearViews();
        finish();
    }
}
