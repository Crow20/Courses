package com.sike.courses.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.TimeUnit;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.sike.courses.CoursesApp;
import com.sike.courses.R;
import com.sike.courses.activity.MyAdapter;
import com.sike.courses.job.http.GetDataJob;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


/**
 * Created by agritsenko on 13.07.2017.
 */



public class MyService extends Service {

    protected JobManager jobManager;
    final String LOG_TAG = "ServiceLogs";
    MyBinder binder = new MyBinder();
    Timer timer;
    TimerTask tTask;
    long interval = 1000;
    protected SharedPreferences sp;

    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        schedule();
        //Log.d(LOG_TAG, "MyService onCreate");
        Log.d(LOG_TAG, "Создался");
    }

    void schedule() {
        if (tTask != null) tTask.cancel();
        if(timer == null) timer = new Timer();
        if (interval > 0) {
            tTask = new TimerTask() {
                public void run() {
                    jobManager = CoursesApp.getInstance().getJobManager();
                    jobManager.addJobInBackground(new GetDataJob());
                    Log.d(LOG_TAG, "run time: "+interval/1000);
                }
            };
            timer.schedule(tTask, 1000, interval);
        }
    }

    public void setInterval(int gap) {
        interval = interval * gap;
        schedule();
    }

    public void stopUpdate(){
        timer.cancel();
        timer = null;
    }

    public IBinder onBind(Intent arg0) {
        Log.d(LOG_TAG, "MyService onBind");
        return binder;
    }

    public class MyBinder extends Binder {
       public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "Уничтожился");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
}
