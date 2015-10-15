package com.ravikwow.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ravikwow
 * Date: 15.10.15
 */
public class TestService extends Service {

    private static final String TAG = TestService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        int duration = intent.getIntExtra("duration", 0);
        new AsyncTask<Integer, Void, Integer>() {
            @Override
            protected Integer doInBackground(Integer... params) {
                int startId = params[0];
                int duration = params[1];
                Log.d(TAG, "start " + startId + ", duration: " + duration);
                try {
                    Thread.sleep(duration * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return startId;
            }

            @Override
            protected void onPostExecute(Integer startId) {
                Log.d(TAG, "stop " + startId);
                stopSelf(startId);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, startId, duration);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
