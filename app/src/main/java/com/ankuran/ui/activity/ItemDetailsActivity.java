package com.ankuran.ui.activity;

import android.content.Intent;
import android.os.Debug;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ankuran.AppConstant;
import com.ankuran.R;
import com.ankuran.model.Employee;
import com.ankuran.model.Item;
import com.ankuran.ui.adaptar.TabAdapter;
import com.ankuran.ui.fragment.ItemDetailFragment;
import com.ankuran.ui.fragment.ItemHistoryFragment;
import com.ankuran.ui.fragment.PayoutReportFragment;
import com.ankuran.ui.fragment.SettlementReportFragment;
import com.ankuran.util.LogUtils;

public class ItemDetailsActivity extends BaseActivity {

    private TabAdapter mTabAdaptor;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    Intent intent;
    Item currentItem;

    ItemDetailFragment itemDetailFragment;
    ItemHistoryFragment itemHistoryFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_item_details;
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
                LogUtils.debug("ItemDetailsActivity","NO current item found");
                finish();

            }
            itemDetailFragment=new ItemDetailFragment();
            itemHistoryFragment=new ItemHistoryFragment();
            itemDetailFragment.setCurrentItem(currentItem);
            itemHistoryFragment.setCurrentItem(currentItem);
        }

        viewPager =  findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        mTabAdaptor = new TabAdapter(getSupportFragmentManager());
        mTabAdaptor.addFragment(itemDetailFragment, "Details");
        mTabAdaptor.addFragment(itemHistoryFragment, "History");
        viewPager.setAdapter(mTabAdaptor);
        tabLayout.setupWithViewPager(viewPager);

    }



}
