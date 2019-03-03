package com.ankuran.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ankuran.AppConstant;
import com.ankuran.R;

public class AddPaymentWageActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout llAddWageContainer, llAddPayoutContainer;
    Intent intent;
    AppConstant.ACTIVITY_TYPE activityType;

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
        llAddWageContainer = findViewById(R.id.llAddWageContainer);
        llAddPayoutContainer = findViewById(R.id.llAddPayoutContainer);
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

    private void showPaymentScreen() {
        llAddWageContainer.setVisibility(View.GONE);
        llAddPayoutContainer.setVisibility(View.VISIBLE);
    }

    private void showDuesScreen() {
        llAddWageContainer.setVisibility(View.VISIBLE);
        llAddPayoutContainer.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddWage:
                showDuesScreen();
                break;

            case R.id.tvAddPayout:
                showPaymentScreen();
                break;

        }
    }
}
