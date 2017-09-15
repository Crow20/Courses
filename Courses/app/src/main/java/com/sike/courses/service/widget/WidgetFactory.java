package com.sike.courses.service.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sike.courses.R;
import com.sike.courses.mvp.model.Course;

import java.util.ArrayList;

/**
 * Created by agritsenko on 15.09.2017.
 */

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    ArrayList<Course> data;
    Context context;
    int widgetID;

    WidgetFactory(Context ctx, Intent intent, ArrayList<Course> list){
        context = ctx;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        data = new ArrayList<Course>();
        data = list;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rView = new RemoteViews(context.getPackageName(),
                R.layout.item_small_width);
        rView.setTextViewText(, );
        return rView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }
}
