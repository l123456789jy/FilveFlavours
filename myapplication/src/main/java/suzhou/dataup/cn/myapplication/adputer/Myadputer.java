package suzhou.dataup.cn.myapplication.adputer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import suzhou.dataup.cn.myapplication.R;
import suzhou.dataup.cn.myapplication.bean.HomeResoutBean;

/**
 * Created by Administrator on 2015/4/13 0013.
 */
public class Myadputer extends RecyclerView.Adapter<Myadputer.ItemViewHolder> {
    public List<HomeResoutBean.ResultsEntity> resultsEntityList;
    public DisplayImageOptions options_base;

    public Myadputer(List<HomeResoutBean.ResultsEntity> resultsEntityList, DisplayImageOptions options_base) {
        this.resultsEntityList = resultsEntityList;
        this.options_base = options_base;
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
    }


    @Override
    public int getItemCount() {
        return resultsEntityList.size();

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ImageView viewById = (ImageView) itemView.findViewById(R.id.tv);
            mImageView = viewById;
        }
    }
}
