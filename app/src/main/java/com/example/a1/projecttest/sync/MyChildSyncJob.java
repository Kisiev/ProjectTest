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
import com.example.a1.projecttest.FeedEntity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.rest.Models.GetAllKidsModel;
import com.example.a1.projecttest.rest.Models.GetStatusKidModel;
import com.example.a1.projecttest.rest.RestService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MyChildSyncJob extends Job {
    private static final long[] VIBRATE_PATTERN = new long[]{0, 100, 100, 100};
    private static final int LED_LIGHTS_TIME_ON = 3000;
    private static final int LED_LIGHTS_TIME_OFF = 1500;
    private static final int NOTIFICATION_ID = 4005;
    private NotificationManager mNotificationManager;
    List<GetStatusKidModel> getStatusKidModels;
    List<GetStatusKidModel> getAllKidStatuses;
    List<GetAllKidsModel> getAllKidsModels;
    List<FeedEntity> getStatusesOnBase;


    public static final String TAG = "job_demo_tag";
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        beginThread();
        Log.d("НАЧАЛООО", " dfdf fds fds fds fds fsdfsd ");
        return Result.SUCCESS;
    }

    public void beginThread() {
        RestService restService = new RestService();
        UserLoginSession userLoginSession = new UserLoginSession(getContext());
        getAllKidStatuses = new ArrayList<>();
        try {
            getAllKidsModels = restService.getKidByParentId(userLoginSession.getID());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (getAllKidsModels != null)
            for (int i = 0; i < getAllKidsModels.size(); i++) {
                try {
                    getStatusKidModels = restService.getStatusKidForFeedModels(getAllKidsModels.get(i).getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (getStatusKidModels != null)
                    for (int j = 0; j < getStatusKidModels.size(); j++) {
                        getAllKidStatuses.add(getStatusKidModels.get(j));
                    }
            }
        if (getAllKidStatuses != null) {
            List<FeedEntity> getBaseFeed = FeedEntity.selectAllNotification();
            if (getAllKidStatuses.size() != getBaseFeed.size()) {
                sendNotification();
            } else {
                for (int i = 0; i < getAllKidStatuses.size(); i++) {
                    if (!getAllKidStatuses.get(i).getUserId().equals(getBaseFeed.get(i).getUserId())
                            || (!getAllKidStatuses.get(i).getStatusId().equals(getBaseFeed.get(i).getStatusId())
                            || (!getAllKidStatuses.get(i).getComment().equals(getBaseFeed.get(i).getComment()))))
                        sendNotification();

                }
            }
        }
    }



    public void sendNotification(){
        Log.d("УВЕДОМЛЕНИЕ", "dfsfdsf dsf ");
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_sentiment_very_satisfied_black_24dp)
                .setContentTitle(getContext().getString(R.string.app_name))
                .setContentText(getContext().getString(R.string.notification_message));
        builder.setLights(Color.BLUE, LED_LIGHTS_TIME_ON, LED_LIGHTS_TIME_OFF);
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        builder.setVibrate(VIBRATE_PATTERN);
        mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void cancelJob(int jobId) {
             JobManager.instance().cancel(jobId);
    }


    public static int schedulePeriodicJob() {
        return new JobRequest.Builder(MyChildSyncJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setPersisted(true)
                .build()
                .schedule();
    }

}
