package com.ankuran.framework.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

import com.ankuran.util.LogUtils;

/**
 * Created by Shikha on 27/02/19.
 */
public class AnkuranAccountAuthenticator extends AbstractAccountAuthenticator {

    private static final String TAG=AnkuranAccountAuthenticator.class.getSimpleName();

    public AnkuranAccountAuthenticator(Context context) {
        super(context);
        LogUtils.debug(TAG,"Inside AccountAuthenticator Constructor");
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse,
                                 String s) {
        LogUtils.debug(TAG,"Inside editProperties");
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse,
                             String s, String s2, String[] strings, Bundle bundle)
            throws NetworkErrorException {
        LogUtils.debug(TAG,"Inside addAccount");
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
                                     Account account, Bundle bundle)
            throws NetworkErrorException {
        LogUtils.debug(TAG,"Inside confirmCredentials");
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse,
                               Account account, String s, Bundle bundle)
            throws NetworkErrorException {
        LogUtils.debug(TAG,"Inside getAuthToken");
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAuthTokenLabel(String s) {
        LogUtils.debug(TAG,"Inside getAuthTokenLabel");
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
                                    Account account, String s, Bundle bundle)
            throws NetworkErrorException {
        LogUtils.debug(TAG,"Inside updateCredentials");
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse,
                              Account account, String[] strings)
            throws NetworkErrorException {
        LogUtils.debug(TAG,"Inside hasFeatures");
        throw new UnsupportedOperationException();
    }
}

