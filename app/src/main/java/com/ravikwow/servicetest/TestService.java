package com.ravikwow.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ravikwow
 * Date: 15.10.15
 */
public class TestService extends Service {
    private static final String TAG = TestService.class.getSimpleName();

    private ExecutorService mExecutorService;
    private Object mTestObject;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        mExecutorService = Executors.newFixedThreadPool(2);
        mTestObject = new Object();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        int duration = intent.getIntExtra("duration", 0);
        TestRunnable run = new TestRunnable(startId, duration);
        mExecutorService.execute(run);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        mTestObject = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class TestRunnable implements Runnable {

        int mStartId;
        int mDuration;

        public TestRunnable(int startId, int duration) {
            mStartId = startId;
            mDuration = duration;
        }

        public void run() {
            Log.d(TAG, "start TestRunnable " + mStartId + ", duration: " + mDuration);
            try {
                Thread.sleep(mDuration * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (mTestObject == null) {
                Log.d(TAG, "test res = null after service destroy");
            } else {
                Log.d(TAG, "test res alive");
            }
            Log.d(TAG, "stop TestRunnable " + mStartId);
            stopSelf(mStartId);
        }
    }
}
