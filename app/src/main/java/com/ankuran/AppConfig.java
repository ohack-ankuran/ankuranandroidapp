package com.ankuran;

import android.os.Environment;

import java.io.File;

import static com.ankuran.AppConfig.Host.STAGING;

public class AppConfig {
    private static final Host HOST_DEFAULT = STAGING;
    private static final String DEFAULT_LOG_TAG = "Ankuran";
    private static final String SHARED_PREF_NAME = "AnkuranPref";
    private static final String BASE_FOLDER_NAME = "Ankuran";
    public static final String DATABASE_NAME = "AnkuranDB";

    public enum Host{
        STAGING1("https://jsonplaceholder.typicode.com"),
        STAGING("http://ankuran-hack.ap-south-1.elasticbeanstalk.com"),
        PRODUCTION("http://ankuran-hack.ap-south-1.elasticbeanstalk.com");

        private final String url;

        private Host(final String url)
        {
            this.url=url;
        }

        @Override
        public String toString() {
            return url;
        }
    }



    public static String getHostDefault() {
        return HOST_DEFAULT.toString();
    }

    public static boolean isDevelopment() {
        if(getHostDefault().equals(Host.PRODUCTION.toString()))
            return false;
        return true;
    }

    public static boolean isLogEnabled() {
        return isDevelopment();
    }

    public static String getDefaultLogTag() {
        return DEFAULT_LOG_TAG;
    }

    public static String getSharedPrefName() {
        return SHARED_PREF_NAME;
    }

    public static String getBaseFolderPath(){
        return Environment.getExternalStorageDirectory() + File.separator + BASE_FOLDER_NAME + File.separator;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }
}
