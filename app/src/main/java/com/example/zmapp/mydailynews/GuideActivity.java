package com.example.zmapp.mydailynews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import adapter.MyGuideAcyivity;
import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity {
    @Bind(R.id.viewPager)ViewPager viewPager;//绑定资源ID
    @Bind({R.id.iv1_guide,R.id.iv2_guide,R.id.iv3_guide,R.id.iv4_guide})
    ImageView[] imgs = new ImageView[4];
    private MyGuideAcyivity adapter;
    private boolean isScrolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);//初始化ButterKnife，将视图和控件进行绑定
        isFirstRun();
        initWidget();
    }
    private void isFirstRun(){
        SharedPreferences sp = getSharedPreferences("isFirstRun",
                Context.MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst",true);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isFirst",false);
        editor.commit();
        if (isFirst != true){
            Intent intent = new Intent(GuideActivity.this,LaunchActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void initWidget(){
        viewPager.addOnPageChangeListener(listener);
        adapter = new MyGuideAcyivity();
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.itemguide1,null);
        View view2 = inflater.inflate(R.layout.itemguide2,null);
        View view3 = inflater.inflate(R.layout.itemguide3,null);
        View view4 = inflater.inflate(R.layout.itemguide4,null);
        adapter.addViewToAdapter(view1);
        adapter.addViewToAdapter(view2);
        adapter.addViewToAdapter(view3);
        adapter.addViewToAdapter(view4);
        viewPager.setAdapter(adapter);
    }

    private ViewPager.OnPageChangeListener listener = new
            ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < imgs.length; i++) {
                imgs[i].setImageResource(R.drawable.adware_style_default);
            }
            imgs[position].setImageResource(R.drawable.adware_style_selected);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state){
                case ViewPager.SCROLL_STATE_DRAGGING://正在滑动
                    isScrolled = false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING://滑动完毕
                    isScrolled = true;
                    break;
                case ViewPager.SCROLL_STATE_IDLE://还没有滑呢
                    if (viewPager.getCurrentItem() == adapter.getCount() - 1){
                        Intent intent = new Intent(GuideActivity.this,
                                LaunchActivity.class);
                        startActivity(intent);
                        GuideActivity.this.finish();
                    }
                    break;
            }
        }
    };
}
