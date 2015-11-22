package suzhou.dataup.cn.myapplication.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.greenrobot.event.EventBus;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.activity.WeatherActivity;
import suzhou.dataup.cn.myapplication.bean.WeatrherBean;
import suzhou.dataup.cn.myapplication.callback.MyHttpCallBcak;
import suzhou.dataup.cn.myapplication.constance.CountUri;
import suzhou.dataup.cn.myapplication.contex.ApplicationData;
import suzhou.dataup.cn.myapplication.dbs.LocationCode;
import suzhou.dataup.cn.myapplication.mangers.OkHttpClientManager;
import suzhou.dataup.cn.myapplication.utiles.GsonUtils;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 * 顶部天气通知栏的服务
 */
public class NotificationIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "suzhou.dataup.cn.myapplication.service.action.FOO";
    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "suzhou.dataup.cn.myapplication.service.extra.PARAM1";
    private LocationClient mLocationClient;
    NotificationManager mNotificationManager = (NotificationManager) ApplicationData.context.getSystemService(NOTIFICATION_SERVICE);
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

    public NotificationIntentService() {
        super("NotificationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        EventBus.getDefault().register(this);
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                //开始定位
                initLocation();
                mLocationClient.start();
            }
        }
    }

    //收到定位成功的消息之后关闭定位
    public void onEventMainThread(final BDLocation location) {
        Toast.makeText(this, location.getCity(), Toast.LENGTH_LONG).show();
        mLocationClient.stop();
        HashMap<String, String> chineseLocalCodeMap = LocationCode.CHINESE_LOCAL_CODE;
        //遍历这个Map获取城市代码
        Iterator<Map.Entry<String, String>> iterator = chineseLocalCodeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (location.getCity().contains(entry.getKey())) {
                getWeather(entry.getValue());
                EventBus.getDefault().unregister(this);
                return;
            }
        }
    }

    private void initLocation() {
        mLocationClient = ((ApplicationData) getApplication()).mLocationClient;
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系，
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }

    //获取天气
    private void getWeather(String cityCode) {
        OkHttpClientManager.get(CountUri.WEATHER_URI + "citykey=" + cityCode + "", new MyHttpCallBcak() {
            @Override
            public void onFailure(Request request, IOException e) {
                LogUtil.e("获取天气失败");
            }

            @Override
            public void onResponse(final Response response) {
                Message mMessage = android.os.Message.obtain();
                try {
                    mMessage.obj = response.body().string();
                    mHandler.sendMessage(mMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public Handler mHandler = new Handler(ApplicationData.context.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WeatrherBean mweatrherBean = GsonUtils.getInstance().toObj(msg.obj.toString(), WeatrherBean.class);
            mBuilder.setContentTitle(mweatrherBean.data.forecast.get(0).date)//设置通知栏标题
                    .setContentText(mweatrherBean.data.forecast.get(0).type + " " + mweatrherBean.data.forecast.get(0).high + " " + mweatrherBean.data.forecast.get(0).low) ///设置通知栏显示内容</span>
                    .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
                            //  .setNumber(number) //设置通知集合的数量
                    .setTicker("天气情况") //通知首次出现在通知栏，带上升动画效果的
                    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                    .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                    .setAutoCancel(false)//设置这个标志当用户单击面板就可以让通知将自动取消
                    .setOngoing(true)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                    .setDefaults(Notification.FLAG_ONLY_ALERT_ONCE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                            //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                    .setSmallIcon(R.drawable.ic_discuss);//设置通知小ICON
            mBuilder.build().vibrate = new long[]{0, 300, 500, 700};
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_discuss));
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mNotificationManager.notify(0, mBuilder.build());
            LogUtil.e("天气" + msg.obj.toString());
        }
    };

    public PendingIntent getDefalutIntent(int flags) {
        Intent mIntent = new Intent(getApplicationContext(), WeatherActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, mIntent, flags);
        return pendingIntent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
