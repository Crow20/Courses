package com.sike.courses.service.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.birbit.android.jobqueue.JobManager;
import com.sike.courses.CoursesApp;
import com.sike.courses.api.response.RequestResult;
import com.sike.courses.event.httpEvent.BaseHttpEvent;
import com.sike.courses.job.http.GetDataJob;
import com.sike.courses.mvp.model.Course;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by agritsenko on 15.09.2017.
 */

public class RemoteViewWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetFactory(getApplicationContext(), intent);
    }



}


