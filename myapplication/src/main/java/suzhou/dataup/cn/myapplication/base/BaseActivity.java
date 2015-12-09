package suzhou.dataup.cn.myapplication.base;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.leakcanary.RefWatcher;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.contex.ApplicationData;
import suzhou.dataup.cn.myapplication.mangers.SystemBarTintManager;
import suzhou.dataup.cn.myapplication.utiles.LayoutUtil;

public abstract class BaseActivity extends FragmentActivity implements SlidingPaneLayout.PanelSlideListener {
    public final static String TAG = BaseActivity.class.getCanonicalName();
    protected View view; // 当前界面的根
    private int layoutId; // 当前界面对应的布局


    //***************图片操作
    public ImageLoader imageLoader_base;
    public ImageLoadingListener animateFirstListener_base;
    /**
     * 默认格式
     */
    public DisplayImageOptions options_base;
    /**
     * 圆图
     */
    public DisplayImageOptions options_roundness;
    //***************图片操作

    @Override
    public void onPanelClosed(View view) {

    }

    @Override
    public void onPanelOpened(View view) {
        finish();
        this.overridePendingTransition(0, R.anim.slide_out_right);
    }

    @Override
    public void onPanelSlide(View view, float v) {
    }
    /**
     * Toast的对象！
     */
    public Toast toast = null;

    /**
     * 软键盘的处理
     * imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);	//显示软键盘
     * imm.hideSoftInputFromWindow(et_sendmessage.getWindowToken(), 0); //强制隐藏键盘
     */
    public InputMethodManager imm;

    //**************登录弹窗
    public AlertDialog.Builder login_dialog;

    // 布局
    public int screenWidth;
    public int screenHeight;
    public float ScreenTitle; // 标题栏与状态栏的高度占比
    public float ScreenTitle_title; // 标题栏的高度
    protected LayoutUtil mLayoutUtil;
    public Gson mGson = new Gson();
    public BaseActivity(int layoutId) {
        super();
        this.layoutId = layoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSwipeBackFinish();
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.d("spoort_list", "BaseActivity oncreate方法");
        view = View.inflate(this, layoutId, null);
        ButterKnife.inject(this, view);
//		view = LayoutInflater.from(this).inflate(layoutId, null);
        setContentView(view);

        init(); // 初始化头中的各个控件,以及公共控件ImageLoader
        initHead(); // 初始化设置当前界面要显示的头状态
        initContent(); // 初始化当前界面的主要内容
        initLocation(); // 初始化空间位置
        initLogic(); // 初始化逻辑
        setStateBarColor(R.color.colorPrimaryDark);
    }
    //设置状态栏的颜色只能兼容到4.4
    protected void setStateBarColor(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win =getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(resId);
            tintManager.setStatusBarDarkMode(true, this);
        }
    }
    /**
     * 初始化滑动返回
     * 想要实现滑动返回的效果
     * 1.主题该activity的主题为透明
     * 2.SlidingPaneLayout就行了！
     */
    private void initSwipeBackFinish() {
        if (isSupportSwipeBack()) {
            SlidingPaneLayout slidingPaneLayout = new SlidingPaneLayout(this);
            //通过反射改变mOverhangSize的值为0，这个mOverhangSize值为菜单到右边屏幕的最短距离，默认
            //是32dp，现在给它改成0
            try {
                //属性
                Field f_overHang = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
                f_overHang.setAccessible(true);
                f_overHang.set(slidingPaneLayout, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            slidingPaneLayout.setPanelSlideListener(this);
            slidingPaneLayout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));

            View leftView = new View(this);
            leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            slidingPaneLayout.addView(leftView, 0);

            ViewGroup decor = (ViewGroup) getWindow().getDecorView();
            ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
            decorChild.setBackgroundColor(getResources().getColor(android.R.color.white));
            decor.removeView(decorChild);
            decor.addView(slidingPaneLayout);
            slidingPaneLayout.addView(decorChild, 1);
        }
    }

    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean isSupportSwipeBack() {
        return true;
    }
    /**
     * 初始化头中的各个控件,以及公共控件ImageLoader
     */
    protected void init() {
        //初始化布局参数
        screenWidth = ApplicationData.screenWidth;
        screenHeight = ApplicationData.screenHeight;
        mLayoutUtil = new LayoutUtil();
        ScreenTitle = ApplicationData.ScreenTitle;
        ScreenTitle_title = ApplicationData.ScreenTitle_title;

        //初始化ImageLoader
        imageLoader_base = ImageLoader.getInstance();
        animateFirstListener_base = new AnimateFirstDisplayListener();
        options_base = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_menu_camera) // resource or
                        // drawable
                .showImageForEmptyUri(R.drawable.ic_menu_camera) // resource or
                        // drawable
                .showImageOnFail(R.drawable.ic_menu_camera) // resource or drawable
                .resetViewBeforeLoading(false) // default
//				.delayBeforeLoading(1000)	// 延时一秒加载
                .cacheInMemory(true) // default //使用缓存！
                .cacheOnDisk(true) // default
                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler()) // default
                .build();

        options_roundness = new DisplayImageOptions.Builder().cacheInMemory() // 缓存在内存中
                .cacheOnDisc() // 磁盘缓存
                .showImageOnLoading(R.drawable.ic_menu_camera) // resource or
                .showImageForEmptyUri(R.drawable.ic_menu_camera) // resource
                        // or
                .showImageOnFail(R.drawable.ic_menu_camera) // resource or
                        // drawable
                .resetViewBeforeLoading(false) // default
//				.delayBeforeLoading(1000)
                .cacheInMemory(true) // default //使用缓存！
                .cacheOnDisk(true) // default
                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .displayer(new RoundedBitmapDisplayer(120))//设置图片为圆角显示！
                .handler(new Handler()) // default
                .build();
    }

    protected abstract void initHead();

    /**
     * 初始化当前界面的主要内容,即除了头部以外的其它部分
     */
    protected abstract void initContent();

    /**
     * 初始化控件位置
     */
    protected abstract void initLocation();

    /**
     * 初始化逻辑
     */
    protected abstract void initLogic();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //检测内存泄漏的代码
        RefWatcher refWatcher = ApplicationData.getRefWatcher(ApplicationData.context);
        refWatcher.watch(this);
    }

    /**
     * 避免每次都进行强转
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T findViewByIds(int viewId) {
        return (T) view.findViewById(viewId);
    }


    public void showToast(int id) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT);
        } else {
            toast.setText(id);
        }
        toast.show();
    }

    public void showToast(String id) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT);
        } else {
            toast.setText(id);
        }
        toast.show();
    }

    public void showToastShort(int id) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT);
        } else {
            toast.setText(id);
        }
        toast.show();
    }

    public void showToastShort(String id) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT);
        } else {
            toast.setText(id);
        }
        toast.show();
    }

    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

}
