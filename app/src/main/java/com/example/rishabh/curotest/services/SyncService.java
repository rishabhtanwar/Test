package com.example.rishabh.curotest.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.example.rishabh.curotest.Adapter.SyncAdapter;

/**
 * Created by deepak on 22/6/16.
 */
public class SyncService extends Service {

    private static SyncAdapter sSyncAdapter = null;
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }
}
