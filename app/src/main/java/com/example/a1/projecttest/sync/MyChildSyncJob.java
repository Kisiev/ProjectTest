package com.example.a1.projecttest.sync;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.annotation.NonNull;

import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.example.a1.projecttest.R;

import java.util.concurrent.TimeUnit;


public class MyChildSyncJob extends Job {
    private static final long[] VIBRATE_PATTERN = new long[]{0, 100, 100, 100};
    private static final int LED_LIGHTS_TIME_ON = 3000;
    private static final int LED_LIGHTS_TIME_OFF = 1500;
    private static final int NOTIFICATION_ID = 4005;
    private NotificationManager mNotificationManager;

    public static final String TAG = "job_demo_tag";
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_sentiment_very_satisfied_black_24dp)
                .setContentTitle(getContext().getString(R.string.app_name))
                .setContentText(getContext().getString(R.string.notification_message));
            builder.setLights(Color.BLUE, LED_LIGHTS_TIME_ON, LED_LIGHTS_TIME_OFF);
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setVibrate(VIBRATE_PATTERN);
        mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
        return Result.SUCCESS;
    }

    public static void cancelJob(int jobId) {
             JobManager.instance().cancel(jobId);
    }


    public static int schedulePeriodicJob() {
        return new JobRequest.Builder(MyChildSyncJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(1000), TimeUnit.MINUTES.toMillis(5))
                .setPersisted(true)
                .build()
                .schedule();
    }

}
