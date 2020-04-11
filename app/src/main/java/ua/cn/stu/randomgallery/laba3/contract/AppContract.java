package ua.cn.stu.randomgallery.laba3.contract;

import androidx.fragment.app.Fragment;

import ua.cn.stu.randomgallery.laba3.services.MainService;

public interface AppContract {
    //void toResultsScreen(Fragment target, Student student);
    void toSetting(Fragment target );
    void toStart(Fragment target);
    MainService getServices();
    void cancel();
}