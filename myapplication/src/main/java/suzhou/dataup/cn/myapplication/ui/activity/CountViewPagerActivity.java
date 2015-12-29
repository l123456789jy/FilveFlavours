package suzhou.dataup.cn.myapplication.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.ui.activity.adputer.MyCountPagerAdapter;
import suzhou.dataup.cn.myapplication.base.BaseActivity;
import suzhou.dataup.cn.myapplication.bean.CountViewPagerBean;
import suzhou.dataup.cn.myapplication.callback.MyHttpCallBcak;
import suzhou.dataup.cn.myapplication.constance.ConstanceData;
import suzhou.dataup.cn.myapplication.mangers.OkHttpClientManager;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

//详情的界面
public class CountViewPagerActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    String uri;
    @InjectView(R.id.convenientBanner)
    AutoScrollViewPager mConvenientBanner;
    @InjectView(R.id.tv)
    TextView mTv;
    @InjectView(R.id.total_count)
    TextView mTotalCount;
    @InjectView(R.id.index_tv)
    TextView mIndexTv;
    CountViewPagerBean mcountViewPagerBean = null;
    MyCountPagerAdapter MyCountPagerAdapter;
    public CountViewPagerActivity() {
        super(R.layout.activity_count_view_pager);
    }


    @Override
    protected void initHead() {
        mToolbar.setTitle("详情");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

    }

    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean isSupportSwipeBack() {
        return false;
    }
    @Override
    protected void initContent() {
        uri = getIntent().getStringExtra(ConstanceData.VIEW_PAGER_COUNT_URI);
        LogUtil.e(uri);
        getNetData();
    }

    @Override
    protected void initLocation() {
        mLayoutUtil.drawViewLayout(mConvenientBanner, 1f, 0.6f, 0f, 0.040f);
    }

    @Override
    protected void initLogic() {
        mConvenientBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (null != mIndexTv) {
                    mIndexTv.setText((i + 1) + "");
                    mTotalCount.setText("/" + mcountViewPagerBean.body.slides.size());
                    if (mcountViewPagerBean.body.slides.get(i).description.equals("")) {
                        mTv.setText(mcountViewPagerBean.body.slides.get(i).title);
                    } else {
                        mTv.setText(mcountViewPagerBean.body.slides.get(i).description);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountViewPagerActivity.this.onBackPressed();
            }
        });
    }

    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyCountPagerAdapter = new MyCountPagerAdapter(mcountViewPagerBean.body.slides, mLayoutUtil, options_base);
            mConvenientBanner.setAdapter(MyCountPagerAdapter);
            mIndexTv.setText(1 + "");
            mTotalCount.setText("/" + mcountViewPagerBean.body.slides.size());
            mTv.setText(mcountViewPagerBean.body.slides.get(0).description);
        }
    };
    public void getNetData() {
        OkHttpClientManager.get(uri, new MyHttpCallBcak() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "获取数据失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(final Response response) {
                try {
                    mcountViewPagerBean = mGson.fromJson(response.body().string(), CountViewPagerBean.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);

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
