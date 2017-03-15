package com.example.zmapp.mydailynews;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import fragment.CommentFragment;
import fragment.FavoriteFragment;
import fragment.LocalFragment;
import fragment.NewsContentFragment;
import fragment.NewsFragment;
import fragment.PhotoFragment;
import util.CameraAlbumUtil;
import util.ImageLoader;
import util.PermissionUtil;

public class MainActivity extends BaseActivity {
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.activity_main)DrawerLayout drawerLayout;
    @Bind(R.id.nav)NavigationView nav;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private NewsFragment newsFragment;
    private FavoriteFragment favoriteFragment;
    private LocalFragment localFragment;
    private CommentFragment commentFragment;
    private PhotoFragment photoFragment;
    private CircleImageView iv_header;
    private TextView email;
    private TextView head_name;
    private CameraAlbumUtil camera;

    //DrawerLayout:抽屉布局
    //NavigationView:侧滑菜单---menu + headerlayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setToolbar();
//        setFragment();
        getHeaderLayout();
        changeFragment(new NewsFragment());
    }
    private void setToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //设置左侧可点击按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        //给左侧可点击按钮加个图片
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        //抽屉
        nav.setNavigationItemSelectedListener(listener);
    }

    private void setFragment(){
        fm = getSupportFragmentManager();//v4包下拿FragmentManager的方法
        ft = fm.beginTransaction();//开始一个事务
        //第一个参数是容器的ID，第二个参数是将要被添加到Activity上的Fragment对象
        ft.replace(R.id.fl_main_list,new NewsFragment());
        ft.addToBackStack(null);//当点击返回时，可以返回到上一个碎片（栈）中
        ft.commit();//提交事务
    }
    public void changeFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //如果是NewsContentFragment，那么加入返回栈，点击返回可以回到上一个页面
        if (fragment instanceof NewsContentFragment){
            ft.addToBackStack(null);
        }
        ft.replace(R.id.fl_main_list, fragment);
        ft.commit();
    }
    private void getHeaderLayout(){
        View headerLayout = nav.getHeaderView(0);
        iv_header = (CircleImageView) headerLayout.findViewById(R.id.iv_header);
        String icon = getIntent().getStringExtra("icon");
        ImageLoader imageLoader = new ImageLoader(this);
        imageLoader.loadImage(icon, new ImageLoader.OnLoadImageListener() {
            @Override
            public void onImageLoadOK(String url, Bitmap bitmap) {
                iv_header.setImageBitmap(bitmap);
            }

            @Override
            public void onImageLoadError(String url) {

            }
        });
        email = (TextView)headerLayout.findViewById(R.id.email);
        String id = getIntent().getStringExtra("id");
        email.setText(id);
        head_name = (TextView)headerLayout.findViewById(R.id.name);
        String name = getIntent().getStringExtra("name");
        head_name.setText(name);
        iv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDailog();
            }
        });
    }
    private void chooseDailog(){
        camera = new CameraAlbumUtil(this);
        new AlertDialog.Builder(this)
                .setTitle("选择头像")
                .setPositiveButton("相机", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtil.requestPermissions(MainActivity.this, 11, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtil.OnRequestPermissionListener() {
                            @Override
                            public void onRequestGranted() {
                                camera.takePhoto();
                            }

                            @Override
                            public void onRequestDenied() {
                                Toast.makeText(MainActivity.this,"没给权限",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtil.requestPermissions(MainActivity.this, 22, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtil.OnRequestPermissionListener() {
                            @Override
                            public void onRequestGranted() {
                                camera.openAlbum();
                            }

                            @Override
                            public void onRequestDenied() {
                                Toast.makeText(MainActivity.this,"没给权限",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        onRequestPermissionsResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = CameraAlbumUtil.onActivityResult(requestCode,resultCode,data);
        if (bitmap != null){
            iv_header.setImageBitmap(bitmap);
        }
//        switch (requestCode){
//            case CameraAlbumUtil.TAKE_PHOTO:
//                if (resultCode == RESULT_OK){
//                    //裁剪图片
//                    camera.cutImageByCamera();
//                }
//                break;
//            case CameraAlbumUtil.CHOOSE_PHOTO:
//                Bitmap bitmap = camera.displayBitmap();
//                iv_header.setImageBitmap(bitmap);
//                break;
//        }
    }
    private NavigationView.OnNavigationItemSelectedListener listener = new
            NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.news:
                    if (newsFragment == null){
                        newsFragment = new NewsFragment();
                    }
                    changeFragment(newsFragment);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.favorite:
                    if (favoriteFragment == null){
                        favoriteFragment = new FavoriteFragment();
                    }
                    changeFragment(favoriteFragment);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.local:
                    if (localFragment == null){
                        localFragment = new LocalFragment();
                    }
                    changeFragment(localFragment);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.comment:
                    if (commentFragment == null){
                        commentFragment = new CommentFragment();
                    }
                    changeFragment(commentFragment);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.photo:
                    if (photoFragment == null){
                        photoFragment = new PhotoFragment();
                    }
                    changeFragment(photoFragment);
                    drawerLayout.closeDrawers();
                    break;
            }
            return true;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(this,"查找",Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(this,"帮助",Toast.LENGTH_SHORT).show();
                break;
            case R.id.helpp:
                Toast.makeText(this,"收藏",Toast.LENGTH_SHORT).show();
                break;
            case R.id.helppp:
                Toast.makeText(this,"分享",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
        return true;
    }
}
