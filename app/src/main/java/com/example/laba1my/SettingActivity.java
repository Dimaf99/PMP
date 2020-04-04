package com.example.laba1my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    private TextView min;
    private TextView max;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        min = (TextView) findViewById(R.id.edt_min);
        max = (TextView) findViewById(R.id.edt_max);
        time = (TextView) findViewById(R.id.edt_time);

        Intent intent = this.getIntent();

        min.setText(  intent.getStringExtra("min") );
        max.setText(  intent.getStringExtra("max") );
        time.setText( intent.getStringExtra("time"));
    }

    public void OnClickSave( View v ){
        Intent intent = new Intent(this, MainActivity.class );
        intent.putExtra("min",  min.getText().toString());
        intent.putExtra("max", max.getText().toString());
        intent.putExtra("time",  time.getText().toString());
        setResult(1, intent);
        finish();
    }

}

