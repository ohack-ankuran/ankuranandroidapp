package com.ankuran.framework.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;

import com.ankuran.AppConstant;
import com.ankuran.AppMain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shikha on 27/02/19.
 */
public class AnkuranAccountUtils {
    private static final String TAG = AnkuranAccountUtils.class.getSimpleName();

    /**
     * Function to if any account exits or not corresponding to  given account type

     */
    public static Account getExistingAccount() {
        AccountManager manager = AccountManager.get(AppMain.getAppMainContext());
        String accountType = AppConstant.ANKURAN_ACCOUNT_TYPE;
        Account[] accounts = manager.getAccountsByType(accountType);
        if (accounts == null || accounts.length == 0)
            return null;
        return accounts != null && accounts.length > 0 ? accounts[0] : null;
    }

    /**
     * Function to get all the password accessible accounts associated with given account type
     *
     * @param manager
     * @param candidates
     */
    private static Account[] getPasswordAccessibleAccounts(
            final AccountManager manager, final Account[] candidates)
            throws Exception {
        final List<Account> accessible = new ArrayList<Account>(
                candidates.length);
        boolean exceptionThrown = false;
        for (Account account : candidates)
            try {
                manager.getPassword(account);
                accessible.add(account);
            } catch (SecurityException ignored) {
                exceptionThrown = true;
            }
        if (accessible.isEmpty() && exceptionThrown)
            throw new Exception();
        return accessible.toArray(new Account[accessible.size()]);
    }


    /**
     * Function to get all the accounts associated with given account type
     * Do'nt use this method inside main thread
     *
     * @param manager
     */
    public static Account[] getAccounts(final AccountManager manager)
            throws Exception {
        String accountType = AppConstant.ANKURAN_ACCOUNT_TYPE;
        final AccountManagerFuture<Account[]> future = manager
                .getAccountsByTypeAndFeatures(accountType, null, null, null);
        final Account[] accounts = future.getResult();
        if (accounts != null && accounts.length > 0)
            return getPasswordAccessibleAccounts(manager, accounts);
        else
            return new Account[0];
    }

    /**
     * Function to create new account
     *
     * @param context
     * @param accountName
     */
    public static Account createAccount(Context context, String accountName) throws Exception {
        String accountType = AppConstant.ANKURAN_ACCOUNT_TYPE;
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account account = AnkuranAccountAuthenticatorService.getAccount(accountName, accountType);
        accountManager.addAccountExplicitly(account, null, null);
        return account;
    }
}

