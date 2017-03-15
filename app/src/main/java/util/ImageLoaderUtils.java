package util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/2/23 0023.
 */
//图片加载及缓存的工具类
//    url-----图片=====存到内存，存到文件
public class ImageLoaderUtils{
    //key:根据什么去取存的东西
    //value:要存的东西
    private static LruCache<String,Bitmap> lruCache;
    private File path;
    public ImageLoaderUtils(Context context){
        int size = (int)Runtime.getRuntime().maxMemory()/8;
        lruCache = new LruCache<String ,Bitmap>(size){
            //计算每张图片的大小
            @Override
            protected int sizeOf(String key,Bitmap value){
                return value.getByteCount();
            }
        };
        path = context.getCacheDir();//存到手机
//        path = context.getExternalCacheDir();//存到内存卡
        if (!path.exists()){
            path.mkdirs();
        }
    }

    public interface OnLoadImageListener{
        void onImageLoadOK(String url, Bitmap bitmap);
        void onImageLoadError(String url);
    }
    public Bitmap loadImage(String url,OnLoadImageListener l){
        Bitmap bitmap = null;
        //内存取
        bitmap = getFromCache(url);
        if (bitmap != null){

            return bitmap;
        }
        //文件取
        bitmap = getFromFile(url);
        if (bitmap != null){

            return bitmap;
        }
        //网络下载
        getFromNet(url,l);

        return null;
    }
    private void saveToCache(String url,Bitmap bitmap){
        lruCache.put(url,bitmap);
    }
    private Bitmap getFromCache(String url){
        return lruCache.get(url);
    }
    private void saveToFile(String url,Bitmap bitmap){
        String fileName = url.substring(url.lastIndexOf('/')+1);
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(path,fileName));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private Bitmap getFromFile(String url){
        String fileName = url.substring(url.lastIndexOf('/')+1);
        File file = new File(path,fileName);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (bitmap != null){
            saveToCache(url,bitmap);
            return bitmap;
        }
        return null;
    }
    private void getFromNet(String url, OnLoadImageListener l){
        MyAsyncTask task = new MyAsyncTask(l);
        task.execute(url);
    }
    //参数一:执行该任务需要传入什么参数
    //参数二:如果执行该任务是否需要在界面显示进度,如果需要,传入进度的数据类型
    //参数三:执行该任务需要返回的数据
    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String newsUrl;
        private OnLoadImageListener l;
        public MyAsyncTask(OnLoadImageListener l){
            this.l = l;
        }
        //默认是在子线程执行,用于执行后台任务
        @Override
        protected Bitmap doInBackground(String... params) {
            newsUrl = params[0];
            Bitmap bitmap = doNetWork();
            return bitmap;//该返回值被传递到了onPostExecute()方法内
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            saveToCache(newsUrl,bitmap);
            saveToFile(newsUrl,bitmap);
            if (bitmap != null){
                l.onImageLoadOK(newsUrl,bitmap);
            }else {
                l.onImageLoadError(newsUrl);
            }
        }

        private Bitmap doNetWork(){
            InputStream is = null;
            try {
                URL url = new URL(newsUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(20*1000);
                conn.setRequestMethod("GET");
                conn.setReadTimeout(20*1000);
                is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if (bitmap != null){
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }
}
