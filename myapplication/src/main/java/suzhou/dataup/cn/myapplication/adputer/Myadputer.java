package suzhou.dataup.cn.myapplication.adputer;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.InjectView;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.activity.AppealActivity;
import suzhou.dataup.cn.myapplication.bean.HomeResoutBean;
import suzhou.dataup.cn.myapplication.constance.ConstanceData;
import suzhou.dataup.cn.myapplication.contex.ApplicationData;
import suzhou.dataup.cn.myapplication.utiles.LayoutUtil;

/**
 *
 */
public class Myadputer extends RecyclerView.Adapter<Myadputer.ItemViewHolder> {

    public List<HomeResoutBean.ResultsEntity> resultsEntityList;
    public DisplayImageOptions options_base;
    public LayoutUtil layoutUtil;
    @InjectView(R.id.tv)
    ImageView mTv;
    private int lastPosition = -1;

    public Myadputer(List<HomeResoutBean.ResultsEntity> resultsEntityList, DisplayImageOptions options_base, LayoutUtil layoutUtil) {
        this.resultsEntityList = resultsEntityList;
        this.options_base = options_base;
        this.layoutUtil = layoutUtil;
        setHasStableIds(true);
    }

    //记住在使用RecyclerView的时候要主页这里的返回类型！ItemViewHolder
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.fragment_weal_item, null);
        return new ItemViewHolder(view);//创建一个viewholder,然后将view传递进来！
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int position) {
        viewHolder.mImageView.setTag(position + "");
        ImageLoader.getInstance().displayImage(resultsEntityList.get(position).url, viewHolder.mImageView, options_base);
        layoutUtil.drawViewLayout(viewHolder.mImageView, 0.4f, 0.4f, 0f, 0f);
        setAnimation(viewHolder.mCardView, position);
        viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ApplicationData.context, AppealActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra(ConstanceData.IMAGEURI, resultsEntityList.get(Integer.parseInt(v.getTag().toString())).url);
                ApplicationData.context.startActivity(mIntent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return resultsEntityList.size();
    }

    //播放完毕要及时的消除动画
    @Override
    public void onViewDetachedFromWindow(ItemViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mCardView.clearAnimation();
    }

    public void restartAnimationPostion() {
        lastPosition = -1;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public CardView mCardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ImageView viewById = (ImageView) itemView.findViewById(R.id.tv);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mImageView = viewById;
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
                    .anim.item_reversal);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
