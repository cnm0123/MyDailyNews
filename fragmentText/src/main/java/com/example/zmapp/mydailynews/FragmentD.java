package com.example.zmapp.mydailynews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Administrator on 2017/2/15 0015.
 */

public class FragmentD extends Fragment {
    public final String TAG = "tag";
    private Button button,button2,button3;
    private OnButtonClickListener listener = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof OnButtonClickListener){
            listener = (OnButtonClickListener)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"FragmentA---------onCreateView");
        View view = inflater.inflate(R.layout.layout_fragmentd,null);
        button = (Button) view.findViewById(R.id.button);
        button2 = (Button) view.findViewById(R.id.button2);
        button3 = (Button) view.findViewById(R.id.button3);
        button.setOnClickListener(l);
        button2.setOnClickListener(l);
        button3.setOnClickListener(l);
        return view;
    }
    private View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button:
                    if (listener != null){
                        listener.onButtonClick(button);
                    }
                    break;
                case R.id.button2:
                    if (listener != null){
                        listener.onButtonClick(button2);
                    }
                    break;
                case R.id.button3:
                    if (listener != null){
                        listener.onButtonClick(button3);
                    }
                    break;
            }
        }
    };
    //方法回调
    public interface OnButtonClickListener{
        public void onButtonClick(View view);

    }
}
