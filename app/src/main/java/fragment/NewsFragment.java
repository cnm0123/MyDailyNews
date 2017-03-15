package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.zmapp.mydailynews.R;

import java.util.ArrayList;
import java.util.List;

import adapter.NewsFragmentAdapter;

/**
 * Created by Administrator on 2017/2/15 0015.
 */

public class NewsFragment extends Fragment{
    private View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NewsFragmentAdapter adapter;
    private List<String> mTitles;//Tab显示的文字
    private List<Fragment> mFragments;//ViewPager显示的Fragment
    private String []mTypes = {"top","shehui","guonei","guoji","yule",
            "tiyu","junshi","keji","caijing","shishang"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news,null);
        initWidget();
        initData();
        return view;
    }
    //初始化控件
    private void initWidget(){
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout_newsfragment);
        mViewPager = (ViewPager)view.findViewById(R.id.viewPager_newsfragment);

    }
    private void initData() {
        mTitles = new ArrayList<String>();
        mTitles.add("头条");
        mTitles.add("社会");
        mTitles.add("国内");
        mTitles.add("国际");
        mTitles.add("娱乐");
        mTitles.add("体育");
        mTitles.add("军事");
        mTitles.add("科技");
        mTitles.add("财经");
        mTitles.add("时尚");

        mFragments = new ArrayList<Fragment>();
        for(int i = 0;i < 10;i++){
            NewsListFragment fragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type",mTypes[i]);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
        adapter = new NewsFragmentAdapter(getFragmentManager());
        adapter.addFragments(mFragments);
        adapter.addTitles(mTitles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);//把TabLayout和ViewPager关联上
    }
}
