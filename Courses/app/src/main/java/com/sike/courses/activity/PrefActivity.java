package com.sike.courses.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.sike.courses.R;
import com.sike.courses.service.MyService;

/**
 * Created by agritsenko on 13.07.2017.
 */

public class PrefActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    SharedPreferences sp;
    boolean bound = false;
    ServiceConnection sConn;
    Intent intent;
    MyService myService;
    final String LOG_TAG = "ServiceLogs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Настройки");
        PreferenceManager.setDefaultValues(this, R.xml.pref, false);
        addPreferencesFromResource(R.xml.pref);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        intent = new Intent(this, MyService.class);
        sConn = new ServiceConnection() {

            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "PrefActivity onServiceConnected params( "+"update = "+sp.getBoolean("autoupdate", false)+" time = "+sp.getString("timeupdate", "1")+" )");
                myService = ((MyService.MyBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "PrefActivity onServiceDisconnected");
                bound = false;
            }
        };
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("autoupdate")){
            if(sp.getBoolean("autoupdate", false)){
                startService(intent);
                if(myService != null){
                    myService.setInterval(Integer.valueOf(sp.getString("timeupdate", "1")));
                }
            }else{
                myService.stopUpdate();
                sp.edit().putString("timeupdate", "1").apply();
            }
        }else if(!(sp.getString("timeupdate", "1").equals("1"))){
            myService.setInterval(Integer.valueOf(sp.getString("timeupdate", "1")));
            startService(intent);
        }else {
            startService(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(intent, sConn, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!bound) return;
        unbindService(sConn);
        bound = false;
    }
}
