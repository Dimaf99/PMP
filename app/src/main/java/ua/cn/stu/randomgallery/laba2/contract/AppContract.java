package ua.cn.stu.randomgallery.laba2.contract;

import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import ua.cn.stu.randomgallery.laba2.services.MainService;

public interface AppContract {
    //void toResultsScreen(Fragment target, Student student);
    void toSetting(Fragment target );
    void toStart(Fragment target);
    MainService getServices();
    void cancel();
}