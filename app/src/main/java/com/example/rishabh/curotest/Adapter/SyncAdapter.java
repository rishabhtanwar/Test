package com.example.rishabh.curotest.Adapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import com.example.rishabh.curotest.BodyRequest.LogValuesData;
import com.example.rishabh.curotest.Model.BgLogs;
import com.example.rishabh.curotest.SyncDataWithApi.SyncBgLogging;
import com.example.rishabh.curotest.Utils.Constants;
import io.realm.Realm;
import io.realm.RealmResults;
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
    String type = extras.getString(Constants.SYNC_DATA);
    syncOfflineData(type);
  }

  private void syncOfflineData(String type) {
    if (type.equalsIgnoreCase(Constants.BG_LOG_SYNC)) {
      syncBgLog();
    } else if (type.equalsIgnoreCase(Constants.BG_SCHEDULE_SYNC)) {
      syncBgSchedule();
    } else {
      syncBgLog();
      syncBgSchedule();
    }
  }

  private void syncBgLog() {
    Realm realm = Realm.getDefaultInstance();
    try {
      int count = 0;
      RealmResults<BgLogs> realmResults =
          realm.where(BgLogs.class).equalTo("isSynced", false).findAll();
      ArrayList<LogValuesData> arrayList = new ArrayList<>();
      for (int i = 0; i < 5; i++) {
        LogValuesData logValuesData = new LogValuesData();
        logValuesData.client_id = realmResults.get(i).getClientId();
        if (realmResults.get(i).getServerId() != 0) {
          logValuesData.server_id = realmResults.get(i).getServerId();
          logValuesData.tasktemplate_id = realmResults.get(i).getServerId();
        } else {
          logValuesData.tasktemplate_id = 0;
        }
        logValuesData.date_time = realmResults.get(i).getDateTime();
        logValuesData.logged_time = realmResults.get(i).getLoggedTime();
        logValuesData.timeslot_id = realmResults.get(i).getTimeSlotId();
        logValuesData.vitaldataattribute_id = 4;
        logValuesData.value = realmResults.get(i).getValue();
        arrayList.add(logValuesData);
      }
      SyncBgLogging.postBgValues(arrayList, "bulk");
    } finally {
      realm.close();
    }
  }

  private void syncBgSchedule() {

  }
}
