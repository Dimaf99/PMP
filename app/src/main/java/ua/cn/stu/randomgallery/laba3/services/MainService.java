package ua.cn.stu.randomgallery.laba3.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import ua.cn.stu.randomgallery.laba3.model.Settings;

public class MainService extends Service {
    final String LOG_TAG = "MY_SERVICE";

    // settings
    //private int min = 4;
    //private int max = 9;
    //private int time = 5;


    public String APP_PREFERENCES = "mysettings";
    private SharedPreferences sPref;


    // strings
    private String[] arrWords  = new String[] {
            "Январь", "Февраль", "Апрель", "Июль", "Июнь", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь12"
    };

    // binder
    private MainServiceBinder binder =  new MainServiceBinder();


    @Override
    public void onCreate() {
        super.onCreate();

        sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        // default values
        SharedPreferences.Editor ed = this.sPref.edit();
        ed.putString("min", String.valueOf(4));
        ed.putString("max", String.valueOf(9));
        ed.putString("time", String.valueOf(5));
        ed.commit();

        Log.d(LOG_TAG, "MyService started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "MyService bind");
        return binder;
    }

    public Settings getSetting(){
        Settings stngs = new Settings();
        stngs.setMin( Integer.parseInt( sPref.getString("min", "") ));
        stngs.setMax( Integer.parseInt(sPref.getString("max", "")) );
        stngs.setTime( Integer.parseInt(sPref.getString("time", "")) );

        return stngs;
    }

    public void setSetting(Settings setting){
        SharedPreferences.Editor ed = this.sPref.edit();
        ed.putString("min", String.valueOf( setting.getMin() ));
        ed.putString("max", String.valueOf(  setting.getMax() ));
        ed.putString("time", String.valueOf( setting.getTime() ));
        ed.commit();
    }



    public String [] getStrings(){
        return arrWords;
    }

    public class MainServiceBinder extends Binder {
        public MainService getService() {
            return MainService.this;
        }
    }
}
