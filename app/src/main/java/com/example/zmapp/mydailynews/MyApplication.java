package com.example.zmapp.mydailynews;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/2/17 0017.
 */
//该类会在程序一启动就去加载或初始化一些东西
public class MyApplication extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
