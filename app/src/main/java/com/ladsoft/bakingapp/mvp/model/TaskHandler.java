package com.ladsoft.bakingapp.mvp.model;

import android.os.Handler;
import android.os.HandlerThread;

public class TaskHandler extends HandlerThread {
    private static final String TAG = TaskHandler.class.getSimpleName();

    private Handler workerHandler;

    public TaskHandler() {
        super(TAG);
    }

    public void prepareWorkerHandler() {
        workerHandler = new Handler(getLooper());
    }

    public void postTask(Runnable task) {
        workerHandler.post(task);
    }
}
