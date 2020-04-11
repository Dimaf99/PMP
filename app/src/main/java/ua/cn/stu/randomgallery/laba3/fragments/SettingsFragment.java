package ua.cn.stu.randomgallery.laba3.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ua.cn.stu.randomgallery.laba3.R;
import ua.cn.stu.randomgallery.laba3.model.Settings;


public class SettingsFragment extends BaseFragment {
    private int min;
    private int max;
    private int time ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView edt_min = (TextView) view.findViewById(R.id.edt_min);
        TextView edt_max = (TextView) view.findViewById(R.id.edt_max);
        TextView edt_time = (TextView) view.findViewById(R.id.edt_time);

        this.min = getAppContract().getServices().getSetting().getMin();
        this.max = getAppContract().getServices().getSetting().getMax();
        this.time = getAppContract().getServices().getSetting().getTime();


        edt_min.setText( String.valueOf( this.min ) );
        edt_max.setText( String.valueOf(  this.max ));
        edt_time.setText( String.valueOf(  this.time ));

        Button btn = (Button) view.findViewById(R.id.btn_save );
        btn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView edt_min = (TextView) view.findViewById(R.id.edt_min);
                TextView edt_max = (TextView) view.findViewById(R.id.edt_max);
                TextView edt_time = (TextView) view.findViewById(R.id.edt_time);

                Settings setting = new Settings();
                setting.setMin( Integer.parseInt( edt_min.getText().toString()) );
                setting.setMax( Integer.parseInt( edt_max.getText().toString()) );
                setting.setTime( Integer.parseInt( edt_time.getText().toString()) );

                getAppContract().getServices().setSetting(setting);
                getAppContract().cancel();

            }
        });
    }
}
