package com.mobdev.hellolivedata.data;

import android.util.Log;
import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.logging.LogManager;

/**
 * Created by Marco Picone (picone.m@gmail.com) 20/03/2020
 * Example of Custom Live Data Class Implementation
 */
public class LogListLiveData extends LiveData<List<LogDescriptor>> {

    private static final String TAG = "LogLiveData";

    private static LogListLiveData sInstance;

    private LogManager logManager = null;

    private LogDataListener listener = new LogDataListener() {
        @Override
        public void onListChanged(List<LogDescriptor> logDescriptorList) {
            Log.d(TAG, "onListChanged called !");
            setValue(logDescriptorList);
        }
    };

    @MainThread
    public static LogListLiveData getInstance() {
        if (sInstance == null) {
            sInstance = new LogListLiveData();
        }
        return sInstance;
    }

    private LogListLiveData() {

    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive called !");
        LogDescriptorManager.getInstance().requestLogUpdates(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive called !");
        LogDescriptorManager.getInstance().removeLogUpdates(listener);
    }
}
