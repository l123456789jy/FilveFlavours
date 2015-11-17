package suzhou.dataup.cn.myapplication.utiles;

import android.content.Context;
import android.widget.Toast;

import suzhou.dataup.cn.myapplication.contex.ApplicationData;

/**
 * Toast 工具类
 * <p/>
 * 使用自定义Toast类 防止多次点击重复生成多个Toast 长时间显示问题
 *
 * @author blj
 */
public class ToastUtils {
    /**
     * 全局短提示 by resId
     *
     * @param resId
     */
    public static void show(int resId) {
        shortToast(ApplicationData.context, resId);
    }

    /**
     * 全局 短提示 by String
     *
     * @param str
     */
    public static void show(String str) {
        shortToast(ApplicationData.context, str);
    }

    /**
     * 全局 长提示 by resId
     *
     * @param resId
     */
    public static void longShow(int resId) {
        longToast(ApplicationData.context, resId);
    }

    /**
     * 全局 长提示 by String
     *
     * @param str
     */
    public static void longShow(String str) {
        longToast(ApplicationData.context, str);
    }


    /**
     * 短提示 by resId
     *
     * @param context
     * @param resId
     */
    public static void shortToast(Context context, int resId) {
        showToastResId(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 短提示 by String
     *
     * @param context
     * @param string
     */
    public static void shortToast(Context context, String string) {
        showToastStr(context, string, Toast.LENGTH_SHORT);
    }

    /**
     * 长提示 by resId
     *
     * @param context
     * @param resId
     */
    public static void longToast(Context context, int resId) {
        showToastResId(context, resId, Toast.LENGTH_LONG);
    }


    /**
     * 常提示 by String
     *
     * @param context
     * @param string
     */
    public static void longToast(Context context, String string) {
        showToastStr(context, string, Toast.LENGTH_LONG);
    }

    /**
     * 判断时间间隔提示Toast by String
     *
     * @param context
     * @param str      文字
     * @param showTime
     */
    private static void showToastStr(Context context, String str, int showTime) {
        MyToast.getToast(context, str, showTime).show();
    }

    /**
     * 判断时间间隔提示Toast by resId
     *
     * @param context
     * @param resId
     * @param showTime
     */
    private static void showToastResId(Context context, int resId, int showTime) {
        MyToast.getToast(context, context.getString(resId), showTime).show();
    }

    /**
     * 自定义Toast类
     *
     * @author blj
     */
    static class MyToast {
        private static Context context = null;
        private static Toast toast = null;

        public static Toast getToast(Context context, String words, int showTime) {
            if (MyToast.context == context) {
                toast.setText(words);
                toast.setDuration(showTime);
            } else {
                MyToast.context = context;
                toast = Toast.makeText(context, words, showTime);
            }
            return toast;
        }
    }

}
