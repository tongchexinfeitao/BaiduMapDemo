package com.example.baidumapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class LocationActivity extends AppCompatActivity {

    // 定位相关
    MapView mMapView;
    BaiduMap mBaiduMap;

    // 定位相关
    LocationClient mLocClient;

    //定位回调监听
    public MyLocationListenner myListener = new MyLocationListenner();

    //MyLocationData 可以理解成当前位置
    private MyLocationData locData;

    // 是否首次定位
    boolean isFirstLoc = true;

    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location2);


        //如果定位不准确(到非洲几内亚湾),首先去设置App的应用权限,第二去检查sha1配置流程是否正确

        mMapView = (MapView) findViewById(R.id.map);

        //拿到baiduMap
        mBaiduMap = mMapView.getMap();

        // 开启定位图层 ,设置地图支持定位
        mBaiduMap.setMyLocationEnabled(true);

        // 定位初始化
        mLocClient = new LocationClient(this);

        //注册定位回调监听
        mLocClient.registerLocationListener(myListener);

        //设置定位的细节
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);

        //这个开始去定位
        mLocClient.start();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }

            //去拿经纬度
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();

            //显示当前位置
            locData = new MyLocationData.Builder()
                    //精确度
                    .accuracy(location.getRadius())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();

            //把当前位置设置到map上
            mBaiduMap.setMyLocationData(locData);

            //第一次进入的时候,设置地图的缩放级别
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();

                //zoom设置地图缩放级别,越大的话地图的范围越大
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
