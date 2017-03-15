package config;

import java.util.ArrayList;
import java.util.List;

import entity.NewsInfo;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class NewsConfig {
    public static String getNewsUrl(String newsType){
        StringBuffer sb = new StringBuffer();
        sb.append("http://v.juhe.cn/toutiao/index?type=");
        sb.append(newsType);
        sb.append("&key=7066afea2bde94d7edfcefda25966b3b");
        return sb.toString();
    }
    //准备一个静态的集合，用于存放用户的收藏
    public static List<NewsInfo> newsInfos = new ArrayList<>();
}
