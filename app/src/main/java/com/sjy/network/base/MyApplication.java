package com.sjy.network.base;

import android.app.Application;

import com.sjy.network.http.HttpUtils;
import com.sjy.network.http.wechat.WeichatHttpUtils;
import com.sjy.network.util.MLog;

public class MyApplication extends Application {
    private static MyApplication MyApplication;

    public static MyApplication getInstance() {
        return MyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication = this;

        //设置打印,正式打包，设为 false
        MLog.init(true, "SJY");//true

        //初始化网络
        HttpUtils.getInstance().init(this, MLog.DEBUG);

        //微信初始化网络
        WeichatHttpUtils.getInstance().init(this, MLog.DEBUG);
    }
}
