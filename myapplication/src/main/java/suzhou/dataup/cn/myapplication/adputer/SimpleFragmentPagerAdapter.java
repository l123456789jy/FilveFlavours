package suzhou.dataup.cn.myapplication.adputer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import suzhou.dataup.cn.myapplication.fragment.PageFragment;
import suzhou.dataup.cn.myapplication.utiles.LogUtil;


/**
 * 作者：liujingyuan on 2015/8/31 11:21
 * 邮箱：906514731@qq.com
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"福利", "Android", "iOS", "休息视频", "拓展资源", "前端", "所有"};
    private Context context;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            //福利的界面
            case 0:
                LogUtil.e("SimpleFragmentPagerAdapter" + position);
                return PageFragment.newInstance(position + 1);
            //android资源的界面
            case 1:
                LogUtil.e("SimpleFragmentPagerAdapter" + position);
                return PageFragment.newInstance(position + 1);
            //ios
            case 2:
                LogUtil.e("SimpleFragmentPagerAdapter" + position);
                return PageFragment.newInstance(position + 1);
            //休息视频
            case 3:
                LogUtil.e("SimpleFragmentPagerAdapter" + position);
                return PageFragment.newInstance(position + 1);
            //拓展资源
            case 4:
                LogUtil.e("SimpleFragmentPagerAdapter" + position);
                return PageFragment.newInstance(position + 1);
            //前端
            case 5:
                LogUtil.e("SimpleFragmentPagerAdapter" + position);
                return PageFragment.newInstance(position + 1);
            //所有
            case 6:
                LogUtil.e("SimpleFragmentPagerAdapter" + position);
                return PageFragment.newInstance(position + 1);
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
