package com.example.rishabh.curotest.DBO;

import com.example.rishabh.curotest.Model.LogStreakPerDay;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rishabh on 27/03/2017.
 */

public class LogStreakDBO {

  public int getLogStreakStatus(long date, Realm realm) {
    int streakStatus = 2;
    RealmResults<LogStreakPerDay> realmResults1 =
        realm.where(LogStreakPerDay.class).findAll();
    RealmResults<LogStreakPerDay> realmResults =
        realm.where(LogStreakPerDay.class).equalTo("date", date).findAll();
    if (realmResults.size() == 0) {
      return streakStatus;
    } else {
      streakStatus = realmResults.get(0).getStatusFlag();
      return streakStatus;
    }
  }


}
