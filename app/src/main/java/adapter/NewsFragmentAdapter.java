package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class NewsFragmentAdapter extends FragmentPagerAdapter{
    private List<Fragment> mFragments ;
    private List<String> mTitles;

    public NewsFragmentAdapter(FragmentManager fm) {
        super(fm);

    }
    public void addFragments(List<Fragment> mFragments){
        this.mFragments = mFragments;
    }
    public void addTitles(List<String> mTitles){
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
