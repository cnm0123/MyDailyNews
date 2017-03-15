package adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/13 0013.
 */

public class MyGuideAcyivity extends PagerAdapter{
    private ArrayList<View> views = new ArrayList<View>();
    public void addViewToAdapter(View view){
        if (view != null) {
            views.add(view);
        }
    }

    @Override
    public int getCount() {
        return views.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//		super.destroyItem(container, position, object);
        //获取当前显示的View
        View view = views.get(position);
        //从ViewGroup里移除
        container.removeView(view);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //获取当前位置显示的View
        View view = views.get(position);
        //添加到ViewGroup里
        container.addView(view);
        return view;
    }
}
