package com.sjy.network.base;

/**
 * 全局常量管理
 */

public class Constants {

    public static final String APP_NAME = "efulai";
    public static final String ABSULTEPATH = "/efulai/userphoto";
    public static final String NEW_HTTP = "https://user.efulai.cn/";//app中有些图片没有根路径
    public static final String DETAIL_HTTP = "https://image.efulai.cn/";//app中有些图片没有根路径
    public static final String KEY_SEARCH_KEY = "search_key";//搜索关键词key
    public static final String TOKEN = "token";
    public static final String WEIXIN_ID = "wxb6af9c7c10b7bbf5";//微信 app_id
    public static final String WEIXIN_SECRET = "ea9325b9c0eac1bd92bd6bbb1d11bb38";//微信 APP_SECRET
    public static final String GRANT_TYPE = "authorization_code";//微信 GRANT_TYPE
    public static final int ANDROID = 2;//设备id,android=2,IOS=3

    /**
     * ==============================================================================
     * ======================================缓存文件管理========================================
     * ==============================================================================
     */
    public static final String APP_HTTP_ACACHE_FILE = "efulai_acache_https";//网络路径缓存

    /**
     * ==============================================================================
     * ======================================缓存时间统一管理========================================
     * ==============================================================================
     */
    public static final int TIME_ONE_HOUER = 3600;
    public static final int TIME_TWO_HOUER = 7200;
    public static final int TIME_THREE_HOUER = 10800;
    public static final int TIME_SIX_HOUER = 21600;
    public static final int TIME_TWELVE_HOUER = 43200;
    public static final int TIME_ONE_DAY = 86400;
    public static final int TIME_TWO_DAY = 172800;
    public static final int TIME_THREE_DAY = 259200;


    /**
     * ==============================================================================
     * ======================================首页相关========================================
     * ==============================================================================
     */

    //搜索轮播
    public static final String BANNER_SEARCH = "banner_search";
}
