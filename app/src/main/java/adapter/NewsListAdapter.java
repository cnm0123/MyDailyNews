package adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zmapp.mydailynews.MainActivity;
import com.example.zmapp.mydailynews.R;
import java.util.List;
import entity.NewsInfo;
import util.ImageLoader;
import util.ImageLoaderUtils;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder>{
    private List<NewsInfo> datas;
    private OnRCVItemClickListener l;
    private ImageLoader imageLoader;
    private ViewHolder viewHolder;
    //监听RecyclerView的item的监听器
    public interface OnRCVItemClickListener{
        void onItemClick(NewsInfo newsInfo);//将对象传递下去
    }
    public void setOnRCVItemClickListener(OnRCVItemClickListener l){
        this.l = l;
    }
    public NewsListAdapter(Context context){
        imageLoader = new ImageLoader(context);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView date;
        private TextView title;
        private View newsView;
        //构造方法内的参数View通常指的是RecyclerView内item的最外层布局
        public ViewHolder(View itemView) {
            super(itemView);
            newsView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_newslist);
            date = (TextView) itemView.findViewById(R.id.tv_item_newslist_date);
            title = (TextView) itemView.findViewById(R.id.tv_item_newslist_title);
        }
    }
    //初始化ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_newslist,null);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把item和position传回去，用接口回调 WeobView
                if (l != null){
                    int position = viewHolder.getAdapterPosition();//获取到当前item的position
                    NewsInfo newsInfo = datas.get(position);
                    l.onItemClick(newsInfo);
                }
            }
        });
        return viewHolder;
    }
    //主要用于设置数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        viewHolder = holder;
        NewsInfo data = datas.get(position);
        holder.imageView.setImageResource(R.mipmap.ic_launcher);

        Bitmap bitmap = imageLoader.loadImage(data.thumbnail_pic_s,listener);
        if (bitmap != null){
            holder.imageView.setImageBitmap(bitmap);
        }
        holder.title.setText(data.title);
        holder.date.setText(data.date);
    }
    private ImageLoader.OnLoadImageListener listener = new ImageLoader.OnLoadImageListener() {
        @Override
        public void onImageLoadOK(String url, Bitmap bitmap) {
            viewHolder.imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onImageLoadError(String url) {
            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        }
    };
    //获取item的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    public void addDatas(List<NewsInfo> datas){
        this.datas = datas;
    }
}
