package com.example.zmapp.mydailynews;

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

public class FragmentC extends Fragment{
    public final String TAG = "tag";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"FragmentA---------onCreateView");
        View view = inflater.inflate(R.layout.layout_fragmentc,null);
        return view;
    }
}
