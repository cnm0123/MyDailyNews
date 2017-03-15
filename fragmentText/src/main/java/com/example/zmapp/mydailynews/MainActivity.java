package com.example.zmapp.mydailynews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Button button,button2,button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        fm = getSupportFragmentManager();//v4包下拿FragmentManager的方法
        ft = fm.beginTransaction();//开始一个事务
        //第一个参数是容器的ID，第二个参数是将要被添加到Activity上的Fragment对象
        ft.replace(R.id.framelayout,new FragmentA());
        ft.addToBackStack(null);//当点击返回时，可以返回到上一个碎片（栈）中
        ft.commit();//提交事务
    }

    private void initWidget(){
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button.setOnClickListener(l);
        button2.setOnClickListener(l);
        button3.setOnClickListener(l);
    }
    private void changeFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.framelayout, fragment);
        ft.commit();
    }

    private View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button:
                    changeFragment(new FragmentA());
                    break;
                case R.id.button2:
                    changeFragment(new FragmentB());
                    break;
                case R.id.button3:
                    changeFragment(new FragmentC());
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG","MainActivity-----------onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG","MainActivity-----------onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG","MainActivity-----------onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG","MainActivity-----------onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG","MainActivity-----------onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG","MainActivity-----------onRestart");
    }
}
