package com.example.a1.projecttest.sync;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class MyChildJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
                case MyChildSyncJob.TAG:
                    return new MyChildSyncJob();
                default:
                    return null;
            }

    }
}
