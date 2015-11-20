package suzhou.dataup.cn.myapplication.contex;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.view.WindowManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;
import com.github.mmin18.layoutcast.LayoutCast;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;
import java.util.List;

import suzhou.dataup.cn.myapplication.constance.Directory;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

public class ApplicationData extends Application {

    public static int screenWidth;
    public static int screenHeight;
    /**
     * 标题栏与状态栏的高度占比
     */
    public static float ScreenTitle;
    /**
     * 标题栏的高度占比
     */
    public static float ScreenTitle_title;
    /**
     * 硬件类型，0，血糖；1，血压；2，运动
     */
    public static int DeviceType = 1;
    /**
     * 硬件运动模式是否断开连接
     */
    public static boolean DeviceClose = true;

    //**********************chat
    /**
     * 标记是否显示showPhone中的删除按钮。0，显示；1，删除
     */
    public static int isShowPhone = 0;
    /**
     * 聊天对象的头像地址
     */
    public static String myChatFriend_headerAddress = "";
    /**
     * 聊天对象的用户名
     */
    public static String myChatFriend_name = "";
    /**
     * 记录聊天对象（ID）
     */
    public static String myChatFriend_id = "";
    /**
     * 记录聊天（ID）咨询id
     */
    public static String myChat_id = "";
    /**
     * 用户头像
     */
    public static String myChat_header = "";
    /**
     * 医生头像
     */
    public static String dcChat_header = "";
    //**********************chat

    /**
     * 生日 年
     */
    public static String dcBirthYear = "1990";
    /**
     * 生日 月
     */
    public static String dcBirthMonth = "11";
    /**
     * 生日 日
     */
    public static String dcBirthDay = "16";

    public static Context context;
    public static String imei; // imei号
    public static int loginType = -1;    //登录状态  -1不在线，0 server登录，1 qq，2 微博
    /**
     * 记录播放（ID）视频的id
     */
    public static String VideId = "";
    /**
     * 记录播放地址
     */
    public static String HeardUri = "";
    /**
     * 记录播放的标题
     */
    public static String Title = "";
    /**
     * 记录图片的地址
     */
    public static String ImaeView = "";
    /**
     * 记录删除视频的ids
     */
    public static String ids = "";
    /**
     * 记录药品的名字
     */
    public static String drugName = "";

    /**
     * 这个是保存运动指南de
     */
    public static String id = null;
    public static String type = null;
    /**
     * 1代表运动指南，2代表症状！
     */
    public static String types = null;
    public static RefWatcher mrefWatcher;
    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        // 获取屏幕尺寸大小，使程序能在不同大小的手机上有更好的兼容性
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
        context = getApplicationContext();
        initImageLoader(context);
        //初始化LeakCanary

        mrefWatcher = LeakCanary.install(this);
        LayoutCast.init(this);
        initLocation();
        LogUtil.e("屏幕的宽度" + screenWidth);
        LogUtil.e("屏幕的高度" + screenHeight);
    }

    private void initLocation() {
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }

    //获取RefWatcher
    public static RefWatcher getRefWatcher(Context context) {
        ApplicationData application = (ApplicationData) context.getApplicationContext();
        return mrefWatcher;
    }

    /**
     * 初始化ImageLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        @SuppressWarnings("deprecation")
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(context))
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(new WeakMemoryCache())//这个类缓存bitmap的总大小没有限制，唯一不足的地方就是不稳定，缓存的图片容易被回收掉
                        //缓存到sd卡把图片！
                .discCache(new UnlimitedDiscCache(new File(Directory.CACHE_VIEWSIMAGE.toString())))
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     */
    private static int getMemoryCacheSize(Context context) {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
            // limit
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }
        return memoryCacheSize;
    }

    /**
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");// 位置语义化信息
            sb.append(location.getLocationDescribe());
            List<Poi> list = location.getPoiList();// POI信息
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            LogUtil.e("BaiduLocationApiDem", sb.toString() + location.getCity());
        }
    }
}
