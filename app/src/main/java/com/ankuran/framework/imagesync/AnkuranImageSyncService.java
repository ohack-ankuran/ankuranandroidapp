package com.ankuran.framework.imagesync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ankuran.util.LogUtils;

public class AnkuranImageSyncService extends Service {

    private static final String TAG = AnkuranImageSyncService.class.getSimpleName();

    // Storage for an instance of the sync adapter
    private static AnkuranImageSyncAdapter sSyncAdapter = null;

    // Object to use as a thread-safe lock
    private static final Object sSyncAdapterLock = new Object();

    /*
     * Instantiate the sync adapter object.
     */
    @Override
    public void onCreate() {
        /*
         * Create the sync adapter as a singleton.
         * Set the sync adapter as syncable
         * Disallow parallel syncs
         */
        LogUtils.debug(TAG, "Inside  onCreate");
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new AnkuranImageSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    /**
     * Return an object that allows the system to invoke
     * the sync adapter.
     */
    @Override
    public IBinder onBind(Intent intent) {
        /*
         * Get the object that allows external processes
         * to call onPerformSync(). The object is created
         * in the base class code when the SyncAdapter
         * constructors call super()
         */
        LogUtils.debug(TAG, "Inside  onBind");
        return sSyncAdapter.getSyncAdapterBinder();
    }


    @Override
    public void onDestroy() {
        LogUtils.debug(TAG, "Inside  onDestroyed");
    }
}
