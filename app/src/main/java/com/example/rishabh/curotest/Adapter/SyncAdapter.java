package com.example.rishabh.curotest.Adapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import java.util.ArrayList;

/**
 * Created by curo on 4/4/17.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {
  Context mcontext;
  public static final String INITIAL_LAST_SYNC_TIME = "2015-01-01";
  private static final String TAG = "CURO";
  private ArrayList<Object> arrayListTableNames;
  private static String[] dateArray;
  private static int dateIndex = 0;
  private boolean isDiscoveryCalled = false;
  private volatile boolean isSyncCleanUpdated = false;

  public SyncAdapter(Context context, boolean autoInitialize) {
    super(context, autoInitialize);
    mcontext = context;
  }

  @Override public void onPerformSync(Account account, Bundle extras, String authority,
      ContentProviderClient contentProviderClient, SyncResult syncResult) {
      syncOfflineData();
  }

  private void syncOfflineData() {

  }
}
