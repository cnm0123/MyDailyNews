package com.example.zmapp.mydailynews;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/2/15 0015.
 */

public class FragmentA extends Fragment{
    public final String TAG = "tag";
    private View view;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"FragmentA---------onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"FragmentA---------onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"FragmentA---------onCreateView");
        View view = inflater.inflate(R.layout.layout_fragmenta,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"FragmentA---------onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"FragmentA---------onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"FragmentA---------onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"FragmentA---------onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"FragmentA---------onStop");
    }

    //v4包的BUG，需移除当前视图，防止重复加载相同视图使得程序闪退
    @Override
    public void onDestroyView() {
//        ((ViewGroup)view.getParent()).removeView(view);
        super.onDestroyView();
        Log.d(TAG,"FragmentA---------onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"FragmentA---------onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"FragmentA---------onDetach");
    }
}
