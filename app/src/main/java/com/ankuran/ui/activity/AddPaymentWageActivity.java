package com.ankuran.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankuran.AppConstant;
import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Activity;
import com.ankuran.model.ActivityDetails;
import com.ankuran.model.DueDetail;
import com.ankuran.model.Employee;
import com.ankuran.model.EmployeeActivityEnum;
import com.ankuran.model.Item;
import com.ankuran.model.PaymentDetails;
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

public class AddPaymentWageActivity extends BaseActivity implements View.OnClickListener, DialogInterface.OnClickListener,AdapterView.OnItemSelectedListener {

    LinearLayout llAddWageContainer, llAddPayoutContainer;
    TextView addWage,addPayment;
    Intent intent;
    AppConstant.ACTIVITY_TYPE activityType;

    EditText mEtPaymentDate,mEtWageDate,mEtPayment,mEtWageAmount,mEtWageFullAmount;
    Button mBtnAddPayment,mBtnAddWage;

    AppCompatSpinner mQuantitySpinner;
    Employee currentEmployee;

    TextView txtName,txtMobile,txtDate,txtDueAmount;
    int quantity=1;



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
        mEtWageAmount=findViewById(R.id.etWageAmount);
        mEtWageFullAmount=findViewById(R.id.etWageFullAmount);
        mBtnAddPayment=findViewById(R.id.btnAddPayment);
        mBtnAddPayment.setOnClickListener(this);
        mBtnAddWage=findViewById(R.id.btnAddWage);
        mBtnAddWage.setOnClickListener(this);

        txtName =findViewById(R.id.txtName);
        txtMobile =findViewById(R.id.txtMobile);
        txtDate =findViewById(R.id.txtDate);
        txtDueAmount =findViewById(R.id.txtDueAmount);

        hideKeyboard();

        setCurrentDate();
        setNumberSpinner();
        intent = getIntent();
        if (intent != null) {
            activityType = (AppConstant.ACTIVITY_TYPE) intent.getSerializableExtra(AppConstant.KEY_ACTIVITY_TYPE);
            currentEmployee = (Employee) intent.getSerializableExtra(AppConstant.KEY_CURRENT_EMPLOYEE);
            if (activityType.equals(AppConstant.ACTIVITY_TYPE.DUE)) {
                showDuesScreen();

            } else {
                showPaymentScreen();
            }
        }

        mEtWageAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(
                    CharSequence charSequence,
                    int i, // Start
                    int i1, // Before
                    int i2 // Count
            )
            {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String amount= mEtWageAmount.getText() != null ? mEtWageAmount.getText().toString().trim() : "";
                if(!TextUtils.isEmpty(amount)){
                    Double fullAmount= Double.valueOf(amount)*quantity;
                    mEtWageFullAmount.setText(String.valueOf(fullAmount));
                }
            }
        });
    }

    private void setNumberSpinner() {
        mQuantitySpinner=findViewById(R.id.npWageQuantity);
        Integer[] items = new Integer[50];
        for (int i=0;i<50;i++){
            items[i]=i;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        mQuantitySpinner.setOnItemSelectedListener(this);
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
        setEmployeeProfile(currentEmployee);
    }

    private void showDuesScreen() {
        AddPaymentWageActivity.this.setTitle("Add Wages");
        llAddWageContainer.setVisibility(View.VISIBLE);
        llAddPayoutContainer.setVisibility(View.GONE);
        setEmployeeProfile(currentEmployee);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddPayment:
                     callAddPaymentApi();
                break;

            case R.id.btnAddWage:
                  callAddWagesApi();
                break;

            case R.id.tvAddWage:
                showDuesScreen();
                break;

            case R.id.tvAddPayout:
                showPaymentScreen();
                break;


        }
    }

    private void setEmployeeProfile(Employee employee) {
        if(employee!=null){
            if(!TextUtils.isEmpty(employee.getFullName())){
                txtName.setText(employee.getFullName());
            }else{
                txtName.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(employee.getFullName())){
                txtName.setText(employee.getFullName());
            }else{
                txtName.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(employee.getMobile())){
                txtMobile.setText(employee.getMobile());
            }else{
                txtMobile.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(employee.getTimeOfJoining()))
                txtDate.setText("Date Added "+AppUtils.getReadableDate(employee.getTimeOfJoining()));
            else
                txtDate.setVisibility(View.GONE);

            txtDueAmount.setText("Due amount "+getString(R.string.Rs)+" "+employee.getOutstandingDue());
        }else{
            finish();
        }
    }


    private void callAddPaymentApi(){
        //TODO add input from ui
        String amount= mEtPayment.getText() != null ? mEtPayment.getText().toString().trim() : "";
        if(TextUtils.isEmpty(amount))
            showToast("Please enter payment amount");

        if (!AppUtils.isConnectedToInternet()) {
            showToast(getString(R.string.network_error));
            return;
        }

        ActivityDetails activityDetails =new ActivityDetails();
        activityDetails.setTimeCreated(AppUtils.getCurrentDate());
        activityDetails.setType(EmployeeActivityEnum.ActivityType.PAYMENT);
        PaymentDetails paymentDetails =new PaymentDetails();
        paymentDetails.setAmount(Double.valueOf(amount));
        activityDetails.setPaymentDetails(paymentDetails);
        addEmployee(activityDetails);
    }

    private void callAddWagesApi(){
        //TODO add input from ui
        String amount= mEtWageAmount.getText() != null ? mEtWageAmount.getText().toString().trim() : "";
        String fullAmount= mEtWageFullAmount.getText() != null ? mEtWageFullAmount.getText().toString().trim() : "";
        if(TextUtils.isEmpty(amount))
            showToast("Please enter amount");

        if (!AppUtils.isConnectedToInternet()) {
            showToast(getString(R.string.network_error));
            return;
        }
        ActivityDetails activityDetails =new ActivityDetails();
        activityDetails.setTimeCreated(AppUtils.getCurrentDate());
        activityDetails.setType(EmployeeActivityEnum.ActivityType.DUE);
        DueDetail dueDetail =new DueDetail();
        dueDetail.setDistributionType(EmployeeActivityEnum.DueDistributionType.INDIVIDUAL.toString());
        Item item= new Item();
        item.setId(102);
        item.setName("Green Apron");
        dueDetail.setItem(item);
        dueDetail.setQuantity(quantity);
        dueDetail.setDuePerItem(Double.valueOf(amount));
        dueDetail.setAmount(Double.valueOf(fullAmount));
        activityDetails.setDueDetails(dueDetail);
        addEmployee(activityDetails);

    }

//    private void performServerCall(){
//        if (activityType.equals(AppConstant.ACTIVITY_TYPE.DUE)) {
//           callAddWagesApi();
//
//        } else {
//           callAddPaymentApi();
//        }
//    }



    private void addEmployee(ActivityDetails activityDetails) {
        AppMain.getDefaultNetWorkClient().addActivity(currentEmployee.getId(),activityDetails).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                LogUtils.debug("Network call OnResponse post call",new Gson().toJson(response));
                processServerResponse(response);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure post call",new Gson().toJson(call));
            }
        });
    }

    private void processServerResponse(Response<JsonObject> response) {
        String operation ="Payment";
        if(activityType.equals(AppConstant.ACTIVITY_TYPE.DUE))
            operation="Due";
        if(response.code() == HttpsURLConnection.HTTP_OK ||response.code() == HttpsURLConnection.HTTP_CREATED||response.code() == HttpsURLConnection.HTTP_ACCEPTED){
            Log.d("Shikha",new Gson().toJson(response.body()));
            showInfoDialog("", operation+"added successfully!!",this);

        }else{
            Log.d("Shikha","not 200"+new Gson().toJson(response));
            showInfoDialog("","Error occurred while adding "+operation+".Please try again later!!",this);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int i) {
        dialog.dismiss();
        clearViews();
        finish();

    }

    private void clearViews() {
        mEtPayment.setText("");
        mEtWageAmount.setText("");
        mEtWageFullAmount.setText("");
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        quantity = (int) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
