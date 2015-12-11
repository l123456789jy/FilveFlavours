package suzhou.dataup.cn.myapplication.ui.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.base.BaseActivity;

public class WeatherActivity extends BaseActivity {


    @InjectView(R.id.im_bg)
    ImageView mImBg;
    @InjectView(R.id.hight_temp)
    TextView mHightTemp;
    @InjectView(R.id.lows_temp)
    TextView mLowsTemp;
    @InjectView(R.id.desc)
    TextView mDesc;
    @InjectView(R.id.ll)
    LinearLayout mLl;

    public WeatherActivity() {
        super(R.layout.activity_fullscreen);
    }

    @Override
    protected void initHead() {

    }

    @Override
    protected void initContent() {

    }

    @Override
    protected void initLocation() {

    }

    @Override
    protected void initLogic() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
