package suzhou.dataup.cn.myapplication.fragment;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import suzhou.dataup.cn.myapplication.adputer.Myadputer;
import suzhou.dataup.cn.myapplication.base.BaseFragment;
import suzhou.dataup.cn.myapplication.bean.HomeResoutBean;
import suzhou.dataup.cn.myapplication.callback.LodeMoreCallBack;
import suzhou.dataup.cn.myapplication.callback.OkCallback;
import suzhou.dataup.cn.myapplication.constance.CountUri;
import suzhou.dataup.cn.myapplication.listener.RecyclerViewOnScroll;
import suzhou.dataup.cn.myapplication.mangers.OkHttpClientManager;
import suzhou.dataup.cn.myapplication.paser.OkJsonParser;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;
import suzhou.dataup.cn.myapplication.utiles.SwipContainerUtiles;
import suzhou.dataup.cn.myapplication.utiles.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HotBasFragmentImp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HotBasFragmentImp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotBasFragmentImp extends BaseFragment implements LodeMoreCallBack {
    private int layoutId; // 当前界面对应的布局
    int lastVisibleItem = 0;
    int index = 1;
    int temp = 0;
    @InjectView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;
    Myadputer mMyadputer;
    List<HomeResoutBean.ResultsEntity> mResultsEntityList = new ArrayList<>();
    GridLayoutManager mLayoutManager;//创建一个瀑布流的布局
    boolean isFirstLoda = true;
    @InjectView(R.id.load_more_pb)
    ProgressBar mLoadMorePb;
    @InjectView(R.id.load_more_tv)
    TextView mLoadMoreTv;
    @InjectView(R.id.footer_linearlayout)
    LinearLayout mFooterLinearlayout;
    int postion = 0;
    public HotBasFragmentImp(int layoutId) {
        super(layoutId);
    }

    @Override
    protected void initHead() {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initContent() {
        // 创建一个线性布局管理器
//        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //这里可以指定他的方式
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);//设置线性的管理器！
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        mMyadputer = new Myadputer(mResultsEntityList, options_base, mLayoutUtil);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
        //监听recyclerView的上滑动的位置来进行积蓄的加载更多的数据
        recyclerView.addOnScrollListener(new RecyclerViewOnScroll(mMyadputer, this));
    }

    @Override
    protected void initLocation() {
        //利用反射进行设置自动刷新！
        SwipContainerUtiles.setRefreshing(mSwipeContainer, true, true);
    }
    @Override
    protected void initLogic() {
    }

    @Override
    protected void isShow() {

    }

    @Override
    protected void isGone() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("xioahuile");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //获取福利的数据
    private void getData(int index) {
        OkHttpClientManager.getResoutAnty(CountUri.BASE_URI + "/福利/10/" + index + "", new OkCallback<HomeResoutBean>(new OkJsonParser<HomeResoutBean>() {
        }) {
            @Override
            public void onSuccess(int code, HomeResoutBean homeResoutBean) {
                if (null != homeResoutBean) {
                    List<HomeResoutBean.ResultsEntity> results = homeResoutBean.results;
                    for (HomeResoutBean.ResultsEntity result : results) {
                        mResultsEntityList.add(result);
                    }
                    if (isFirstLoda) {
                        mMyadputer.restartAnimationPostion();
                        mFooterLinearlayout.setVisibility(View.GONE);
                        mSwipeContainer.setRefreshing(false);//刷新完毕!
                        recyclerView.setAdapter(mMyadputer);
                        isFirstLoda = false;
                        // mMyadputer.notifyItemRangeChanged(0,10);
                    } else {
                        mFooterLinearlayout.setVisibility(View.GONE);
                        mSwipeContainer.setRefreshing(false);//刷新完毕!
                        mMyadputer.notifyItemRangeInserted(mMyadputer.getItemCount() - 10, mResultsEntityList.size());
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
        //请求数据
        index++;
        getData(index);
        mFooterLinearlayout.setVisibility(View.VISIBLE);
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }
}
