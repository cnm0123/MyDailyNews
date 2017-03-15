package entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class NewsInfo implements Parcelable{
    public String title;//新闻标题
    public String date;//日期
    public String category;//新闻类别，eg :shehui
    public String author_name;//作者， eg:腾讯娱乐
    public String thumbnail_pic_s;//新闻图片的网址
    public String url;

    public static final Creator<NewsInfo> CREATOR = new Creator<NewsInfo>() {
        @Override
        public NewsInfo createFromParcel(Parcel in) {
            NewsInfo info = new NewsInfo();
            info.title = in.readString();
            info.date = in.readString();
            info.category = in.readString();
            info.author_name = in.readString();
            info.thumbnail_pic_s = in.readString();
            info.url = in.readString();
            return info;
        }

        @Override
        public NewsInfo[] newArray(int size) {
            return new NewsInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(category);
        dest.writeString(author_name);
        dest.writeString(thumbnail_pic_s);
        dest.writeString(url);
    }
}