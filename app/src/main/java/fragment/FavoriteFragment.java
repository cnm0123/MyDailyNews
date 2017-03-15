package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zmapp.mydailynews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.NewsFavoriteAdapter;
import config.NewsConfig;
import entity.NewsInfo;

/**
 * Created by Administrator on 2017/2/15 0015.
 */

public class FavoriteFragment extends Fragment{
    private View view;
    private RecyclerView recyclerView;
    private NewsFavoriteAdapter adapter;
    private List<NewsInfo> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite,null);
        initWidget();
        return view;
    }
    private void initWidget(){
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_favorite);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(manager.VERTICAL);//默认是竖着的，这句可以不写，要是横着的必须写
        recyclerView.setLayoutManager(manager);
        datas = NewsConfig.newsInfos;
        adapter = new NewsFavoriteAdapter();
        adapter.addDatas(datas);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

}
