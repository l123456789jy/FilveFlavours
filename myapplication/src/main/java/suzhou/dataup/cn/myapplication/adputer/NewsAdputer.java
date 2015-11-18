package suzhou.dataup.cn.myapplication.adputer;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.activity.CountActivity;
import suzhou.dataup.cn.myapplication.bean.NewsBean;
import suzhou.dataup.cn.myapplication.bean.ViewPagerBean;
import suzhou.dataup.cn.myapplication.constance.ConstanceData;
import suzhou.dataup.cn.myapplication.contex.ApplicationData;
import suzhou.dataup.cn.myapplication.utiles.LayoutUtil;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

/**
 * 安卓的适配器
 */
public class NewsAdputer extends RecyclerView.Adapter<NewsAdputer.ItemViewHolder> {
    //区分显示不同的条目
    private static final int IS_HEADER = 2;
    private static final int IS_FOOTER = 3;
    private static final int IS_NORMAL = 1;
    public List<NewsBean.BodyEntity.ItemEntity> resultsEntityList;
    public DisplayImageOptions options_base;
    public LayoutUtil layoutUtil;
    String tempTime = "first";
    public List<ImageView> imageViewList;
    MyPagerAdapter mMyPagerAdapter;
    public List<ViewPagerBean.BodyEntity.ItemEntity> EntyList;

    public NewsAdputer(List<ViewPagerBean.BodyEntity.ItemEntity> item, List<ImageView> imageViewList, List<NewsBean.BodyEntity.ItemEntity> resultsEntityList, DisplayImageOptions options_base, LayoutUtil layoutUtil) {
        this.resultsEntityList = resultsEntityList;
        this.options_base = options_base;
        this.layoutUtil = layoutUtil;
        this.imageViewList = imageViewList;
        this.EntyList = item;

    }

    //记住在使用RecyclerView的时候要主页这里的返回类型！ItemViewHolder
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        LogUtil.e("viewType" + viewType);
        //代表是头部的viewpager
        if (viewType == IS_NORMAL) {
            view = View.inflate(parent.getContext(), R.layout.new_pager_item, null);
        } else {
            view = View.inflate(parent.getContext(), R.layout.fragment_news_item, null);
        }
        return new ItemViewHolder(view, viewType);//创建一个viewholder,然后将view传递进来！
    }

    //区分不同条目
    @Override
    public int getItemViewType(int position) {
        //代表是最后一个条目添加footview
        LogUtil.e("position" + position);
        if (position == 0) {
            return IS_NORMAL;
        } else {
            return super.getItemViewType(position);
        }

    }

    @Override
    public void onBindViewHolder(final ItemViewHolder viewHolder, int position) {
        int itemViewType = viewHolder.getItemViewType();
        if (itemViewType == IS_NORMAL) {
            imageViewList.clear();
            mMyPagerAdapter = new MyPagerAdapter(EntyList, layoutUtil, ApplicationData.context, imageViewList, viewHolder.mTextView2, viewHolder.mtotal_count, viewHolder.mindex_tv, options_base);
            viewHolder.mAutoScrollViewPager.setAdapter(mMyPagerAdapter);
            viewHolder.mTextView2.setText(EntyList.get(0).title);
            viewHolder.mAutoScrollViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
                @Override
                public void onPageSelected(int position) {
                    if (null != viewHolder.mindex_tv) {
                        viewHolder.mindex_tv.setText((position + 1) + "");
                        viewHolder.mTextView2.setText(EntyList.get(position).title);
                    }
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        } else {
            ImageLoader.getInstance().displayImage(resultsEntityList.get(position).thumbnail, viewHolder.mIv, options_base);
            viewHolder.mTextView.setText(resultsEntityList.get(position).title);
            layoutUtil.drawViewFramLayout(viewHolder.rl, 1f, 0f, 0f, 0f);
            viewHolder.mIv.setTag(resultsEntityList.get(position).commentsUrl + "," + resultsEntityList.get(position).title);
            viewHolder.mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String countUri = v.getTag().toString();
                    Intent mIntent = new Intent(ApplicationData.context, CountActivity.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mIntent.putExtra(ConstanceData.COUNT_URI, countUri);
                    ApplicationData.context.startActivity(mIntent);
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        return resultsEntityList.size();
    }

    public void notif(List<NewsBean.BodyEntity.ItemEntity> mnewsBean) {
        this.resultsEntityList = mnewsBean;
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        ImageView mIv;
        RelativeLayout footer_rl;
        RelativeLayout rl;
        AutoScrollViewPager mAutoScrollViewPager;
        TextView mTextView2;
        TextView mtotal_count;
        TextView mindex_tv;

        public ItemViewHolder(View itemView, int viewType) {
            super(itemView);
            LogUtil.e("viewType" + viewType);
            if (viewType == IS_NORMAL) {//就要使用vipager的布局
                footer_rl = (RelativeLayout) itemView.findViewById(R.id.rl);
                mAutoScrollViewPager = (AutoScrollViewPager) itemView.findViewById(R.id.convenientBanner);
                mTextView2 = (TextView) itemView.findViewById(R.id.tv);
                mtotal_count = (TextView) itemView.findViewById(R.id.total_count);
                mindex_tv = (TextView) itemView.findViewById(R.id.index_tv);
            } else {
                mTextView = (TextView) itemView.findViewById(R.id.tv);
                mIv = (ImageView) itemView.findViewById(R.id.iv);
                rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            }
        }
    }

}
