package com.sike.courses.service.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.birbit.android.jobqueue.JobManager;
import com.sike.courses.CoursesApp;
import com.sike.courses.R;
import com.sike.courses.job.http.GetDataJob;

import java.util.Arrays;

/**
 * Created by agritsenko on 15.09.2017.
 */

public class MyWidget extends AppWidgetProvider {

    final String LOG_TAG = "WidgetLogs";


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(LOG_TAG, "onEnabled");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i : appWidgetIds) {
            updateWidget(context, appWidgetManager, i);
        }
        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(LOG_TAG, "onDisabled");
    }

    void updateWidget(Context context, AppWidgetManager appWidgetManager,
                      int appWidgetId) {
        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.widget);

        setList(rv, context, appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }



    void setList(RemoteViews rv, Context context, int appWidgetId) {
        Intent adapter = new Intent(context, RemoteViewWidgetService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        rv.setRemoteAdapter(R.id.lvList, adapter);
    }
}
