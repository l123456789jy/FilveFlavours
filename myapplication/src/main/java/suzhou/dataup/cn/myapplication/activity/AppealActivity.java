package suzhou.dataup.cn.myapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.base.BaseActivity;
import suzhou.dataup.cn.myapplication.constance.ConstanceData;

public class AppealActivity extends BaseActivity {
    @InjectView(R.id.toolbars)
    Toolbar mToolbars;
    @InjectView(R.id.im)
    ImageView mIm;

    public AppealActivity() {
        super(R.layout.activity_appeal);
    }

    @Override
    protected void initHead() {
        mToolbars.setTitle("大图");
        mToolbars.setTitleTextColor(Color.WHITE);
        mToolbars.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    }

    @Override
    protected void initContent() {
        String imageUri = getIntent().getStringExtra(ConstanceData.IMAGEURI);
        ImageLoader.getInstance().displayImage(imageUri, mIm);
    }

    @Override
    protected void initLocation() {

    }

    @Override
    protected void initLogic() {
        //监听返回键
        mToolbars.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppealActivity.this.onBackPressed();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
