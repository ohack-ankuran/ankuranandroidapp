package com.ankuran.ui.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Employee;
import com.ankuran.model.GroupWageEmployee;
import com.ankuran.model.dao.EmployeeList;
import com.ankuran.ui.adaptar.GroupWagesRecyclerViewAdapter;
import com.ankuran.ui.adaptar.listener.OnRecyclerItemClickListener;
import com.ankuran.ui.fragment.AddItemToGroupWagesFragment;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupWagesActivity extends BaseActivity implements OnRecyclerItemClickListener, View.OnClickListener, DialogInterface.OnClickListener {

    IndexFastScrollRecyclerView mRecyclerView;
    GroupWagesRecyclerViewAdapter mAdapter;
    Button addEmployeesToGroupWages;
    CoordinatorLayout coordinatorLayout;
    List<GroupWageEmployee> mEmployees;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_group_wages;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();
//        addDummyDataToPref();
        getAllEmployee();
    }

    private void initUI() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(R.string.btxt_group_wage);
//        toolbar.setNavigationIcon(R.drawable.back_arrow);
        mEmployees = new ArrayList<>();
        addEmployeesToGroupWages = findViewById(R.id.btnAddToGroupWages);
        addEmployeesToGroupWages.setOnClickListener(this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .activityGroupWagesLayout);
        mRecyclerView = findViewById(R.id.group_wages_recycler);
        mAdapter = new GroupWagesRecyclerViewAdapter(mEmployees, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setIndexbarHighLateTextColor("#020202");
        mRecyclerView.setIndexBarHighLateTextVisibility(true);
        mRecyclerView.setIndexBarColor("#FFFFFF");
        mRecyclerView.setIndexBarTextColor("#888383");
        mRecyclerView.setIndexTextSize(30);
        mRecyclerView.setPreviewVisibility(true);
        mRecyclerView.setIndexbarWidth(50);
        mRecyclerView.setPreviewPadding(10);
        mRecyclerView.setIndexbarMargin(10);
    }

    private void getAllEmployee() {
        Log.d(TAG, "getAllEmployee");

        AppMain.getDefaultNetWorkClient().allEmployee().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    //TODO put validation check
                    Log.d("Shikha", new Gson().toJson(response.body()));
                    EmployeeList list = new Gson().fromJson(response.body(), EmployeeList.class);
                    List<Employee> mEmpList = list.getEmployees();
                    for (Employee e : mEmpList) {
                        mEmployees.add(new GroupWageEmployee(e, false, Double.valueOf(0)));
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    Log.d("Shikha", "not 200" + new Gson().toJson(response));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure get callv", new Gson().toJson(call));
            }
        });

    }

    private void addDummyDataToPref() {
        String workerList = "{\"employees\":[{\"id\":234567898766,\"fullName\":\"Anita Jain\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"bbbbbbbbbbbjnjjkk\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"bbbbbbbbbbbbblkjk\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"bbbbbbbbbbdjfj\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"ccccbbbbbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"ccccbbljlbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"dddccccbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"eeeccccbbbbbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"ffffccccbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"ggsrgtfrccccbbbbbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"hjhjccccbbbbbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898766,\"fullName\":\"iohjccccbbbbbbbbbbbbb\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-05T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Jain\",\"bslEmployeeId\":\"BSL-236799835\"},\"centre\":1,\"outstandingDue\":900},{\"id\":234567898768,\"fullName\":\"Kanta Singh\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-02-03T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Singh\",\"bslEmployeeId\":\"BSL-236799837\"},\"centre\":1,\"outstandingDue\":-500},{\"id\":234567898769,\"fullName\":\"Ramakanta Sharma\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-02-03T00:15:21.030Z\",\"active\":true,\"centre\":1,\"outstandingDue\":0},{\"id\":234567898765,\"fullName\":\"Sunita Singh\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-01T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Shyam Singh\",\"bslEmployeeId\":\"BSL-236799834\"},\"centre\":1,\"outstandingDue\":2356},{\"id\":234567898767,\"fullName\":\"Sushila Verma\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-01-07T00:15:21.030Z\",\"active\":true,\"husband\":{\"fullName\":\"Mr Verma\",\"bslEmployeeId\":\"BSL-236799836\"},\"centre\":1,\"outstandingDue\":1500},{\"id\":234567898770,\"fullName\":\"Stuti Mishra\",\"mobile\":\"9880199911\",\"timeOfJoining\":\"2019-02-03T00:15:21.030Z\",\"active\":false,\"husband\":{\"fullName\":\"Mr Singh\"},\"centre\":1,\"outstandingDue\":300}]}";
        EmployeeList list = new Gson().fromJson(workerList, EmployeeList.class);
        List<Employee> mEmpList = list.getEmployees();
        for (Employee e : mEmpList) {
            mEmployees.add(new GroupWageEmployee(e, false, Double.valueOf(0)));
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemClick(View view, int position, Object object) {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToGroupWages:
                mAdapter.notifyDataSetChanged();
                //TODO pass worker data
                if (mAdapter.getSelectedEmployees().size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("selected_employees", (ArrayList<GroupWageEmployee>) mAdapter.getSelectedEmployees());
                    AddItemToGroupWagesFragment fragment = new AddItemToGroupWagesFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    View view = findViewById(R.id.activityGroupWages);
                    view.setVisibility(View.VISIBLE);
                    transaction.replace(R.id.activityGroupWages, fragment);
                    transaction.commit();
                } else {
                    showInfoDialog("", "Select Employees to add group wages", this);
                }
                break;
        }

    }

  @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }
}
