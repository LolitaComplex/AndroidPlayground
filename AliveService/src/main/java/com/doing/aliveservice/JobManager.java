package com.doing.aliveservice;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class JobManager {

    public static final int JOB_ID = 33;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void schedule(Context context) {
        ComponentName component = new ComponentName(context, TestJobService.class);

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, component);
        //充电时就去做
        //builder.setRequiresCharging(true);
        //联网时就可以执行任务
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPeriodic(5000);
        JobScheduler scheduler = (JobScheduler) context.getSystemService(
                Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(builder.build());
    }
}
