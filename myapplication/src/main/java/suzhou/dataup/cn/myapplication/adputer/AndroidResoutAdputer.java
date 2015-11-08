package suzhou.dataup.cn.myapplication.adputer;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.activity.CountActivity;
import suzhou.dataup.cn.myapplication.bean.HomeResoutBean;
import suzhou.dataup.cn.myapplication.constance.ConstanceData;
import suzhou.dataup.cn.myapplication.contex.ApplicationData;
import suzhou.dataup.cn.myapplication.utiles.LayoutUtil;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

/**
 * 安卓的适配器
 */
public class AndroidResoutAdputer extends RecyclerView.Adapter<AndroidResoutAdputer.ItemViewHolder> {
    //区分显示不同的条目
    private static final int IS_HEADER = 2;
    private static final int IS_FOOTER = 3;
    private static final int IS_NORMAL = 1;
    public List<HomeResoutBean.ResultsEntity> resultsEntityList;
    public DisplayImageOptions options_base;
    public LayoutUtil layoutUtil;
    String tempTime = "first";
    boolean isFirst = true;

    public AndroidResoutAdputer(List<HomeResoutBean.ResultsEntity> resultsEntityList, DisplayImageOptions options_base, LayoutUtil layoutUtil) {
        this.resultsEntityList = resultsEntityList;
        this.options_base = options_base;
        this.layoutUtil = layoutUtil;
    }

    //记住在使用RecyclerView的时候要主页这里的返回类型！ItemViewHolder
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        //如果是最后一个条目就显示出底部的布局
        if (viewType == IS_FOOTER) {
            view = createLoadMoreView();
        } else {
            view = View.inflate(parent.getContext(), R.layout.fragment_android_item, null);
        }
        return new ItemViewHolder(view, viewType);//创建一个viewholder,然后将view传递进来！
    }

    //区分不同条目
    @Override
    public int getItemViewType(int position) {
        //代表是最后一个条目添加footview
        LogUtil.e("position" + position);
        LogUtil.e("resultsEntityList.size()==" + resultsEntityList.size());
        if (position == resultsEntityList.size() + 1) {
            return IS_FOOTER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int position) {
        int itemViewType = viewHolder.getItemViewType();
        if (itemViewType != IS_FOOTER) {
            viewHolder.time_tv.setText("更新时间:  " + resultsEntityList.get(position).updatedAt.split("T")[0]);
            viewHolder.mTextView.setTag(resultsEntityList.get(position).url + "," + resultsEntityList.get(position).desc);
            viewHolder.mTextView.setText(resultsEntityList.get(position).desc);
            layoutUtil.drawViewLayout(viewHolder.mRl, 1f, 0f, 0.040f, 0f);
            viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String countUri = v.getTag().toString();
                    Intent mIntent = new Intent(ApplicationData.context, CountActivity.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mIntent.putExtra(ConstanceData.COUNT_URI, countUri);
                    ApplicationData.context.startActivity(mIntent);
                }
            });
        } else {


        }

    }

    @Override
    public int getItemCount() {
        return resultsEntityList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        RelativeLayout mRl;
        TextView time_tv;
        FrameLayout footer_layout;

        public ItemViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType != IS_FOOTER) {
                mTextView = (TextView) itemView.findViewById(R.id.textView);
                mRl = (RelativeLayout) itemView.findViewById(R.id.rl);
                time_tv = (TextView) itemView.findViewById(R.id.time_tv);
            } else {
                footer_layout = (FrameLayout) itemView.findViewById(R.id.footer_layout);
            }
        }
    }

    public FrameLayout getFootRootView() {

        return null;
    }

    //添加底部加载更多的按钮
    private View createLoadMoreView() {
        View loadMoreView = LayoutInflater.from(ApplicationData.context).inflate(R.layout.view_load_more, null);
        return loadMoreView;
    }
}
