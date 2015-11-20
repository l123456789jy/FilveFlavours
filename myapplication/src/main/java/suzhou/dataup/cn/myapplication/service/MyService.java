package suzhou.dataup.cn.myapplication.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

public class MyService extends Service {
    AlarmManager mAlarmManager = null;
    PendingIntent mPendingIntent = null;

    @Override
    public void onCreate() {
        //start the service through alarm repeatly打开天气通知栏的服务定位获取天气
        Intent intent = new Intent(getApplicationContext(), NotificationIntentService.class);
        intent.setAction(NotificationIntentService.ACTION_FOO);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mPendingIntent = PendingIntent.getService(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
        long now = System.currentTimeMillis();
        //每隔半个小时执行一次
        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, now, 1800000, mPendingIntent);


        //避免服务被杀死放置一个隐形的通知栏，让服务在前台
        Notification notification = new Notification(R.drawable.ic_discuss, "wf update service is running", System.currentTimeMillis());
        PendingIntent mpintent = PendingIntent.getService(this, 0, intent, 0);
        notification.setLatestEventInfo(this, "WF Update Service", "wf update service is running！", mpintent);
        //让该service前台运行，避免手机休眠时系统自动杀掉该服务
        //如果 id 为 0 ，那么状态栏的 notification 将不会显示。
        startForeground(0, notification);
        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("Callback Successed!");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
