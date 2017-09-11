package com.example.baidumapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Marker marker;

    public LocationClient mLocationClient = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

//1.设置地图类型
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //卫星地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        //开启交通图
//        mBaiduMap.setTrafficEnabled(true);

        //热力图
//        mBaiduMap.setBaiduHeatMapEnabled(true);


//2.在地图上添加位置标记

//定义Maker坐标点
        LatLng point = new LatLng(39.963175, 116.400244);

//构建Marker图标
        BitmapDescriptor icon_marka = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        BitmapDescriptor icon_markb = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_markb);
        BitmapDescriptor icon_markc = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_markc);
        BitmapDescriptor icon_markd = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_markd);
        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        giflist.add(icon_marka);
        giflist.add(icon_markb);
        giflist.add(icon_markc);
        giflist.add(icon_markd);

//构建MarkerOption，用于在地图上标记添加标记
        MarkerOptions options = new MarkerOptions()
                .position(point)  //设置marker的位置
                .icons(giflist)  //设置marker图标
                .zIndex(1)  //设置marker所在层级
                .period(100)
                .draggable(true);  //设置手势拖拽
        options.animateType(MarkerOptions.MarkerAnimateType.jump);

//将marker添加到地图上
        marker = (Marker) (mBaiduMap.addOverlay(options));

//3.给地图上的标记设置拖拽监听
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
                //拖拽中
            }

            public void onMarkerDragEnd(Marker marker) {
                //拖拽结束
                LatLng position = marker.getPosition();
                Toast.makeText(MainActivity.this, "纬度 : " + position.latitude + "  经度: " + position.longitude, Toast.LENGTH_SHORT).show();
            }

            public void onMarkerDragStart(Marker marker) {
                //开始拖拽
            }
        });

//4.在地图上添加多边形标记
        LatLng pt1 = new LatLng(39.93923, 116.357428);
        LatLng pt2 = new LatLng(39.91923, 116.327428);
        LatLng pt3 = new LatLng(39.89923, 116.347428);
        LatLng pt4 = new LatLng(39.89923, 116.367428);
        LatLng pt5 = new LatLng(39.91923, 116.387428);
        List<LatLng> pts = new ArrayList<LatLng>();
        pts.add(pt1);
        pts.add(pt2);
        pts.add(pt3);
        pts.add(pt4);
        pts.add(pt5);
        //构建用户绘制多边形的Option对象
        OverlayOptions polygonOption = new PolygonOptions()
                .points(pts)
                .stroke(new Stroke(5, 0xAA00FF00))
                .fillColor(0xAAFFFF00);
        mBaiduMap.addOverlay(polygonOption);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
