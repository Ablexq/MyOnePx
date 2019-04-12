package com.example.myonepx;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.util.Calendar;

public class MainActivity extends Activity {
    private Button startBtn;
    private Button settingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Calendar calendar = Calendar.getInstance();
//                int second = calendar.get(Calendar.SECOND);
//                if (second % 10 == 0) {
//                    System.out.println("second=10=================" + second);
//                } else {
//                    System.out.println("second==================" + second);
//                }
//                handler.postDelayed(this, 1000);
//            }
//        }, 1000);

        Log.d("keeplive", "MainActivity process ======== " + android.os.Process.myPid());
        startBtn = (Button) findViewById(R.id.btn1);
        settingBtn = (Button) findViewById(R.id.btn2);

        Intent intent = new Intent(MainActivity.this, DemoService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println("=======================26以上========================");
            startForegroundService(intent);
        } else {
            System.out.println("=======================26以下========================");
            startService(intent);
        }

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, DemoService.class);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    System.out.println("=======================26以上========================");
//                    startForegroundService(intent);
//                } else {
//                    System.out.println("=======================26以下========================");
//                    startService(intent);
//                }
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingUtils.enterWhiteListSetting(MainActivity.this);
            }
        });
    }
}
