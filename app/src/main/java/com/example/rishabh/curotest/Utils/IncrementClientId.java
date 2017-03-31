package com.example.rishabh.curotest.Utils;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rishabh on 31/03/2017.
 */

public class IncrementClientId {

  public static int getNextKey(Realm realm, Class classObject) {
    try {
      return realm.where(classObject).max("clientId").intValue() + 1;
    } catch (ArrayIndexOutOfBoundsException e) {
      return 0;
    }
  }
}
