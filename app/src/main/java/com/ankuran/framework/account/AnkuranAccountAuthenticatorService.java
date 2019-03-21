package com.ankuran.framework.account;

import android.accounts.Account;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ankuran.util.LogUtils;

/**
 * Created by Shikha on 27/02/19.
 */
public class AnkuranAccountAuthenticatorService extends Service {

    private static final String TAG = AnkuranAccountAuthenticatorService.class.getSimpleName();
    private AnkuranAccountAuthenticator mAuthenticator;

    public static Account getAccount(String accountName, String accountType) {
        LogUtils.debug(TAG, "Inside  getAccount");
        return new Account(accountName, accountType);
    }

    @Override
    public void onCreate() {
        LogUtils.debug(TAG, "Inside  onCreated");
        mAuthenticator = new AnkuranAccountAuthenticator(this);
    }

    @Override
    public void onDestroy() {
        LogUtils.debug(TAG, "Inside  onDestroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.debug(TAG, "Inside  onBind");
        return mAuthenticator.getIBinder();
    }
}
