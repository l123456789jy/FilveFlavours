package suzhou.dataup.cn.myapplication.adputer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import suzhou.dataup.cn.myapplication.fragment.PageFragment;


/**
 * 作者：liujingyuan on 2015/8/31 11:21
 * 邮箱：906514731@qq.com
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"tab1", "tab2", "tab3", "tab4"};
    private Context context;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
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
