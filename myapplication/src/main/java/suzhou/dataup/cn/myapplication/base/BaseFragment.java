package suzhou.dataup.cn.myapplication.base;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.contex.ApplicationData;
import suzhou.dataup.cn.myapplication.utiles.LayoutUtil;

public abstract class BaseFragment extends Fragment {
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

    private Toast toast = null; //Toast的对象！

    //**************登录弹窗
    public AlertDialog.Builder login_dialog;
    // 布局
    private int screenWidth;
    private int screenHeight;
    float ScreenTitle; // 标题栏与状态栏的高度占比
    float ScreenTitle_title; // 标题栏的高度
    protected LayoutUtil mLayoutUtil;
    public Gson mGson = new Gson();

    public BaseFragment(int layoutId) {
        super();
        this.layoutId = layoutId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view = View.inflate(getActivity(), layoutId, null);
//		view = inflater.inflate(layoutId, container, false);
        ButterKnife.inject(this, view);

        init(); // 初始化头中的各个控件,以及公共控件ImageLoader
        initHead(); // 初始化设置当前界面要显示的头状态
        initContent(); // 初始化当前界面的主要内容
        initLocation(); // 初始化空间位置
        initLogic(); // 初始化逻辑
        return view;
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
                .showImageOnLoading(R.drawable.londing) // resource or
                        // drawable
                .showImageForEmptyUri(R.drawable.londing) // resource or
                        // drawable
                .showImageOnFail(R.drawable.londing) // resource or drawable
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

    /**
     * 显示和隐藏
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //可见
        if (isVisibleToUser) {
            isShow();
            //不可见
        } else {
            isGone();
        }
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

    /**
     * fragment可见
     */
    protected abstract void isShow();

    /**
     * fragment不可见
     */
    protected abstract void isGone();

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
            toast = Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT);
        } else {
            toast.setText(id);
        }
        toast.show();
    }

    public void showToast(String id) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT);
        } else {
            toast.setText(id);
        }
        toast.show();
    }

    public void showToastShort(int id) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT);
        } else {
            toast.setText(id);
        }
        toast.show();
    }

    public void showToastShort(String id) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT);
        } else {
            toast.setText(id);
        }
        toast.show();
    }

    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
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
