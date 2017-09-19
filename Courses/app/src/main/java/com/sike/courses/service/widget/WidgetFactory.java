package com.sike.courses.service.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sike.courses.R;
import com.sike.courses.Singleton;
import com.sike.courses.mvp.model.Course;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by agritsenko on 15.09.2017.
 */

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    ArrayList<Course> data;
    Context context;
    int widgetID;

    WidgetFactory(Context ctx, Intent intent){
        context = ctx;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        data = new ArrayList<>();
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
        Log.d("WidgetFactory", "row="+position);
        RemoteViews rView = new RemoteViews(context.getPackageName(),
                R.layout.widget_item);
//        if(data.get(position).getSubname().equals())
//        if(data.size() > 24) data.removeAll(data.subList(23, data.size()-1));
        if(!data.get(position).isSubnameNull(data.get(position))){
            if(data.get(position).getName().equals("USD")){
                rView.setTextViewText(R.id.col1, data.get(position).getSubname());
            }
            rView.setTextViewText(R.id.col2, data.get(position).getName());
            rView.setTextViewText(R.id.col3, data.get(position).getDate());
            rView.setTextViewText(R.id.col4, data.get(position).getValue1());
            rView.setTextViewText(R.id.col5, data.get(position).getValue2());
        }else {
            rView.setTextViewText(R.id.col1, data.get(position).getName());
            rView.setTextViewText(R.id.col3, data.get(position).getDate());
            rView.setTextViewText(R.id.col4, data.get(position).getValue1());
            rView.setTextViewText(R.id.col5, data.get(position).getValue2());
        }

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
        HashMap<Object, ArrayList<Course>> list = Singleton.getInstance().list;
        if(list != null) {
            data = list.get("cash");
            data.addAll(data.size(), list.get("currency"));
            data.addAll(data.size(), list.get("indices"));
        }
    }



    @Override
    public void onDestroy() {
        data.clear();
    }
}
