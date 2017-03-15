package base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import butterknife.ButterKnife;
import util.LogUtils;

/**
 * Created by Administrator on 2017/2/13 0013.
 */

public class BaseActivity extends AppCompatActivity {
    //创建一个集合用于存放所有activity
    private static ArrayList<BaseActivity> activities = new ArrayList<BaseActivity>();

    //一键退出
    public void finishAll(){
        for (int i = 0; i < activities.size(); i++) {
            activities.get(i).finish();
        }
    }



    //--------------------activity的跳转-----------------------
    protected void startActivity(Class<?> targetClass){
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
    }

    protected void startActivity(Class<?> targetClass, Bundle bundle){
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startActivity(Class<?> targetClass, int enterAnim, int exitAnim){
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
    }

    protected void startActivity(Class<?> targetClass, Bundle bundle, int enterAnim, int exitAnim){
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
    }

    //--------------------管理生命周期-----------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
        LogUtils.d(this, "--------onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d(this, "--------onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(this, "--------onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d(this, "--------onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.d(this, "--------onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activities.remove(this);
        LogUtils.d(this, "--------onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.d(this, "--------onRestart");
    }
    private static boolean debag = true;
    public static void d(Object obj, String msg){
        if (debag) {
            Log.d(obj.getClass().getSimpleName(), msg);
        }
    }

}
