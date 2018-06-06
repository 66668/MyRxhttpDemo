#MVP模式下-RxJava2+Retrofit2+Okhttp3框架搭建及使用示例
先介绍网络框架使用，再介绍RxJava2+Retrofit2+Okhttp3的封装步骤，帮助童鞋们提升开发速度
##框架的使用教程
###1.MyApplication中初始化

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication = this;

        //比较好用的Log封装，推荐使用
        MLog.init(true, "SJY");//true

        //初始化网络
        HttpUtils.getInstance().init(this, MLog.DEBUG);

    }
###2.MVP的正常使用：

