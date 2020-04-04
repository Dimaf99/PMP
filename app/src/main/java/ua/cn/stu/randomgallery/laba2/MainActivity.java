package ua.cn.stu.randomgallery.laba2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.UUID;

import ua.cn.stu.randomgallery.laba2.contract.AppContract;
import ua.cn.stu.randomgallery.laba2.fragments.GameFragment;
import ua.cn.stu.randomgallery.laba2.fragments.MenuFragment;
import ua.cn.stu.randomgallery.laba2.fragments.SettingsFragment;
import ua.cn.stu.randomgallery.laba2.model.Settings;
import ua.cn.stu.randomgallery.laba2.services.MainService;

public class MainActivity extends AppCompatActivity implements AppContract {

    public static String LOG_TAG = "MY_ACTIVITY";

    private MainService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Activity onCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            launchFragment(null, new MenuFragment());
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "Activity onStart");
        Intent intent = new Intent(this, MainService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "Activity onStop");
        unbindService(serviceConnection);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "Activity onDestoy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Activity onPause");
    }

    private void launchFragment(@Nullable Fragment target, Fragment fragment) {
        if (target != null) {
            fragment.setTargetFragment(target, 0);
        }
        String tag = UUID.randomUUID().toString();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, fragment, tag)
                .commit();

    }

    @Override
    public void toSetting(Fragment target) {
        launchFragment(target, new SettingsFragment() );
    }

    @Override
    public void toStart(Fragment target) {
        launchFragment(target, new GameFragment());
    }

    @Override
    public MainService getServices() {
        return service;
    }


    @Override
    public void cancel() {
        int count = getSupportFragmentManager()
                .getBackStackEntryCount();
        if (count <= 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        cancel();
    }

    private ServiceConnection serviceConnection =
            new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder binder) {
                    service = ((MainService.MainServiceBinder) binder).getService();
                    Log.d("MY_SERVICE", "ServiceConnection connected to " + service);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    service = null;
                }
            };

}
