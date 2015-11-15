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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import suzhou.dataup.cn.myapplication.bean.CountViewPagerBean;
import suzhou.dataup.cn.myapplication.contex.ApplicationData;
import suzhou.dataup.cn.myapplication.utiles.LayoutUtil;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;
import uk.co.senab.photoview.PhotoView;

/**
 * 图片饿详情页面
 */
public class MyCountPagerAdapter extends PagerAdapter {
    public List<CountViewPagerBean.BodyEntity.SlidesEntity> EntyList;
    public FragmentActivity activity;
    public List<ImageView> imageViewList;
    public LayoutUtil layoutUtil;
    public TextView totalCount;
    public DisplayImageOptions options_base;

    public MyCountPagerAdapter(List<CountViewPagerBean.BodyEntity.SlidesEntity> slides, LayoutUtil layoutUtil, DisplayImageOptions options_base) {
        this.EntyList = slides;
        this.layoutUtil = layoutUtil;
        this.options_base = options_base;
        imageViewList = new ArrayList<>();
        for (CountViewPagerBean.BodyEntity.SlidesEntity slidesEntity : EntyList) {
            PhotoView photoView = new PhotoView(ApplicationData.context);
            photoView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewList.add(photoView);
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
        ((ViewPager) arg0).addView(imageViewList.get(arg1), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageLoader.getInstance().displayImage(EntyList.get(arg1).image, imageViewList.get(arg1), options_base);
        //tv.setText(EntyList.get(arg1).title);
        //   totalCount.setText("/" + EntyList.size());
        LogUtil.e("EntyList.get(arg1).image" + EntyList.get(arg1).image);
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