package com.ankuran;

import android.os.Environment;

import java.io.File;

public class AppConstant {
    public static final int CAMERA_REQUEST_CODE =1000 ;
    public static final String KEY_ACTIVITY_TYPE="Activity Type";
    public static final String KEY_CURRENT_EMPLOYEE="Current Employee";
    public static final String KEY_CURRENT_ITEM="Current Item";
    private static final Object MAIN_FOLDER = "Ankuran" ;

    public static final  String AWS_ACCESS_KEY = "";
    public static final  String AWS_SECRET_KEY = "";
    public static final  String AWS_IMAGES_BUCKET_NAME = "ankuran-images";
    public static final  String AWS_TEST_FOLDER="test_uploads/";
    public static final  String AWS_ANKURAN_FOLDER="ankuran/";

    public static final String BASE_FOLDER = Environment.getExternalStorageDirectory() + File.separator + MAIN_FOLDER + File.separator;



    public static final String ANKURAN_ACCOUNT_TYPE ="com.ankuran.framework.account";
    public static final String ANKURAN_CONTENT_AUTHORITY ="com.ankuran.framework.imagesync";

    public enum ACTIVITY_TYPE{
        DUE(1),
        PAYOUT(2);

        private final int type;

        ACTIVITY_TYPE(final int type)
        {
            this.type=type;
        }
    }





}
