package com.ankuran.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paypal.ankuran.R;

public class AddNewWorker extends BaseActivity implements View.OnClickListener {

    EditText mETName;
    EditText mETHusbandName;
    EditText mETHusbandId;
    EditText mETPhone;
    Button mSave;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_new_worker;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();
    }

    private void initUI() {
        mETName=findViewById(R.id.etName);
        mETHusbandName=findViewById(R.id.etHusbandName);
        mETHusbandId=findViewById(R.id.etHusbandId);
        mETPhone=findViewById(R.id.etPhone);

        mSave=findViewById(R.id.btnSave);
        mSave.setOnClickListener(this);


        //TODO add data for edit screen using intent
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                break;
        }

    }
}
