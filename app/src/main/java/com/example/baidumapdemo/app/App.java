package com.example.baidumapdemo.app;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by hasee on 2017/9/9.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
    }
}
