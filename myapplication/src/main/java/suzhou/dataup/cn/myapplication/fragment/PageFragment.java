package suzhou.dataup.cn.myapplication.fragment;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import rx.Observable;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.adputer.MyPagerAdapter;
import suzhou.dataup.cn.myapplication.adputer.NewsAdputer;
import suzhou.dataup.cn.myapplication.base.BaseFragment;
import suzhou.dataup.cn.myapplication.bean.NewsBean;
import suzhou.dataup.cn.myapplication.bean.ViewPagerBean;
import suzhou.dataup.cn.myapplication.callback.LodeMoreCallBack;
import suzhou.dataup.cn.myapplication.callback.MyHttpCallBcak;
import suzhou.dataup.cn.myapplication.constance.CountUri;
import suzhou.dataup.cn.myapplication.listener.RecyclerViewOnScroll;
import suzhou.dataup.cn.myapplication.mangers.OkHttpClientManager;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;
/**
 * 新闻界面
 */
public class PageFragment extends BaseFragment implements LodeMoreCallBack {
    Observable<String> stringObservable;
    @InjectView(R.id.convenientBanner)
    AutoScrollViewPager mConvenientBanner;
    @InjectView(R.id.tv)
    TextView mTv;
    @InjectView(R.id.total_count)
    TextView mTotalCount;
    @InjectView(R.id.index_tv)
    TextView mIndexTv;
    public List<ImageView> mImageViewList = new ArrayList<>();
    @InjectView(R.id.rl)
    RelativeLayout mRl;
    @InjectView(R.id.my_recycler_view)
    RecyclerView mMyRecyclerView;
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;
    @InjectView(R.id.load_more_pb)
    ProgressBar mLoadMorePb;
    @InjectView(R.id.load_more_tv)
    TextView mLoadMoreTv;
    @InjectView(R.id.footer_linearlayout)
    LinearLayout mFooterLinearlayout;
    NewsBean mnewsBean;
    NewsAdputer mNewsAdputer;
    boolean isFirst = true;
    public int index = 2;
    HttpUtils mHttpUtils = new HttpUtils();
    ViewPagerBean mviewPagerBean;
    public List<NewsBean.BodyEntity.ItemEntity> resultsEntityList = new ArrayList<>();
    public PageFragment() {
        super(R.layout.fragment_page);
    }
    @Override
    protected void initHead() {
    }
    @Override
    protected void initContent() {
        getNewsViewPagerData();

        // mConvenientBanner.startAutoScroll();
        // 创建一个线性布局管理器
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mMyRecyclerView.setLayoutManager(mLayoutManager);//设置线性的管理器！
        //设置刷新时的不同的颜色！
        mSwipeContainer.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        //google官方的下拉刷新！
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isFirst = true;
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        index = 2;
                        resultsEntityList.clear();
                        getNetData(2);
                    }
                }.start();
            }
        });
    }

    @Override
    protected void initLocation() {
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
                    mTv.setText(mviewPagerBean.body.item.get(i).title);
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }
    @Override
    protected void isShow() {
        isFirst = true;
        getNetData(2);
    }
    @Override
    protected void isGone() {
    }
    //获取头部的数据的数据
    private void getNewsViewPagerData() {
        //在子线程中执行访问网络的操作
        OkHttpClientManager.get(CountUri.Home_pager, new MyHttpCallBcak() {
            @Override
            public void onFailure(Request request, IOException e) {
            }
            @Override
            public void onResponse(final Response response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mImageViewList.clear();
                            JSONArray mJSONArray = new JSONArray(response.body().string());
                            mviewPagerBean = mGson.fromJson(mJSONArray.getString(0), ViewPagerBean.class);
                            MyPagerAdapter mMyPagerAdapter = new MyPagerAdapter(mviewPagerBean.body.item, mLayoutUtil, getActivity(), mImageViewList, mTv, mTotalCount, mIndexTv, options_base);
                            mConvenientBanner.setAdapter(mMyPagerAdapter);
                            mTv.setText(mviewPagerBean.body.item.get(0).title);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //加载更多
    @Override
    public void LodeMore() {
        isFirst = false;
        mFooterLinearlayout.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                super.run();
                getNetData(index++);
            }
        }.start();


    }

    public void getNetData(int index) {
        LogUtil.e(CountUri.NEWS_BASE_URI + index);
        mHttpUtils.send(HttpRequest.HttpMethod.GET, CountUri.NEWS_BASE_URI + index, new RequestCallBack<String>() {
            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeContainer.setRefreshing(false);//刷新完毕!
                        try {
                            JSONArray mJSONArray = new JSONArray(responseInfo.result.toString());
                            mnewsBean = mGson.fromJson(mJSONArray.getString(0), NewsBean.class);
                            for (NewsBean.BodyEntity.ItemEntity itemEntity : mnewsBean.body.item) {
                                resultsEntityList.add(itemEntity);
                            }
                            if (isFirst) {
                                isFirst = false;
                                mNewsAdputer = new NewsAdputer(resultsEntityList, options_base, mLayoutUtil);
                                //监听recyclerView的上滑动的位置来进行积蓄的加载更多的数据
                                mMyRecyclerView.addOnScrollListener(new RecyclerViewOnScroll(mNewsAdputer, PageFragment.this));
                                mMyRecyclerView.setAdapter(mNewsAdputer);
                            } else {
                                mFooterLinearlayout.setVisibility(View.GONE);
                                mNewsAdputer.notif(resultsEntityList);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtil.e("新闻解析异常" + e);
                        }
                    }
                });
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(getActivity(), "获取数据失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
