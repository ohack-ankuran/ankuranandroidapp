package com.paypal.ankuran.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.paypal.ankuran.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTVWorkerProfile;
    TextView mTVCalculateWage;
    TextView mTVInventory;
    TextView mTVSocialImpact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        mTVWorkerProfile = findViewById(R.id.tvWorkerProfile);
        mTVCalculateWage = findViewById(R.id.tvCalculateWage);
        mTVInventory = findViewById(R.id.tvInventory);
        mTVSocialImpact = findViewById(R.id.tvSocialReport);

        mTVWorkerProfile.setOnClickListener(this);
        mTVCalculateWage.setOnClickListener(this);
        mTVInventory.setOnClickListener(this);
        mTVSocialImpact.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvWorkerProfile:
                Intent workerProfileIntent = new Intent(MainActivity.this,WorkersProfileListActivity.class);
                startActivity(workerProfileIntent);
                break;

            case R.id.tvCalculateWage:
                Intent calculateIntent = new Intent(MainActivity.this,CalculateWageActivity.class);
                startActivity(calculateIntent);
                break;


            case R.id.tvInventory:
                Intent inventoryIntent = new Intent(MainActivity.this,InventoryDetailsActivity.class);
                startActivity(inventoryIntent);
                break;


            case R.id.tvSocialReport:
                Intent socialReportIntent = new Intent(MainActivity.this,SocialReportActivity.class);
                startActivity(socialReportIntent);
                break;
        }

    }
}
