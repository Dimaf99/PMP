package com.example.laba1my;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    private int min = 4;
    private int max = 9;
    private int time = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.min = Integer.parseInt( data.getStringExtra("min") );
        this.max = Integer.parseInt( data.getStringExtra("max") );
        this.time = Integer.parseInt( data.getStringExtra("time"));

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onClickStartGame(View v){
        Intent intent = new Intent(this, GameActivity.class );
        intent.putExtra("min",  String.valueOf( this.min ) );
        intent.putExtra("max", String.valueOf( this.max));
        intent.putExtra("time", String.valueOf( this.time ));
        startActivity(intent);
    }

    public void onClickSetting(View v){
        Intent intent = new Intent(this, SettingActivity.class );
        intent.putExtra("min",  String.valueOf( this.min ) );
        intent.putExtra("max", String.valueOf( this.max));
        intent.putExtra("time", String.valueOf( this.time ));
        startActivityForResult(intent, 1);
    }
}
