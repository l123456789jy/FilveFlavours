package suzhou.dataup.cn.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import suzhou.dataup.cn.myapplication.service.MyService;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e("收到的广播" + intent.getAction());
        //监听系统的广播来重复的启动服务，如果服务被杀死就启动起来，监听手机开启的广播，和Intent.ACTION_TIME_TICK这个广播系统会每隔一分钟发送一次，只能在代码中注册！
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) || Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
            Intent startServiceIntent = new Intent(context, MyService.class);
            startServiceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(startServiceIntent);
        }
    }

}
