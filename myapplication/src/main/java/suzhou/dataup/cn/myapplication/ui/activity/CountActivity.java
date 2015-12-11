package suzhou.dataup.cn.myapplication.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.SweetSheet;
import com.mingle.sweetpick.ViewPagerDelegate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.base.BaseActivity;
import suzhou.dataup.cn.myapplication.constance.ConstanceData;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

//详情的界面
public class CountActivity extends BaseActivity {
    SweetSheet mSweetSheet;
    @InjectView(R.id.toolbars)
    Toolbar mToolbars;
    @InjectView(R.id.webview)
    WebView mWebview;
    @InjectView(R.id.myProgressBar)
    ProgressBar mMyProgressBar;
    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @InjectView(R.id.flat_button)
    FloatingActionButton mFlatButton;
    String[] split;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    public CountActivity() {
        super(R.layout.activity_count);
    }

    @Override
    protected void initHead() {
        mCollapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        mToolbars.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        //  webSettings.setUserAgentString("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; zh-tw) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16");
        mWebview.requestFocusFromTouch();
    }

    @Override
    protected void initContent() {
        String count = getIntent().getStringExtra(ConstanceData.COUNT_URI);
        split = count.split(",");
        //当添加视察的动画的时候不要用toobal设置标题了！
        mCollapsingToolbar.setTitle(split[1]);
        mWebview.loadUrl(split[0]);
        mFlatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSweetSheet.isShow()) {
                    mSweetSheet.dismiss();
                } else {
                    mSweetSheet.show();
                }
            }
        });
    }

    @Override
    protected void initLocation() {
        //监听返回键
        mToolbars.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebview.canGoBack()) {
                    mWebview.goBack();
                } else {
                    CountActivity.this.onBackPressed();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mWebview.getClass().getMethod("onPause").invoke(mWebview, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mWebview.getClass().getMethod("onResume").invoke(mWebview, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initLogic() {
        initSwieet();
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                showToast(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mWebview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                mMyProgressBar.setMax(100);
                if (progress == 100) {
                    mMyProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == mMyProgressBar.getVisibility()) {
                        mMyProgressBar.setVisibility(View.VISIBLE);
                    }
                    mMyProgressBar.setProgress(progress);
                }
                LogUtil.e("加载了" + progress);
            }
        });
        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(CountActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSwieet() {
        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet = new SweetSheet((RelativeLayout) findViewById(R.id.rl));
        List<String> mlist = new ArrayList<>();
        //设置数据源 (数据源支持设置 list 数组,也支持从menu 资源中获取)
        ArrayList<MenuEntity> list = new ArrayList<>();
        //添加假数据
        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.drawable.ic_menu_share;
        menuEntity1.title = "分享";
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.iconId = R.drawable.ic_menu_share;
        menuEntity.title = "QQ";
        list.add(menuEntity1);
        list.add(menuEntity);
        mSweetSheet.setMenuList(list);
        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new ViewPagerDelegate(1));
        //根据设置不同Effect来设置背景效果:BlurEffect 模糊效果.DimEffect 变暗效果,NoneEffect 没有效果.
        mSweetSheet.setBackgroundEffect(new DimEffect(8));
        //设置菜单点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {
                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
                intent.setType("text/plain"); // 分享发送的数据类型
                String msg = split[1] + "\n" + split[0];
                intent.putExtra(Intent.EXTRA_TEXT, msg); // 分享的内容
                startActivity(Intent.createChooser(intent, "选择分享"));// 目标应用选择对话框的标题
                return true;
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);

    }

}

