package suzhou.dataup.cn.myapplication.fragment;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.adputer.MyPagerAdapter;
import suzhou.dataup.cn.myapplication.base.BaseFragment;
import suzhou.dataup.cn.myapplication.bean.ViewPagerBean;
import suzhou.dataup.cn.myapplication.callback.MyHttpCallBcak;
import suzhou.dataup.cn.myapplication.constance.CountUri;
import suzhou.dataup.cn.myapplication.mangers.OkHttpClientManager;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;


/**
 * 新闻界面
 */
public class PageFragment extends BaseFragment {
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

    public PageFragment() {
        super(R.layout.fragment_page);
    }

    @Override
    protected void initHead() {

    }

    @Override
    protected void initContent() {
        getNewsViewPagerData();
        mConvenientBanner.startAutoScroll();
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
                mIndexTv.setText((i + 1) + "");
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    protected void isShow() {

    }

    @Override
    protected void isGone() {

    }

    //获取头部的数据的数据
    private void getNewsViewPagerData() {
        //目前不知道原因，如果这里不写成链式结构设置在子线程无效！
        stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                //在子线程中执行访问网络的操作
                OkHttpClientManager.get(CountUri.Home_pager, new MyHttpCallBcak() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(final Response response) {
                        try {
                            subscriber.onNext(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
            //设置运行执行逻辑的时候在Io线程可以执行耗时的操作,回显结果在主线程！
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            //接收到网络的数据
            @Override
            public void onNext(String s) {
                try {
                    mImageViewList.clear();
                    JSONArray mJSONArray = new JSONArray(s);
                    ViewPagerBean mviewPagerBean = mGson.fromJson(mJSONArray.getString(0), ViewPagerBean.class);
                    MyPagerAdapter mMyPagerAdapter = new MyPagerAdapter(mviewPagerBean.body.item, mLayoutUtil, getActivity(), mImageViewList, mTv, mTotalCount, mIndexTv);
                    mConvenientBanner.setAdapter(mMyPagerAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("errow" + e + "");
                }

            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "获取服务器数据失败。。", Toast.LENGTH_SHORT).show();
            }
        };
        //myObservable订阅mySubscriber
        stringObservable.subscribe(mySubscriber);

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
}
