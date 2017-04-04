package com.example.rishabh.curotest.Utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by curo on 4/4/17.
 */

public class AppUtils {
  public static Account account;

  public Account CreateSyncAccount() {
    if (account == null) {
      account = new Account(Constants.ACCOUNT, Constants.ACCOUNT_TYPE);
      AccountManager accountManager =
          (AccountManager) Application.getAppContext().getSystemService(Context.ACCOUNT_SERVICE);
      accountManager.addAccountExplicitly(account, null, null);
      ContentResolver.setIsSyncable(account, Constants.AUTHORITY, 1);
      ContentResolver.setSyncAutomatically(account, Constants.AUTHORITY, true);
      //ContentResolver.addPeriodicSync(newAccount, AUTHORITY, new Bundle(), 8640);
    }
    return account;
  }
}
