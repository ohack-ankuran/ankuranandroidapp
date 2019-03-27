package com.ankuran.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankuran.R;
import com.ankuran.ui.adaptar.TabAdapter;
import com.ankuran.ui.adaptar.listener.IFilterClick;
import com.ankuran.ui.fragment.DatePickerFragment;
import com.ankuran.ui.fragment.PayoutReportFragment;
import com.ankuran.ui.fragment.SettlementReportFragment;

public class PayoutReportActivity extends BaseActivity implements View.OnClickListener ,DatePickerFragment.DatePickerDialogListener{


    private TabAdapter mTabAdaptor;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    Button mBtnView;
    LinearLayout mLLStartDate,mLLEndDate;
    TextView mTVStartDate,mTVEndDate;
    Boolean isStartDate=true;

    IFilterClick filterClick;

    PayoutReportFragment payoutReportFragment;
    SettlementReportFragment settlementReportFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_payout_report;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();

    }

    private void initUI() {


        viewPager =  findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        mTabAdaptor = new TabAdapter(getSupportFragmentManager());
        settlementReportFragment=new SettlementReportFragment();
//        settlementReportFragment.setFilterClick(this);
        payoutReportFragment=new PayoutReportFragment();
//        payoutReportFragment.setFilterClick(this);
        mTabAdaptor.addFragment(payoutReportFragment, "Payout");
        mTabAdaptor.addFragment(settlementReportFragment, "Settlement");
        viewPager.setAdapter(mTabAdaptor);
        tabLayout.setupWithViewPager(viewPager);
        mLLStartDate =findViewById(R.id.llStartDateContainer);
        mLLStartDate.setOnClickListener(this);
        mLLEndDate =findViewById(R.id.llEndDateContainer);
        mLLEndDate.setOnClickListener(this);

        mTVStartDate =findViewById(R.id.tvStartDate);
        mTVEndDate =findViewById(R.id.tvEndDate);
        mBtnView=findViewById(R.id.btnView);
        mBtnView.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.llStartDateContainer:
                showDatePicker(this);
                //TODO end date validation
                break;


            case R.id.llEndDateContainer:
                showDatePicker(this);
                break;

            case R.id.btnView:
              //  showProgressDialog();
               // getAllItemHistory();
                String startDate = mTVStartDate.getText()!=null && !TextUtils.isEmpty(mTVStartDate.getText().toString()) ? mTVStartDate.getText().toString().trim() : "2019-02-21";
                String endDate = mTVEndDate.getText() != null && !TextUtils.isEmpty(mTVEndDate.getText().toString())? mTVEndDate.getText().toString().trim() : "2019-03-30";
                //TODO remove this strong coupling
                settlementReportFragment.onFilterClick(startDate,endDate);
                payoutReportFragment.onFilterClick(startDate,endDate);
                break;
        }

    }

    @Override
    public void onDateSelected(DatePicker view, int day, int month, int year) {
        String date=year + "-" + month + "-" + day;
        //Todo set date
        if(isStartDate){
            mTVStartDate.setText(date);
            mLLEndDate.setClickable(true);
        }else{
            mTVEndDate.setText(date);
            mLLEndDate.setClickable(false);
        }

        isStartDate=!isStartDate;
    }


}
