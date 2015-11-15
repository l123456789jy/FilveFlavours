package suzhou.dataup.cn.myapplication.adputer;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.activity.CountActivity;
import suzhou.dataup.cn.myapplication.bean.NewsBean;
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

    public NewsAdputer(List<NewsBean.BodyEntity.ItemEntity> resultsEntityList, DisplayImageOptions options_base, LayoutUtil layoutUtil) {
        this.resultsEntityList = resultsEntityList;
        this.options_base = options_base;
        this.layoutUtil = layoutUtil;
    }

    //记住在使用RecyclerView的时候要主页这里的返回类型！ItemViewHolder
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = View.inflate(parent.getContext(), R.layout.fragment_news_item, null);
        return new ItemViewHolder(view, viewType);//创建一个viewholder,然后将view传递进来！
    }

    //区分不同条目
    @Override
    public int getItemViewType(int position) {
        //代表是最后一个条目添加footview
        LogUtil.e("position" + position);
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int position) {
        int itemViewType = viewHolder.getItemViewType();
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
        FrameLayout footer_layout;
        RelativeLayout rl;

        public ItemViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType != IS_FOOTER) {
                mTextView = (TextView) itemView.findViewById(R.id.tv);
                mIv = (ImageView) itemView.findViewById(R.id.iv);
                rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            } else {
                footer_layout = (FrameLayout) itemView.findViewById(R.id.footer_layout);
            }
        }
    }

}
