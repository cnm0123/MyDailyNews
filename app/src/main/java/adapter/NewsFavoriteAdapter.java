package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zmapp.mydailynews.R;

import java.util.List;

import entity.NewsInfo;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class NewsFavoriteAdapter extends RecyclerView.Adapter<NewsFavoriteAdapter.ViewHolder>{
    private List<NewsInfo> datas;

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView date;
        private TextView title;
        private TextView category;
        private View newsView;
        //构造方法内的参数View通常指的是RecyclerView内item的最外层布局
        public ViewHolder(View itemView) {
            super(itemView);
            newsView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.iv_newsfavorite);
            date = (TextView) itemView.findViewById(R.id.tv_newsfavorite_date);
            title = (TextView) itemView.findViewById(R.id.tv_newsfavorite_title);
            category = (TextView) itemView.findViewById(R.id.tv_newsfavorite_category);
        }
    }
    //初始化ViewHolder
    @Override
    public NewsFavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_newsfavorite,null);
        final NewsFavoriteAdapter.ViewHolder viewHolder = new NewsFavoriteAdapter.ViewHolder(view);

        return viewHolder;
    }
    //主要用于设置数据
    @Override
    public void onBindViewHolder(NewsFavoriteAdapter.ViewHolder holder, int position) {
        NewsInfo data = datas.get(position);
        holder.imageView.setImageResource(R.mipmap.ic_launcher);
        holder.title.setText(data.title);
        holder.date.setText(data.date);
        holder.category.setText(data.category);
    }
    //获取item的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    public void addDatas(List<NewsInfo> datas){
        this.datas = datas;
    }
}
