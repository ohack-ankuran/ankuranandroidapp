package com.ankuran.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import com.ankuran.R;
import com.otaliastudios.cameraview.CameraView;

public class CalculateWageActivity extends BaseActivity {

    private Button btnCapture;
    private CameraView cameraView;

    @Override
    protected int getContentViewId() {
                        return R.layout.activity_calculate_wage;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
         bindViews();

    }

    private void bindViews() {


    }




}
