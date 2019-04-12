package com.example.myonepx;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "收到定时广播", Toast.LENGTH_LONG).show();
        this.writeFile(context);
//        Intent i = new Intent(context, LongRunningService.class);
//        context.startService(i);
    }

    private void writeFile(Context context) {
        try {
            String path = Environment.getExternalStoragePublicDirectory("") + "/print22.txt";
            System.out.println("=============================="+path);
            File file = new File(path);
            if (!file.exists() && file.createNewFile()) {
                Toast.makeText(context, "创建文件成功", Toast.LENGTH_LONG).show();
                System.out.println("Create file successed");
            } else {
//                Toast.makeText(context, "创建文件失败", Toast.LENGTH_LONG).show();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
            Date date = new Date(System.currentTimeMillis());
            String time = simpleDateFormat.format(date);
            FileUtil.method2(path, time + ",sendNum:"+ sendNum + ",sum:"+sum);
            FileUtil.method2(path, "\r\n");
            Toast.makeText(context, "写入文件成功", Toast.LENGTH_SHORT).show();
            this.wakeAndNotify(context);
//            this.wakeScreen(context);
//            this.sendNotification(context);
        } catch (Exception e) {
            System.out.println(e);
            Toast.makeText(context, "报错了", Toast.LENGTH_SHORT).show();
        }
    }

    private static long sum = 1;
    private static int sendNum = 1;
    private void wakeAndNotify(Context context) {
        long fa = factorial(sendNum);
        if (sum == fa){
            this.wakeScreen(context);
            this.sendNotification2(context);
            sendNum++;
        }
        sum++;
    }

    /**
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1
     */
    private static long factorial(int n) {
        long sum = 0;
        while( n > 0 ) {
            sum = sum + n--;
        }
        return sum;
    }

    private void wakeScreen(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (!pm.isScreenOn()) {
//            String msg = intent.getStringExtra("msg");
//            textview.setText("又收到消息:" + msg);
            //点亮屏幕
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire();
            wl.release();
        }

    }

    private void sendNotification2(Context context) {
        String id = "my_channel_01";
        String name="我是渠道名字";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
//            Toast.makeText(this, mChannel.toString(), Toast.LENGTH_SHORT).show();
//            Log.i(TAG, mChannel.toString());
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(context)
                    .setChannelId(id)
                    .setContentTitle("我是标题")
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentText("我是内容"+sendNum+","+sum)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setOngoing(true)
                    .setDefaults(Notification.DEFAULT_SOUND).build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle("我是标题")
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentText("我是内容"+sendNum+","+sum)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setOngoing(true)
                    .setDefaults(Notification.DEFAULT_SOUND);
            //无效
            notification = notificationBuilder.build();
        }
        notificationManager.notify(111123, notification);

    }

    private void sendNotification(Context context) {
        /**
         *  创建通知栏管理工具
         */

        NotificationManager notificationManager = (NotificationManager) context.getSystemService
                (NOTIFICATION_SERVICE);

        /**
         *  实例化通知栏构造器
         */

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        /**
         *  设置Builder
         */
        //设置标题
        mBuilder.setContentTitle("我是标题")
                //设置内容
                .setContentText("我是内容"+sendNum+","+sum)
                //设置大图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher_round)
                //设置通知时间
                .setWhen(System.currentTimeMillis())
                //首次进入时显示效果
                .setTicker("我是测试内容")
                //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
                .setDefaults(Notification.DEFAULT_SOUND);
        //发送通知请求
        notificationManager.notify(10, mBuilder.build());
    }
}