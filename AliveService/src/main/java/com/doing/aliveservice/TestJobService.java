package com.doing.aliveservice;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TestJobService extends JobService {

    private static final String TAG = "TestJobService";

    public TestJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: " + params.getJobId());
        return false;
    }

    /**
     * Cancel一个Job是会被回调
     * @param params
     * @return
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.w(TAG, "onStopJob: " + params.getJobId());
        return false;
    }
}
