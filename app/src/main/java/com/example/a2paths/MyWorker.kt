package com.example.a2paths

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(appContext: Context, workerParameters: WorkerParameters) : Worker(appContext, workerParameters) {

    override fun doWork(): ListenableWorker.Result {
        Log.d(TAG, "Performing long running task in scheduled job")
        return ListenableWorker.Result.success()
    }

    companion object {
        private const val TAG = "MyWorker"
    }
}