package com.ankuran.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.ankuran.AppConstant;
import com.ankuran.AppMain;
import com.ankuran.R;
import com.ankuran.model.Employee;
import com.ankuran.model.Item;
import com.ankuran.util.AppUtils;
import com.ankuran.util.FileUtils;
import com.ankuran.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import java.io.File;
import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ankuran.AppConstant.CAMERA_REQUEST_CODE;

public class AddNewItem extends BaseActivity implements View.OnClickListener,DialogInterface.OnClickListener{

    FrameLayout itemImageContainer;
    ImageView itemImage;
    ImageView cameraIcon;
    String cameraFilePath;
    
    EditText mETName,mETType;
    Button mSave;
    
    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_new_item;
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        initUI();

    }

    private void initUI() {
        itemImageContainer=findViewById(R.id.itemImageContainer);
        itemImage=findViewById(R.id.itemImage);
        cameraIcon=findViewById(R.id.cameraIcon);
        
        mETName=findViewById(R.id.etName);
        mETType=findViewById(R.id.etType);

        itemImageContainer.setOnClickListener(this);
        
        mSave=findViewById(R.id.btnSave);
        mSave.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.itemImageContainer:
                    openCameraApp();
                break;
                
                
            case R.id.btnSave:
                performSave();
                break;
        }
    }

    private void performSave() {
        Item item = new Item();
        String fullName = mETName.getText() != null ? mETName.getText().toString().trim() : "";
        if (TextUtils.isEmpty(fullName)) {
            showToast("Please enter your name");
            return;
        }

        item.setName(fullName);
        String type = mETType.getText() != null ? mETType.getText().toString().trim() : "";
        if (!TextUtils.isEmpty(type)) {
            item.setType(type);
        } else {
            showToast("Please enter valid time");
        }
        
        item.setTimeCreated(AppUtils.getCurrentDate());

        if (!AppUtils.isConnectedToInternet()) {
            showToast(getString(R.string.network_error));
            return;
        }
        addItem(item);
           
    }



    private void addItem(Item item) {
        AppMain.getDefaultNetWorkClient().addItem(item).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                LogUtils.debug("Network call OnResponse post call",new Gson().toJson(response));
                processServerResponse(response,"creat");

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: Show retrofit error dialog
                LogUtils.debug("Network call onFailure post call",new Gson().toJson(call));
            }
        });
    }

    private void openCameraApp() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);

            File pictureFile = null;
            try {
                pictureFile = FileUtils.createImageFile(this);
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                cameraFilePath="file://"+pictureFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.ankuran.android.fileprovider",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, AppConstant.CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case CAMERA_REQUEST_CODE:
                     itemImage.setImageURI(Uri.parse(cameraFilePath));
                    break;
            }
    }

    private void processServerResponse(Response<JsonObject> response, String operation) {
        if(response.code() == HttpsURLConnection.HTTP_OK ||response.code() == HttpsURLConnection.HTTP_CREATED||response.code() == HttpsURLConnection.HTTP_ACCEPTED){
            Log.d("Shikha",new Gson().toJson(response.body()));
            showInfoDialog("", "Item added successfully!!",this);

        }else{
            Log.d("Shikha","not 200"+new Gson().toJson(response));
            showInfoDialog("","Item added successfully!!",this);
        }
    }

    private void clearViews() {
        mETName.setText("");
        mETType.setText("");
    }


    @Override
    public void onClick(DialogInterface dialog, int i) {
        dialog.dismiss();
        clearViews();
        finish();

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (resultCode == Activity.RESULT_OK)
//            switch (requestCode){
//                case CAMERA_REQUEST_CODE:
//                     itemImage.setImageURI(Uri.parse(cameraFilePath));
//                    break;
//            }
//    }
}
