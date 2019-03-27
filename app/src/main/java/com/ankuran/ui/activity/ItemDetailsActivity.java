package com.ankuran.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ankuran.R;
import com.ankuran.ui.adaptar.TabAdapter;
import com.ankuran.ui.fragment.PayoutReportFragment;
import com.ankuran.ui.fragment.SettlementReportFragment;

public class ItemDetailsActivity extends BaseActivity {

    private TabAdapter mTabAdaptor;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_item_details;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();
    }

    private void initUI() {
        viewPager =  findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        mTabAdaptor = new TabAdapter(getSupportFragmentManager());
        mTabAdaptor.addFragment(new PayoutReportFragment(), "Details");
        mTabAdaptor.addFragment(new SettlementReportFragment(), "History");
        viewPager.setAdapter(mTabAdaptor);
        tabLayout.setupWithViewPager(viewPager);

    }



}
