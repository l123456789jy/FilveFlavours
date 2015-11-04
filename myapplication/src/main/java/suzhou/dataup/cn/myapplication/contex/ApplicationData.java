package suzhou.dataup.cn.myapplication.contex;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.view.WindowManager;

import com.github.mmin18.layoutcast.LayoutCast;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

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
    /**
     * 测量状态1，手动，0，自动
     */
    public static int DeviceState = 1;
    public static String BSData = "3.0";    //血糖值
    public static int BSTyPe = 0;    //0，餐前；1，餐后
    public static String BPSSData = "120";    //收缩压
    public static String BPSZData = "80";    //舒张压
    public static String BPXLData = "0";    //心率
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
        LayoutCast.init(this);
        LogUtil.e("屏幕的宽度" + screenWidth);
        LogUtil.e("屏幕的高度" + screenHeight);
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
}
