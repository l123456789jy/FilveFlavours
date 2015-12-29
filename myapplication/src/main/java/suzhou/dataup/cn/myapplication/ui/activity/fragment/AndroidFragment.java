package suzhou.dataup.cn.myapplication.ui.activity.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.ui.activity.adapter.AndroidResoutAdputer;
import suzhou.dataup.cn.myapplication.base.BaseFragment;
import suzhou.dataup.cn.myapplication.bean.HomeResoutBean;
import suzhou.dataup.cn.myapplication.callback.LodeMoreCallBack;
import suzhou.dataup.cn.myapplication.callback.OkCallback;
import suzhou.dataup.cn.myapplication.constance.CountUri;
import suzhou.dataup.cn.myapplication.listener.RecyclerViewOnScroll;
import suzhou.dataup.cn.myapplication.mangers.OkHttpClientManager;
import suzhou.dataup.cn.myapplication.paser.OkJsonParser;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;
import suzhou.dataup.cn.myapplication.utiles.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AndroidFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AndroidFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 安卓控件的界面的界面
 */
public class AndroidFragment extends BaseFragment implements LodeMoreCallBack {
    int lastVisibleItem = 0;
    int index = 1;
    int temp = 0;
    @InjectView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;
    AndroidResoutAdputer mMyadputer;
    List<HomeResoutBean.ResultsEntity> mResultsEntityList = new ArrayList<>();
    boolean isFirstLoda = true;
    @InjectView(R.id.load_more_pb)
    ProgressBar mLoadMorePb;
    @InjectView(R.id.load_more_tv)
    TextView mLoadMoreTv;
    @InjectView(R.id.footer_linearlayout)
    LinearLayout mFooterLinearlayout;

    public AndroidFragment() {
        super(R.layout.fragment_weal);
    }

    @Override
    protected void initHead() {
    }

    @Override
    protected void initContent() {
        // 创建一个线性布局管理器
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);//设置线性的管理器！
        //设置刷新时的不同的颜色！
        mSwipeContainer.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        //google官方的下拉刷新！
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastVisibleItem = 0;
                isFirstLoda = true;
                mResultsEntityList.clear();
                index = 1;
                getData(index);

            }
        });
        mMyadputer = new AndroidResoutAdputer(mResultsEntityList, options_base, mLayoutUtil);
        //监听recyclerView的上滑动的位置来进行积蓄的加载更多的数据
        recyclerView.addOnScrollListener(new RecyclerViewOnScroll(mMyadputer, this));
    }

    @Override
    protected void initLocation() {
        getData(index);
    }

    @Override
    protected void initLogic() {
    }

    @Override
    protected void isShow() {
        if (mMyadputer != null) {
            lastVisibleItem = 0;
            isFirstLoda = true;
            mResultsEntityList.clear();
            index = 1;
            getData(index);
            LogUtil.e("可见了");
        }
    }

    @Override
    protected void isGone() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //获取Android的数据
    private void getData(int index) {
        OkHttpClientManager.getResoutAnty(CountUri.BASE_URI + "/Android/20/" + index + "", new OkCallback<HomeResoutBean>(new OkJsonParser<HomeResoutBean>() {
        }) {
            @Override
            public void onSuccess(int code, HomeResoutBean homeResoutBean) {
                if (null != homeResoutBean) {
                    List<HomeResoutBean.ResultsEntity> results = homeResoutBean.results;
                    for (HomeResoutBean.ResultsEntity result : results) {
                        mResultsEntityList.add(result);
                    }
                    if (isFirstLoda) {
                        mFooterLinearlayout.setVisibility(View.GONE);
                        mSwipeContainer.setRefreshing(false);//刷新完毕!
                        recyclerView.setAdapter(mMyadputer);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        isFirstLoda = false;
                    } else {
                        mFooterLinearlayout.setVisibility(View.GONE);
                        mSwipeContainer.setRefreshing(false);//刷新完毕!
                        mMyadputer.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Throwable e) {
                mSwipeContainer.setRefreshing(false);//刷新完毕!
                ToastUtils.show("获取服务端数据失败");
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

    //监听加载更多
    @Override
    public void LodeMore() {
        mSwipeContainer.setRefreshing(true);//刷新完毕!
        //请求数据
        index++;
        getData(index);
        mFooterLinearlayout.setVisibility(View.VISIBLE);
    }
}
