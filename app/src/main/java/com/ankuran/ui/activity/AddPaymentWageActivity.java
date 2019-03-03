package com.ankuran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankuran.AppConstant;
import com.ankuran.R;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPaymentWageActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout llAddWageContainer, llAddPayoutContainer;
    TextView addWage,addPayment;
    Intent intent;
    AppConstant.ACTIVITY_TYPE activityType;

    EditText mEtPaymentDate,mEtWageDate,mEtPayment;
    Button mBtnAddPayment,mBtnAddWage;

    AppCompatSpinner mQuantitySpinner;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_payment_wage;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();
    }

    private void initUI() {
        //TODO add worker name in toolbar
        addWage =findViewById(R.id.tvAddWage);
        addWage.setVisibility(View.GONE);
        addPayment =findViewById(R.id.tvAddPayout);
        addPayment.setVisibility(View.GONE);
        llAddWageContainer = findViewById(R.id.llAddWageContainer);
        llAddPayoutContainer = findViewById(R.id.llAddPayoutContainer);
        mEtPaymentDate=findViewById(R.id.etPaymentDate);
        mEtWageDate=findViewById(R.id.etWageDate);
        mEtPayment=findViewById(R.id.etPayment);
        mBtnAddPayment=findViewById(R.id.btnAddPayment);
        mBtnAddPayment.setOnClickListener(this);
        mBtnAddWage=findViewById(R.id.btnAddWage);
        mBtnAddWage.setOnClickListener(this);
        setCurrentDate();
        setNumberSpinner();
        intent = getIntent();
        if (intent != null) {
            activityType = (AppConstant.ACTIVITY_TYPE) intent.getSerializableExtra(AppConstant.KEY_ACTIVITY_TYPE);
            if (activityType.equals(AppConstant.ACTIVITY_TYPE.DUE)) {
                showDuesScreen();

            } else {
                showPaymentScreen();
            }
        }
    }

    private void setNumberSpinner() {
        mQuantitySpinner=findViewById(R.id.npWageQuantity);
        Integer[] items = new Integer[200];
        for (int i=0;i<200;i++){
            items[i]=i;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        mQuantitySpinner.setAdapter(adapter);
    }


    private void setCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.format(new Date());
        mEtPaymentDate.setText(currentDate);
        mEtWageDate.setText(currentDate);

    }

    private void showPaymentScreen() {
        AddPaymentWageActivity.this.setTitle("Add Payment");
        llAddWageContainer.setVisibility(View.GONE);
        llAddPayoutContainer.setVisibility(View.VISIBLE);
    }

    private void showDuesScreen() {
        AddPaymentWageActivity.this.setTitle("Add Wages");
        llAddWageContainer.setVisibility(View.VISIBLE);
        llAddPayoutContainer.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddPayment:
                //TODO add payment api
                break;

            case R.id.tvAddWage:
                showDuesScreen();
                break;

            case R.id.tvAddPayout:
                showPaymentScreen();
                break;


        }
    }
}
