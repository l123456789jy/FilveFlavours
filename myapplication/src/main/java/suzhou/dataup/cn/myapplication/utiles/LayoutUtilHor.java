package suzhou.dataup.cn.myapplication.utiles;

import android.view.View;
import android.widget.RelativeLayout.LayoutParams;

import suzhou.dataup.cn.myapplication.contex.ApplicationData;

//横屏的适配工具！
public class LayoutUtilHor {
    /**
     * 切图宽高比例值
     */
    private float ratio;
//	private float topMargin;

    private float mRatio;
    private float ScreenWidth;    //屏幕的宽
    private float ScreenHeight;    //屏幕的高

    public LayoutUtilHor() {
        ratio = 1.775f;//这个是按1136/640算出来的比例值！要使用ui图的宽高！因为宽和高颠倒了！
//		topMargin = ApplicationData.ScreenTitle;
        ScreenHeight = ApplicationData.screenWidth;//此时获取屏幕的宽就是屏幕的高
        ScreenWidth = ApplicationData.screenHeight;
        mRatio = ScreenHeight / ScreenWidth;
//		Log.i("spoort_list", "LayoutUtil 屏幕比例" + mRatio);
    }

    public void drawViewLayout(View view, float width, float height, float marginleft,
                               float marginTop) {
        ScreenHeight = ScreenWidth / ratio;

        LayoutParams params = (LayoutParams) view.getLayoutParams();

        if (width == 0.0f) {

        } else {
            params.width = (int) (ScreenWidth * width);
        }
        if (height == 0.0f) {

        } else {
            params.height = (int) (ScreenHeight * height);
        }

        if (marginleft == 0.0f) {

        } else {
            params.leftMargin = (int) (ScreenWidth * marginleft);
        }

        if (marginTop == 0.0f) {

        } else {
            params.topMargin = (int) (ScreenHeight * (marginTop));
        }

        view.setLayoutParams(params);
    }

}
