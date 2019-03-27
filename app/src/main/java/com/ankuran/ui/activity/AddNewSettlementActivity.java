package com.ankuran.ui.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Item;
import com.ankuran.model.Settlement;
import com.ankuran.util.AppUtils;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewSettlementActivity extends BaseActivity implements View.OnClickListener ,DialogInterface.OnClickListener{

    EditText mDate,mAmount,mNotes;
    CheckBox mCBCorrection;
    Button mSave;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_new_settlement;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();
    }

    private void initUI() {
        mDate=findViewById(R.id.etDate);
        mAmount=findViewById(R.id.etAmount);
        mNotes=findViewById(R.id.etName);
        mCBCorrection=findViewById(R.id.cbCorrection);
        mSave=findViewById(R.id.btnAdSettlement);
        mSave.setOnClickListener(this);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.format(new Date());
        mDate.setText(currentDate);
        }

    private void performSave() {
        Settlement settlement = new Settlement();
        String amount = mAmount.getText() != null ? mAmount.getText().toString().trim() : "";
        if (TextUtils.isEmpty(amount)) {
            showToast("Please enter amount");
            return;
        }

        settlement.setAmount(Double.valueOf(amount));
        String note = mNotes.getText() != null ? mNotes.getText().toString().trim() : "";
        settlement.setNotes(note);

        settlement.setCorrection(mCBCorrection.isChecked());

        settlement.setTimeCreated(AppUtils.getCurrentDate());

        if (!AppUtils.isConnectedToInternet()) {
            showToast(getString(R.string.network_error));
            return;
        }
        addSettlement(settlement);

    }

    private void addSettlement(Settlement settlement) {
        AppMain.getDefaultNetWorkClient().addSettlement(settlement).enqueue(new Callback<JsonObject>() {
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

    private void processServerResponse(Response<JsonObject> response, String operation) {
        if(response.code() == HttpsURLConnection.HTTP_OK ||response.code() == HttpsURLConnection.HTTP_CREATED||response.code() == HttpsURLConnection.HTTP_ACCEPTED){
            Log.d("Shikha",new Gson().toJson(response.body()));
            showInfoDialog("", "Item added successfully!!",this);

        }else{
            Log.d("Shikha","not 200"+new Gson().toJson(response));
            showInfoDialog("","Item added successfully!!",this);
        }
    }

    private void clearViews() {
        mAmount.setText("");
        mNotes.setText("");
        mCBCorrection.setChecked(false);
    }

    @Override
    public void onClick(DialogInterface dialog, int i) {
        dialog.dismiss();
        clearViews();
        finish();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdSettlement:
                 performSave();
                break;
        }

    }
}
