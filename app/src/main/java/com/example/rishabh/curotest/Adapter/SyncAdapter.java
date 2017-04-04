package com.example.rishabh.curotest.Adapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import com.example.rishabh.curotest.BodyRequest.LogValuesData;
import com.example.rishabh.curotest.Interfaces.LogScheduleCallback;
import com.example.rishabh.curotest.Model.BgLogs;
import com.example.rishabh.curotest.Model.BgSchedule;
import com.example.rishabh.curotest.SyncDataWithApi.SyncBgLogging;
import com.example.rishabh.curotest.Utils.AppDateHelper;
import com.example.rishabh.curotest.Utils.Constants;
import com.example.rishabh.curotest.activities.LauncherActivity;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
      if (realmResults.size()>0) {
        for (int i = 0; i < realmResults.size(); i++) {
          if (i==5){
            break;
          }
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
        SyncBgLogging.postBgValues(arrayList, "bulk", null);
      }else {
        SyncBgLogging.postBgValues(arrayList, "bulk", null);
      }
    } finally {
      realm.close();
    }
  }

  public static void syncBgSchedule() {
    Realm realm = Realm.getDefaultInstance();
    try {
      JSONArray jsonArray = new JSONArray();
      JSONObject jsonObject = null;
      RealmResults<BgSchedule> realmResults =
          realm.where(BgSchedule.class).equalTo("isSynced", false).findAll();
      if (realmResults.size()>0) {
        for (int i = 0; i < realmResults.size(); i++) {
          if (i==5){
            break;
          }
          jsonObject = new JSONObject();
          try {
            jsonObject.put("client_id", realmResults.get(i).getClientId());
            jsonObject.put("timeslot_id", realmResults.get(i).getTimeSlotId());
            jsonObject.put("start_date", AppDateHelper.getStrigDateFromMillis(realmResults.get(i).getStartDate()));
            if (realmResults.get(i).getEndDate() == 0) {
              jsonObject.put("end_date", null);
              jsonObject.put("is_deleted", false);
            } else {
              jsonObject.put("end_date", AppDateHelper.getStrigDateFromMillis(realmResults.get(i).getEndDate()));
              jsonObject.put("is_deleted", true);
            }
            if (realmResults.get(i).getServerId() == 0) {
              jsonObject.put("server_id", null);
            } else {
              jsonObject.put("server_id", realmResults.get(i).getServerId());
            }
            jsonArray.put(jsonObject);
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
        SyncBgLogging.postBgSchedule("bulk", jsonArray, "blood_glucose", null);
      }else {
        SyncBgLogging.postBgSchedule("bulk", jsonArray, "blood_glucose", null);
      }
    } finally {
      realm.close();
    }
  }
}
