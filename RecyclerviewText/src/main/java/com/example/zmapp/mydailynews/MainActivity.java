package com.example.zmapp.mydailynews;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<JavaBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_newslist);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(manager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MyAdapter();
        initData();
        adapter.addDatas(datas);
        recyclerView.setAdapter(adapter);
    }
    private void initData(){
        datas = new ArrayList<JavaBean>();
        for (int i = 0;i < 20;i++){
            JavaBean data = new JavaBean();
            data.bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            data.title = "哈哈阿斯利康戴假发发到你垃圾分类";
            datas.add(data);
        }
    }
}
