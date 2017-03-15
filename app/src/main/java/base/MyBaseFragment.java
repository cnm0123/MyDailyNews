package base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/2/15 0015.
 */

public class MyBaseFragment extends Fragment{
    public View rootView;

    //v4包的BUG,需移除当前视图，防止重复加载相同视图使得程序闪退
    @Override
    public void onDestroyView() {
        ((ViewGroup) rootView.getParent()).removeView(rootView);
        super.onDestroyView();
    }
}
