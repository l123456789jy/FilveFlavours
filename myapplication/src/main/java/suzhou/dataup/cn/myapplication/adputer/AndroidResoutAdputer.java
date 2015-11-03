package suzhou.dataup.cn.myapplication.adputer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.bean.HomeResoutBean;
import suzhou.dataup.cn.myapplication.utiles.LayoutUtil;

/**
 * 安卓的适配器
 */
public class AndroidResoutAdputer extends RecyclerView.Adapter<AndroidResoutAdputer.ItemViewHolder> {

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
        View view = View.inflate(parent.getContext(), R.layout.fragment_android_item, null);
        return new ItemViewHolder(view);//创建一个viewholder,然后将view传递进来！
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int position) {
        viewHolder.time_tv.setText("更新时间:  " + resultsEntityList.get(position).updatedAt.split("T")[0]);
        viewHolder.mTextView.setTag(resultsEntityList.get(position).url);
        viewHolder.mTextView.setText(resultsEntityList.get(position).desc);
        layoutUtil.drawViewLayout(viewHolder.mRl, 1f, 0f, 0.040f, 0f);
        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countUri = v.getTag().toString();
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultsEntityList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        RelativeLayout mRl;
        TextView time_tv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
            mRl = (RelativeLayout) itemView.findViewById(R.id.rl);
            time_tv = (TextView) itemView.findViewById(R.id.time_tv);
        }
    }
}
