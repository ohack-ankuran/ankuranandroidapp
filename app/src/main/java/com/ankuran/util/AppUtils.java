package com.ankuran.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ankuran.AppMain;
import com.ankuran.model.ItemHistoryEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppUtils {

    public static boolean isValidList(List list) {
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    public static boolean isConnectedToInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) AppMain.getAppMainContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Since NetworkInfo is null incase of no current network
        boolean isNetworkConnected = false;
        if (networkInfo != null) {
            isNetworkConnected = networkInfo.isConnected();
        }
        return isNetworkConnected;
    }


    public static String getCurrentDate(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String currentDate=dateFormat.format(currentTime);
        return currentDate;
    }

    public static String getReadableDate(String dateInString){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        SimpleDateFormat formatterOut = new SimpleDateFormat("dd MMM yyyy");
        try {

            Date date = formatter.parse(dateInString);
            String readableDate=formatterOut.format(date);
            return  readableDate;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //TODO change this
        return "21 Mar 2019";
    }

    public static ItemHistoryEnum.HistoryReason getHistoryReason(String reason){
        if(reason.equalsIgnoreCase("New Production")){
            return ItemHistoryEnum.HistoryReason.STOCK_NEW;
            }else if(reason.equalsIgnoreCase("Stock Transfer Reversal")){
            return ItemHistoryEnum.HistoryReason.STOCK_TRANSFER_REVERSAL;
        }else if(reason.equalsIgnoreCase("Stock Transfer")){
            return ItemHistoryEnum.HistoryReason.STOCK_TRANSFER;
        }else if(reason.equalsIgnoreCase("Sale")){
            return ItemHistoryEnum.HistoryReason.SALE;
        }else{
            return ItemHistoryEnum.HistoryReason.OTHERS;
        }
    }
}
