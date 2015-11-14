package suzhou.dataup.cn.myapplication.adputer;

/**
 * 作者：liujingyuan on 2015/11/14 23:54
 * 邮箱：906514731@qq.com
 */

import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import suzhou.dataup.cn.myapplication.bean.ViewPagerBean;
import suzhou.dataup.cn.myapplication.utiles.LayoutUtil;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;

/**
 * ViewPager适配器
 */
public class MyPagerAdapter extends PagerAdapter {
    public List<ViewPagerBean.BodyEntity.ItemEntity> EntyList;
    public LayoutUtil mlayoutUtil;
    public FragmentActivity activity;
    public List<ImageView> imageViewList;
    public TextView tv;
    public TextView totalCount;
    public TextView indexTv;

    public MyPagerAdapter(List<ViewPagerBean.BodyEntity.ItemEntity> item, LayoutUtil layoutUtil, FragmentActivity activity, List<ImageView> imageViewList, TextView tv, TextView totalCount, TextView indexTv) {
        this.EntyList = item;
        this.mlayoutUtil = layoutUtil;
        this.activity = activity;
        this.tv = tv;
        this.totalCount = totalCount;
        this.indexTv = indexTv;
        this.imageViewList = imageViewList;
        for (ViewPagerBean.BodyEntity.ItemEntity itemEntity : item) {
            ImageView imageView = new ImageView(activity);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewList.add(imageView);
        }
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(imageViewList.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(imageViewList.get(arg1), 0);
        ImageLoader.getInstance().displayImage(EntyList.get(arg1).thumbnail, imageViewList.get(arg1));
        tv.setText(EntyList.get(arg1).title);
        totalCount.setText("/" + EntyList.size());

        // imageViewList.get(arg1).setTag(EntyList.get(arg1).contenturl + "");
       /* //vipager里面的点击事件
        mListViews.get(arg1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(ExchangeCenterActivity.this, CountUriActivity.class);
                mIntent.putExtra("counturi", v.getTag().toString());
                startActivity(mIntent);
            }
        });*/
        return imageViewList.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }
}