package com.example.a1.projecttest;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.evernote.android.job.JobManager;
import com.example.a1.projecttest.sync.MyChildJobCreator;
import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChildApplication extends Application{
    public static ChildApplication childApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
        JobManager.create(this).addJobCreator(new MyChildJobCreator());
        Stetho.initializeWithDefaults(this);
        childApplication = this;
    }
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    public static synchronized ChildApplication getInstance() {
        return childApplication;
    }
}
