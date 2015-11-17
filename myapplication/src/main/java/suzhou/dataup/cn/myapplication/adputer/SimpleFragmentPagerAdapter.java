package suzhou.dataup.cn.myapplication.adputer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import suzhou.dataup.cn.myapplication.fragment.AndroidFragment;
import suzhou.dataup.cn.myapplication.fragment.PageFragment;
import suzhou.dataup.cn.myapplication.fragment.RestViedoFragment;
import suzhou.dataup.cn.myapplication.fragment.WealFragment;


/**
 * 作者：liujingyuan on 2015/8/31 11:21
 * 邮箱：906514731@qq.com
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"福利", "Android", "新闻", "休息视频"};
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
                WealFragment mWealFragment = new WealFragment();
                return mWealFragment;
            //android资源的界面
            case 1:
                AndroidFragment mAndroidFragment = new AndroidFragment();
                return mAndroidFragment;
            //新闻界面
            case 2:
                PageFragment mPageFragment = new PageFragment();
                return mPageFragment;
            //休息视频
            case 3:
                RestViedoFragment mRestViedoFragment = new RestViedoFragment();
                return mRestViedoFragment;
            //拓展资源
            case 4:

                //前端
            case 5:

                //所有
            case 6:

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
