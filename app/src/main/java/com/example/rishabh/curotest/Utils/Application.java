package com.example.rishabh.curotest.Utils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by rishabh on 27/03/2017.
 */

public class Application extends android.app.Application {
  static android.app.Application application;

  @Override public void onCreate() {
    application = this;
    super.onCreate();
    // The Realm file will be located in Context.getFilesDir() with name "default.realm"
    Realm.init(this);
    RealmConfiguration config = new RealmConfiguration.Builder().build();
    Realm.setDefaultConfiguration(config);
  }

  public static android.app.Application getAppContext() {
    return application;
  }
}
