package com.ankuran.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.ankuran.AppConstant;
import com.ankuran.R;
import com.ankuran.util.FileUtils;


import java.io.File;
import java.io.IOException;

import static com.ankuran.AppConstant.CAMERA_REQUEST_CODE;

public class AddNewItem extends BaseActivity implements View.OnClickListener {

    FrameLayout itemImageContainer;
    ImageView itemImage;
    ImageView cameraIcon;
    String cameraFilePath;

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

        itemImageContainer.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.itemImageContainer:
                    openCameraApp();
                break;
        }
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
