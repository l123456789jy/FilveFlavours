package suzhou.dataup.cn.myapplication.utiles;


import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout.LayoutParams;

import suzhou.dataup.cn.myapplication.contex.ApplicationData;

public class LayoutUtil {
    /**
     * 切图宽高比例值
     */
    private float ratio;
//	private float topMargin;

    private float mRatio;
    private static final float ScreenWidth = ApplicationData.screenWidth;
    //屏幕的宽
    private static final float ScreenHeight = ApplicationData.screenHeight;
    //屏幕的高

    public LayoutUtil() {
        ratio = 0.563f;//这个是按640/1136算出来的比例值！要使用ui图的宽高！
//		topMargin = ApplicationData.ScreenTitle;


//		Log.i("spoort_list", "LayoutUtil ScreenHeight" + ScreenHeight);
        mRatio = ScreenWidth / ScreenHeight;
//		Log.i("spoort_list", "LayoutUtil 屏幕比例" + mRatio);
    }

    /**
     * 绘制单选框父布局
     *
     * @param view
     * @param ScreenWidth  屏幕的宽度！
     * @param ScreenHeight 屏幕的高度！
     * @param width        控件的宽度！
     * @param height       控件的高度！
     * @param marginleft   控件距离左边的距离！
     * @param marginTop    控件距离上边的距离！
     */
    public void drawRadiogroup(RadioGroup view, float width, float height, float marginleft,
                               float marginTop) {
        float ScreenHeight = ScreenWidth / ratio;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view
                .getLayoutParams();

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

    /**
     * 绘制单选框
     *
     * @param view
     * @param ScreenWidth  屏幕的宽度！
     * @param ScreenHeight 屏幕的高度！
     * @param width        控件的宽度！
     * @param height       控件的高度！
     * @param marginleft   控件距离左边的距离！
     * @param marginTop    控件距离上边的距离！
     */
    public void drawRadiobutton(RadioButton view, float width, float height, float marginleft,
                                float marginTop) {
        float ScreenHeight = ScreenWidth / ratio;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view
                .getLayoutParams();

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

    /**
     * 绘制多选框 CheckBox
     *
     * @param view
     * @param width
     * @param height
     * @param marginleft
     * @param marginTop
     */
    public void drawCheckBox(CheckBox view, float width, float height, float marginleft,
                             float marginTop) {
        float ScreenHeight = ScreenWidth / ratio;

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


    public void drawViewLinearLayout(View view, float width, float height, float marginleft,
                                     float marginTop) {
        float ScreenHeight = ScreenWidth / ratio;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();

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

    public void drawViewLinearRBLayout(View view, float width, float height, float marginleft, float marginright,
                                       float marginTop, float marginBottom) {
        float ScreenHeight = ScreenWidth / ratio;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();

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

        if (marginright == 0.0f) {

        } else {
            params.rightMargin = (int) (ScreenWidth * marginright);
        }

        if (marginTop == 0.0f) {

        } else {
            params.topMargin = (int) (ScreenHeight * (marginTop));
        }

        if (marginBottom == 0.0f) {

        } else {
            params.bottomMargin = (int) (ScreenHeight * (marginBottom));
        }

        view.setLayoutParams(params);
    }

    public void drawViewLinearlLayout(View view, float width, float height, float marginleft, float marginright,
                                      float marginTop) {
        float ScreenHeight = ScreenWidth / ratio;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();

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

        if (marginright == 0.0f) {

        } else {
            params.rightMargin = (int) (ScreenWidth * marginright);
        }

        if (marginTop == 0.0f) {

        } else {
            params.topMargin = (int) (ScreenHeight * (marginTop));
        }

        view.setLayoutParams(params);
    }

    public void drawViewLayout(View view, float width, float height, float marginleft,
                               float marginTop) {
        float ScreenHeight = ScreenWidth / ratio;

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

    public void drawViewFramLayout(View view, float width, float height, float marginleft,
                                   float marginTop) {
        float ScreenHeight = ScreenWidth / ratio;

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();

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

    /**
     * 绘制首页
     *
     * @param view       视图对象
     * @param width      宽
     * @param height     高
     * @param marginleft 左边距
     * @param marginTop  上边距
     */
    public void drawViewLayouts(View view, float width, float height, float marginleft,
                                float marginTop) {
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        if (mRatio > ratio) {    //大于切图，缩放宽

            if (width == 0.0f) {
            } else {
                params.width = (int) (ScreenHeight * width * mRatio);
            }
            if (height == 0.0f) {
            } else {
                params.height = (int) (ScreenHeight * height);
            }
        } else if (mRatio < ratio) {    //小于切图，缩放高
            if (width == 0.0f) {
            } else {
                params.width = (int) (ScreenWidth * width);
            }

            if (height == 0.0f) {
            } else {
                params.height = (int) (ScreenWidth / mRatio * height);
            }
        } else {
            if (width == 0.0f) {
            } else {
                params.width = (int) (ScreenWidth * width);
            }

            if (height == 0.0f) {
            } else {
                params.height = (int) (ScreenHeight * height);
            }
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

    /**
     * 绘制首页
     *
     * @param view         视图对象
     * @param width        宽
     * @param height       高
     * @param marginleft   左边距
     * @param marginright  右边距
     * @param marginTop    上边距
     * @param marginBottom 下边距
     */
    public void drawViewRBLayouts(View view, float width, float height, float marginleft, float marginright,
                                  float marginTop, float marginBottom) {
        LayoutParams params = (LayoutParams) view.getLayoutParams();

        if (mRatio > ratio) {    //大于切图，缩放宽
//			Log.i("spoort_list", "HomeFragmentActivity initLocation 大于切图，缩放宽");

            if (width == 0.0f) {
            } else {
                params.width = (int) (ScreenHeight * width * mRatio);
            }
            if (height == 0.0f) {
            } else {
                params.height = (int) (ScreenHeight * height);
            }
        } else if (mRatio < ratio) {    //小于切图，缩放高
//			Log.i("spoort_list", "HomeFragmentActivity initLocation 小于切图，缩放高");
            if (width == 0.0f) {
            } else {
                params.width = (int) (ScreenWidth * width);
            }

            if (height == 0.0f) {
            } else {
                params.height = (int) (ScreenWidth / mRatio * height);
            }
        } else {
            if (width == 0.0f) {
            } else {
                params.width = (int) (ScreenWidth * width);
            }

            if (height == 0.0f) {
            } else {
                params.height = (int) (ScreenHeight * height);
            }
        }

        if (marginleft == 0.0f) {
        } else {
            params.leftMargin = (int) (ScreenWidth * marginleft);
        }

        if (marginright == 0.0f) {

        } else {
            params.rightMargin = (int) (ScreenWidth * marginright);
        }

        if (marginTop == 0.0f) {
        } else {
            params.topMargin = (int) (ScreenHeight * (marginTop));
        }

        if (marginBottom == 0.0f) {

        } else {
            params.bottomMargin = (int) (ScreenHeight * marginBottom);
        }
        view.setLayoutParams(params);
    }

    public void drawViewlLayout(View view, float width, float height, float marginleft, float marginright,
                                float marginTop) {
        float ScreenHeight = ScreenWidth / ratio;

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
        if (marginright == 0.0f) {

        } else {
            params.rightMargin = (int) (ScreenWidth * marginright);
        }

        if (marginTop == 0.0f) {

        } else {
            params.topMargin = (int) (ScreenHeight * (marginTop));
        }

        view.setLayoutParams(params);
    }

    public void drawViewRBLayout(View view, float width, float height, float marginleft, float marginright,
                                 float marginTop, float marginBottom) {
        float ScreenHeights = ScreenWidth / ratio;
        Log.i("spoort_list", "屏幕的高度为" + ScreenHeights);

        LayoutParams params = (LayoutParams) view.getLayoutParams();

        if (width == 0.0f) {

        } else {
            params.width = (int) (ScreenWidth * width);
        }

        if (height == 0.0f) {

        } else {
            params.height = (int) (ScreenHeights * height);
        }

        if (marginleft == 0.0f) {

        } else {
            params.leftMargin = (int) (ScreenWidth * marginleft);
        }
        if (marginright == 0.0f) {

        } else {
            params.rightMargin = (int) (ScreenWidth * marginright);
        }

        if (marginTop == 0.0f) {

        } else {
            params.topMargin = (int) (ScreenHeights * (marginTop));
        }

        if (marginBottom == 0.0f) {

        } else {
            params.bottomMargin = (int) (ScreenHeights * marginBottom);
        }
        view.setLayoutParams(params);
    }

}
