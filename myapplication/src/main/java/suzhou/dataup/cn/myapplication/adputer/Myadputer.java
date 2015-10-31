package suzhou.dataup.cn.myapplication.adputer;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.InjectView;
import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.bean.HomeResoutBean;
import suzhou.dataup.cn.myapplication.contex.ApplicationData;
import suzhou.dataup.cn.myapplication.utiles.LayoutUtil;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

/**
 * Created by Administrator on 2015/4/13 0013.
 */
public class Myadputer extends RecyclerView.Adapter<Myadputer.ItemViewHolder> {
    public List<HomeResoutBean.ResultsEntity> resultsEntityList;
    public DisplayImageOptions options_base;
    public LayoutUtil layoutUtil;
    @InjectView(R.id.tv)
    ImageView mTv;


    public Myadputer(List<HomeResoutBean.ResultsEntity> resultsEntityList, DisplayImageOptions options_base, LayoutUtil layoutUtil) {
        this.resultsEntityList = resultsEntityList;
        this.options_base = options_base;
        this.layoutUtil = layoutUtil;
    }

    //记住在使用RecyclerView的时候要主页这里的返回类型！ItemViewHolder
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.fragment_weal_item, null);
        return new ItemViewHolder(view);//创建一个viewholder,然后将view传递进来！
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int position) {
        ImageLoader.getInstance().displayImage(resultsEntityList.get(position).url, viewHolder.mImageView, options_base);
        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(resultsEntityList.get(position).url);
        initLocation(viewHolder, bitmap);
    }

    private void initLocation(ItemViewHolder viewHolder, Bitmap mbitmap) {
        LogUtil.e("viewHolder.mCardView" + viewHolder.mCardView);
        layoutUtil.drawViewRBLayout(viewHolder.mImageView, 1f, ((mbitmap.getHeight()) / ApplicationData.screenHeight), 0.020f, 0.020f, 0.020f, 0f);
    }
    @Override
    public int getItemCount() {
        return resultsEntityList.size();
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
}
