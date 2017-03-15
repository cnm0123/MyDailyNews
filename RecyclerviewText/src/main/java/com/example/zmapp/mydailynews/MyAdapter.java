package com.example.zmapp.mydailynews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<JavaBean> datas;

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        //构造方法内的参数View通常指的是RecyclerView内item的最外层布局
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
    //初始化ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_recyclerview,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    //主要用于设置数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JavaBean data = datas.get(position);
        holder.imageView.setImageBitmap(data.bitmap);
        holder.textView.setText(data.title);
    }
    //获取item的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    public void addDatas(List<JavaBean> datas){
        this.datas = datas;
    }
}
