package com.example.rishabh.curotest.helpers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by rishabh on 04/04/2017.
 */

public class AuthenticatorService extends Service {
  // Instance field that stores the authenticator object
  private Authenticator mAuthenticator;
  @Override
  public void onCreate() {
    // Create a new authenticator object
    mAuthenticator = new Authenticator(this);
  }
  /*
   * When the system binds to this Service to make the RPC call
   * return the authenticator's IBinder.
   */
  @Override
  public IBinder onBind(Intent intent) {
    return mAuthenticator.getIBinder();
  }
}
