package fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.example.zmapp.mydailynews.MyApplication;
import com.example.zmapp.mydailynews.R;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/2/15 0015.
 */

public class LocalFragment extends Fragment{
    private View view;
    private TextView textView;
    private Context mcontext;
    private MapView mapview;
    private AMap aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //准备一个集合，用于装没有被授权的权限
    private List<String> permissionList = new ArrayList<>();
    @Override
    public void onAttach(Context context) {
        mcontext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local,null);
        textView = (TextView) view.findViewById(R.id.tv_fm_local);
        mapview = (MapView)view.findViewById(R.id.amapview);
        initLocation();
        requestPermission();
        mapview.onCreate(savedInstanceState);
        getMapView();
        return view;
    }
    private void getMapView(){
        if (aMap ==null){
            aMap = mapview.getMap();
            aMap.setTrafficEnabled(true);// 显示实时交通状况
            //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
            aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
        }
    }
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(mcontext, Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(mcontext, Manifest.permission
                .READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(mcontext, Manifest.permission
                .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[
                    permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        } else {
            //启动定位
            mLocationClient.startLocation();
        }
    }

    private void initLocation(){
        //初始化定位
        mLocationClient = new AMapLocationClient(MyApplication.getContext());
        //设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取最近3s内精度最高的一次定位结果：
        mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(5000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    private AMapLocationListener mLocationListener = new AMapLocationListener(){
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            final StringBuilder sb = new StringBuilder(256);
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    sb.append("").append(amapLocation.getLatitude());//获取纬度
                    sb.append("").append(amapLocation.getLongitude());//获取经度
                    sb.append("").append(amapLocation.getAddress());//获取地址
                    //获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    sb.append("\n"+df.format(date));
                    Toast.makeText(getActivity(),sb.toString(),Toast.LENGTH_SHORT).show();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(sb.toString());

                        }
                    });
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(mcontext,
                                    "必须同意所有权限", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    //启动定位
                    mLocationClient.startLocation();
                } else {
                    Toast.makeText(mcontext, "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }
}
