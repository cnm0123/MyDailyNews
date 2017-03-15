package com.example.zmapp.mydailynews;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Button;
import android.widget.Toast;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;
import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class LaunchActivity extends BaseActivity{
    @Bind(R.id.textureView)TextureView textureView;
    @Bind(R.id.btn_signin_launch)Button btn_signin;
    @Bind(R.id.btn_signup_launch)Button btn_signup;
    private MediaPlayer player;
    private int pausePosition;
    private Context context;
    private PlatformDb platDB;
    //MediaPlayer(播放器)
    //1.播放什么----数据
    //2.到哪播放----SurfaceView(缺点:不能对视频平移、旋转、缩放等操作)、TextureView(载体)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);
        ShareSDK.initSDK(this);
        textureView.setSurfaceTextureListener(listener);
    }
    @OnClick(R.id.btn_signin_launch)
    public void longin(){
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        authorize(qq);
        platDB = qq.getDb();//获取数平台数据DB
        //通过DB获取各种数据
        platDB.getToken();
        platDB.getUserGender();
        String icon = platDB.getUserIcon();
        String id = platDB.getUserId();
        String name = platDB.getUserName();
        if (platDB.getUserId() != null){
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("icon",icon);
            bundle.putString("id",id);
            startActivity(MainActivity.class,bundle);
            LaunchActivity.this.finish();
        }
    }
    @OnClick(R.id.btn_signup_launch)
    public void longup(){
        Toast.makeText(LaunchActivity.this,"阿斯蒂芬",Toast.LENGTH_SHORT).show();
    }

    private TextureView.SurfaceTextureListener listener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            try {
                player = new MediaPlayer();
                //拿到Assets下的数据源，并返回一个AssetFileDescriptor类型的对象
                AssetFileDescriptor afd = getAssets().openFd("welcome.mp4");
                FileDescriptor fd = afd.getFileDescriptor();
                player.setDataSource(fd, afd.getStartOffset(),afd.getLength());//设置播放数据源
                Surface surface = new Surface(surfaceTexture);
                player.setSurface(surface);
                player.setLooping(true);
                player.prepareAsync();
                //设个监听，当数据准备好时，再开始播放
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        player.start();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (player.isPlaying()){
            player.pause();
            pausePosition = player.getCurrentPosition();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!player.isPlaying()){
            player.seekTo(pausePosition);
            player.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null){
            player.stop();
            player.release();//回收
            player = null;
        }
    }
    private void authorize(Platform plat){
        if (plat == null) {
            popupOthers();
            return;
        }
        //判断指定平台是否已经完成授权
        if(plat.isAuthValid()) {
            String userId = plat.getDb().getUserId();
            if (userId != null) {
//                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
//                login(plat.getName(), userId, null);
                return;
            }
        }
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        plat.SSOSetting(true);
        //获取用户资料
        plat.showUser(null);
    }

    private void popupOthers(){
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        qq.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                //用户资源都保存到res
                // 通过打印res数据看看有哪些数据是你想要的

                platform.getDb().exportData();
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });
        //authorize与showUser单独调用一个即可
//        qq.authorize();//单独授权,OnComplete返回的hashmap是空的
        qq.showUser(null);//授权并获取用户信息
        // 移除授权
        // weibo.removeAccount(true);
        //判断指定平台是否已经完成授权

    }
}
