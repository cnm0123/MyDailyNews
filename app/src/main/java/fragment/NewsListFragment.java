package fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zmapp.mydailynews.MainActivity;
import com.example.zmapp.mydailynews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapter.NewsListAdapter;
import config.NewsConfig;
import entity.NewsInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class NewsListFragment extends Fragment{
    private View view;
    private String mType;
    private RecyclerView mRecyclerView;
    private String newsUrl;
    private String jsonData;//网络请求返回的JSON数据
    private List<NewsInfo> datas;
    private NewsListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_list,null);
        mType = getArguments().getString("type");
        initWidget();
        return view;
    }
    public Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (msg.what == 11){
                datas = (List<NewsInfo>) msg.obj;
                adapter.addDatas(datas);
                adapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(adapter);
            }
        }
    };

    private void initWidget() {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_newslist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(manager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
//        mTextView = (TextView)view.findViewById(R.id.newslist_textview);
//        doHttp();
        adapter = new NewsListAdapter(getActivity());
        adapter.setOnRCVItemClickListener(new NewsListAdapter.OnRCVItemClickListener() {
            @Override
            public void onItemClick(NewsInfo newsInfo) {
                //跳转或者是切换Fragment-----Fragment之间的通信
                NewsContentFragment f = new NewsContentFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("newsContent",newsInfo);
                f.setArguments(bundle);
                ((MainActivity)getActivity()).changeFragment(f);
            }
        });
        requestNetwork();
    }
    //
    private void requestNetwork(){
        newsUrl = NewsConfig.getNewsUrl(mType);//要请求的数据的网址
        new Thread(new Runnable() {//另起一个子线程，访问网络必须在子线程操作
            @Override
            public void run() {
                try {
                    //1.okHttpClient
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .get()
                            .url(newsUrl)
                            .build();
                    //new一个Call对象，需要传入Request请求对象，然后执行这个请求
                    //拿到返回给我们的Response相应消息
                    Response response = client.newCall(request).execute();
                    if(response.isSuccessful()){//如果请求成功
                        jsonData = response.body().string();
                        Log.d("aaaaaaaaaaaaaa","-------------"+jsonData);
//                        datas = jsonParse(jsonData);
                        Message msg = Message.obtain();
                        msg.what = 11;
                        msg.obj = jsonParse(jsonData);
                        Log.d("aaaaaaaaaaaaaa","-------------"+jsonParse(jsonData));
                        handler.sendMessage(msg);
                    }else{

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private List<NewsInfo> jsonParse(String jsonData){
        List<NewsInfo> newsDatas = new ArrayList<NewsInfo>();
        try {
            JSONObject object1 = new JSONObject(jsonData);
            if (object1.getString("reason").equals("成功的返回")){
                JSONObject object2 = object1.getJSONObject("result");
                JSONArray array = object2.getJSONArray("data");
                for (int i = 0;i < array.length();i++){
                    NewsInfo mData = new NewsInfo();
                    JSONObject object3 = array.getJSONObject(i);
                    mData.title = object3.getString("title");
                    mData.date = object3.getString("date");
                    mData.category = object3.getString("category");
                    mData.author_name = object3.getString("author_name");
                    mData.url = object3.getString("url");
                    mData.thumbnail_pic_s = object3.getString("thumbnail_pic_s");
                    newsDatas.add(mData);
                }
                return newsDatas;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
//    private void doHttp(){
//        final String newsUrl = NewsConfig.getNewsUrl(mType);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                InputStream is =null;
//                HttpURLConnection conn = null;
//                final StringBuffer sb;
//                try {
//                    URL url = new URL(newsUrl);
//                    conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("GET");
//                    conn.setConnectTimeout(5000);
//                    conn.setReadTimeout(5000);
//                    conn.connect();
//                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
//                        is = conn.getInputStream();
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//                        sb = new StringBuffer();
//                        String line;
//                        while ((line = reader.readLine()) != null){
//                            sb.append(line);
//                        }
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mTextView.setText(sb.toString());
//                            }
//                        });
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    try {
//                        is.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    conn.disconnect();
//                }
//            }
//        }).start();
//    }
}
