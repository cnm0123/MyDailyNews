package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.zmapp.mydailynews.MainActivity;
import com.example.zmapp.mydailynews.R;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import config.NewsConfig;
import entity.NewsInfo;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class NewsContentFragment extends Fragment{
    private Toolbar toolbar;
    private View view;
    private WebView webView;
    private NewsInfo newsInfo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        newsInfo = getArguments().getParcelable("newsContent");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_content,null);
        ShareSDK.initSDK(getActivity());
        initWidget();
        collectionNews();
        return view;
    }
    private void initWidget(){
        webView = (WebView) view.findViewById(R.id.webview_newscontent);
        //设置WebView是否支持Java脚本语言
        webView.getSettings().setJavaScriptEnabled(true);
            //设置网页在当前WebView上显示而不是第三方浏览器
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(newsInfo.url);
    }
    private void collectionNews(){
        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                boolean isClick = false;
                if (item.getItemId() == R.id.helpp){
                    if (NewsConfig.newsInfos.size() <= 0){
                        NewsConfig.newsInfos.add(newsInfo);
                        Toast.makeText(getActivity(),"收藏成功",Toast.LENGTH_SHORT).show();
                    }else {
                        for (NewsInfo data : NewsConfig.newsInfos){
                            if (data.url.equals(newsInfo.url)){
                                isClick = true;
                                Toast.makeText(getActivity(),"不要再重复收藏了",Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (!isClick){
                            NewsConfig.newsInfos.add(newsInfo);
                            Toast.makeText(getActivity(),"收藏成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else if (item.getItemId() == R.id.helppp){
                    showShare();
                }
                return true;
            }
        });
    }
    private void showShare(){
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(getActivity());
    }
}