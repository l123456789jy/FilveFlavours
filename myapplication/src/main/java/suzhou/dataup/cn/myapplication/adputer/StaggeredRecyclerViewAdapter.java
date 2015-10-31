package suzhou.dataup.cn.myapplication.adputer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import suzhou.dataup.cn.myapplication.R;


/**
 * 瀑布流的适配器
 * Created by xm on 2015/8/3.
 */
public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;

    private LayoutInflater mInflater = null;

    public StaggeredRecyclerViewAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //inflate方法的第二个参数一定要写viewGroup，不然item的宽度是固定的。并不等于屏幕宽度
        View view = mInflater.inflate(R.layout.fragment_weal_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        //瀑布流 动态设置itemview的布局高度
        if (i % 3 == 0) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) myViewHolder.itemView.getLayoutParams();
            params.height = 250;
            myViewHolder.itemView.setLayoutParams(params);
        } else if (i % 3 == 1) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) myViewHolder.itemView.getLayoutParams();
            params.height = 200;
            myViewHolder.itemView.setLayoutParams(params);
        } else {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) myViewHolder.itemView.getLayoutParams();
            params.height = 150;
            myViewHolder.itemView.setLayoutParams(params);
        }

        myViewHolder.tv.setText("   RecyclerView   " + i);
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv)
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

