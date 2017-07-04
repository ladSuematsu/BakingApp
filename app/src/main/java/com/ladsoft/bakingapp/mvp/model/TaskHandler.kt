package com.ladsoft.bakingapp.mvp.model


import android.os.Handler
import android.os.HandlerThread

class TaskHandler : HandlerThread(TAG) {

    private var workerHandler: Handler? = null

    fun prepareWorkerHandler() {
        workerHandler = Handler(looper)
    }

    fun postTask(task: Runnable) {
        workerHandler?.post(task)
    }

    companion object {
        private val TAG = TaskHandler::class.java.simpleName
    }
}