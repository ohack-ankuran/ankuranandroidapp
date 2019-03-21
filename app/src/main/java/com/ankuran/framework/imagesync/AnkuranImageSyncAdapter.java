package com.ankuran.framework.imagesync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.Environment;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.ankuran.AppConstant;
import com.ankuran.AppMain;
import com.ankuran.util.LogUtils;

import java.io.File;
import java.io.FilenameFilter;

public class AnkuranImageSyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = AnkuranImageSyncAdapter.class.toString();
    private int folderSize = 0;


    public AnkuranImageSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        LogUtils.debug(TAG, "Inside onPerformSync");
        performS3ImageSync();

    }

    private void performS3ImageSync() {
//        LogUtils.debug(TAG, "Syncing images on s3");
//
//        AWSCredentials credentials = new AWSCredentials() {
//            @Override
//            public String getAWSAccessKeyId() {
//                return AppConstant.AWS_ACCESS_KEY;
//            }
//
//            @Override
//            public String getAWSSecretKey() {
//                return AppConstant.AWS_SECRET_KEY;
//            }
//        };
//        TransferManager tx = new TransferManager(credentials);
//
//        String zipFilePath = Environment.getExternalStorageDirectory() + File.separator + AppConstant.TMS_ZIP_FOLDER;
//        File dir = new File(zipFilePath);
//        File[] directory = dir.listFiles(new FilenameFilter() {
//            @Override
//            public boolean accept(File file, String s) {
//                folderSize++;
//                return s.endsWith(ITMSConstants.ZIP);
//            }
//        });
//
//        if (directory != null) {
//            LogUtils.debug(TAG, "Folder size: " + folderSize);
//            for (File file : directory) {
////                String key = null;
////                if (ITMSConstants.isDevelopment) {
////                    key = ITMSConstants.AWS_TMS_FOLDER + ITMSConstants.AWS_TEST_FOLDER + file.getName();
////                } else {
////                    key = ITMSConstants.AWS_TMS_FOLDER + file.getName();
////                }
////                awsUpload(tx, ITMSConstants.AWS_IMAGES_BUCKET_NAME, key, file);
////                file.delete();
//            }
//        }
//
//        tx.shutdownNow();
        LogUtils.debug(TAG, "Images Sync campaign finished");
    }


    private void awsUpload(TransferManager transferManager, String bucketName, String key, File file) {
        Upload myUpload = transferManager.upload(bucketName, key, file);
        if (myUpload.isDone() == false) {
           LogUtils.debug(TAG, "Transfer: " + myUpload.getDescription());
           LogUtils.debug(TAG, "  - State: " + myUpload.getState());
           LogUtils.debug(TAG, "  - Progress: "
                    + myUpload.getProgress().getBytesTransferred());
        }
        try {
            myUpload.waitForCompletion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

