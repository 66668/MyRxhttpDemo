package com.sjy.network.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sjy.network.base.Constants;
import com.sjy.network.util.CheckNetworkUtils;
import com.sjy.network.util.MLog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jingbin on 2017/2/14.
 * 网络请求工具类 用于集成retrofit+okhttp3
 * 由 MyHttpService负责调用
 * <p>
 */

public class HttpUtils {
    private static HttpUtils instance;
    private Gson gson;
    private Context context;
    private Object https;
    Cache cache = null;
    static File httpCacheDirectory;

    private boolean debug;//判断 app版本，由application设置

    private final static String TAG = "HttpUtils";

    /**
     * 获取实例对象
     */
    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化 application中初始化
     *
     * @param context
     * @param debug
     */
    public void init(Context context, boolean debug) {
        this.context = context;
        this.debug = debug;
        HttpHead.init(context);//设置http头
        //设置单独缓存
        initCache();
    }
    //=================================== 获取Retrofit样式====================================

    /**
     * 01 Retrofit构建 默认样式：Retrofit+okhttp
     */
    public <T> T getDefaultServer(Class<T> clz) {
        if (https == null) {
            synchronized (HttpUtils.class) {
//                https = getDefaultBuilder(URLUtils.API_BASE_URL).build().create(clz);
                https = getMYBuilder(URLUtils.API_BASE_URL).build().create(clz);
            }
        }
        initCache();//再设置一遍，可以注销
        return (T) https;
    }

    /**
     * 02 Retrofit构建:Retrofit+okhttp,添加token验证
     */

    public <T> T getTokenServer(Class<T> clz) {
        if (https == null) {
            synchronized (HttpUtils.class) {
                https = getTokenBuilder(URLUtils.API_BASE_URL).build().create(clz);
            }
        }
        initCache();//再设置一遍，可以注销
        return (T) https;
    }

     /**
     * 04 Retrofit构建:Retrofit+okhttp,添加token验证
     */

    public <T> T getCacheServer(Class<T> clz) {
        if (https == null) {
            synchronized (HttpUtils.class) {
                https = getCacheBuilder(URLUtils.API_BASE_URL).build().create(clz);
            }
        }
        initCache();//再设置一遍，可以注销
        return (T) https;
    }

     /**
     * 04 Retrofit构建:Retrofit+okhttp,添加token验证
     */

    public <T> T getHeaderServer(Class<T> clz) {
        if (https == null) {
            synchronized (HttpUtils.class) {
                https = getHeaderBuilder(URLUtils.API_BASE_URL).build().create(clz);
            }
        }
        initCache();//再设置一遍，可以注销
        return (T) https;
    }

    //设置缓存
    private void initCache() {
        if (httpCacheDirectory == null || cache == null) {
            synchronized (HttpUtils.class) {
                httpCacheDirectory = new File(context.getCacheDir(), Constants.APP_HTTP_ACACHE_FILE);
                try {
                    cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
                } catch (Exception e) {
                    Log.e("OKHttp", "Could not create http cache", e);
                }
            }
        }
    }

    //================================= Retrofit构建 可以根据自己缓存需要，灵活设置====================================

    /**
     * 调用以前接口的样式
     *
     * @param apiUrl
     * @return
     */
    private Retrofit.Builder getMYBuilder(String apiUrl) {

        //retrofit配置 可用链式结构
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getMyOkHttp());//设置okhttp3（重点），不设置走默认的
        builder.baseUrl(apiUrl);//设置远程地址

        //官方的json解析，要求格式必须规范才不会异常，但后台的不一定规范，这就要求自定义一个解析器避免这个情况
        builder.addConverterFactory(GsonConverterFactory.create());//将规范的gson及解析成实体
        //        builder.addConverterFactory(JsonResultConvertFactory.create());//自定义的json解析器


        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create()); //Rx
        return builder;
    }


    /**
     * 00 retrofit配置
     * （可用链式结构，但需要返回处理build()，就不用链式）
     */
    private Retrofit.Builder getDefaultRetrofit(String apiUrl) {

        //retrofit配置
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getDefaultOkhttp());//retrofit本身就可以异步处理接口，不用okhttp
        builder.baseUrl(apiUrl);//设置远程地址
        builder.addConverterFactory(new NullOnEmptyConverterFactory());      //01:添加自定义转换器，处理null响应
        builder.addConverterFactory(GsonConverterFactory.create(getGson())); //02:添加Gson转换器,将规范的gson及解析成实体
        //builder.addConverterFactory(GsonConverterFactory.create());        //03:添加Gson转换器,将规范的gson及解析成实体
        //builder.addConverterFactory(JsonResultConvertFactory.create());    //04:自定义的json解析器处理不规范json
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());//添加RxJavaCallAdapter

        return builder;
    }

    /**
     * 01 retrofit配置
     * （可用链式结构，但需要返回处理build()，就不用链式）
     */
    private Retrofit.Builder getDefaultBuilder(String apiUrl) {

        //retrofit配置
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getDefaultOkhttp());//设置okhttp（重点），不设置走默认的
        builder.baseUrl(apiUrl);//设置远程地址
        builder.addConverterFactory(new NullOnEmptyConverterFactory());      //01:添加自定义转换器，处理null响应
        builder.addConverterFactory(GsonConverterFactory.create(getGson())); //02:添加Gson转换器,将规范的gson及解析成实体
        //builder.addConverterFactory(GsonConverterFactory.create());        //03:添加Gson转换器,将规范的gson及解析成实体
        //builder.addConverterFactory(JsonResultConvertFactory.create());    //04:自定义的json解析器处理不规范json
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());//添加RxJavaCallAdapter

        return builder;
    }

    /**
     * 02 retrofit配置
     * （可用链式结构，但需要返回处理build()，就不用链式）
     * 有token验证
     */
    private Retrofit.Builder getTokenBuilder(String apiUrl) {

        //retrofit配置 可用链式结构
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getTokenOkHttp());//设置okhttp3（重点），不设置走默认的
        builder.baseUrl(apiUrl);//设置远程地址

        //官方的json解析，要求格式必须规范才不会异常，但后台的不一定规范，这就要求自定义一个解析器避免这个情况
        builder.addConverterFactory(GsonConverterFactory.create());//将规范的gson及解析成实体
        //        builder.addConverterFactory(JsonResultConvertFactory.create());//自定义的json解析器


        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create()); //Rx
        return builder;
    }

    /**
     * 03 retrofit配置
     * （可用链式结构，但需要返回处理build()，就不用链式）
     * 特殊缓存接口使用
     */
    private Retrofit.Builder getCacheBuilder(String apiUrl) {

        //retrofit配置 可用链式结构
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getDefaultOkhttp());//设置okhttp3（重点），不设置走默认的
        builder.baseUrl(apiUrl);//设置远程地址

        //官方的json解析，要求格式必须规范才不会异常，但后台的不一定规范，这就要求自定义一个解析器避免这个情况
        builder.addConverterFactory(GsonConverterFactory.create());//将规范的gson及解析成实体
        //        builder.addConverterFactory(JsonResultConvertFactory.create());//自定义的json解析器


        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create()); //Rx
        return builder;
    }

    /**
     * 04 retrofit配置
     * （可用链式结构，但需要返回处理build()，就不用链式）
     * 统一header请求头
     */
    private Retrofit.Builder getHeaderBuilder(String apiUrl) {

        //retrofit配置 可用链式结构
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getHeaderOkHttp());//设置okhttp3（重点），不设置走默认的
        builder.baseUrl(apiUrl);//设置远程地址

        //官方的json解析，要求格式必须规范才不会异常，但后台的不一定规范，这就要求自定义一个解析器避免这个情况
        builder.addConverterFactory(GsonConverterFactory.create());//将规范的gson及解析成实体
        //        builder.addConverterFactory(JsonResultConvertFactory.create());//自定义的json解析器


        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create()); //Rx
        return builder;
    }


    //================================= okhttp构建 该处根据你的后台灵活设置====================================

    /**
     * 以前能通的样式
     * @return
     */
    public OkHttpClient getMyOkHttp() {
        //log打印级别
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        try {
            //具体配置，可用链式结构
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            //            okBuilder.cache(cache);//自定义缓存路径
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10*1000, TimeUnit.MILLISECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new CacheInterceptor());//添加缓存拦截器
            okBuilder.addNetworkInterceptor(new CacheInterceptor());//添加缓存拦截器
            okBuilder.addInterceptor(loggingInterceptor);//设置拦截器,打印//getInterceptor()为默认的，现在改为自定义
            okBuilder.cache(cache);//设置缓存
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    MLog.d("HttpUtils", "hostname: " + hostname);
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * 01 单独缓存+自定义缓存路径：
     * <p>
     * 如果服务端没有配合处理cache请求头，会抛出如下504异常,可以自定义cache策略：
     * onError:retrofit2.adapter.rxjava.HttpException: HTTP 504 Unsatisfiable Request (only-if-cached)
     *
     * @return
     */
    private OkHttpClient getDefaultOkhttp() {
        //log打印级别
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        try {
            //具体配置，可用链式结构
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            //            okBuilder.cache(cache);//自定义缓存路径
            okBuilder.readTimeout(20, TimeUnit.SECONDS);//读超时
            okBuilder.connectTimeout(10 * 1000, TimeUnit.MILLISECONDS);//链接超时
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);//写超时
            okBuilder.addInterceptor(new CacheInterceptor());//添加缓存拦截器
            okBuilder.addNetworkInterceptor(new CacheInterceptor());//添加网络缓存拦截器
            okBuilder.addInterceptor(loggingInterceptor);//设置拦截器,打印// getInterceptor() 为默认的，现在改为自定义 loggingInterceptor
            okBuilder.cache(cache);//设置缓存
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    MLog.d("HttpUtils", "hostname: " + hostname);
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 02 证书验证
     * <p>
     * <p>
     * okhttp配置
     *
     * @return
     */
    private OkHttpClient getUnsafeOkHttp() {
        try {
            //获取目标网站的证书
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};

            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            //具体配置，可用链式结构
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(60, TimeUnit.SECONDS);
            okBuilder.connectTimeout(60, TimeUnit.SECONDS);
            okBuilder.writeTimeout(60, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new HttpCacheInterceptor());
            okBuilder.addInterceptor(getInterceptor());//设置拦截器
            okBuilder.sslSocketFactory(sslSocketFactory);
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    //                    Log.d("HttpUtils", "==come");
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 03 token验证
     *
     *
     * @return
     */
    private OkHttpClient getTokenOkHttp() {
        try {
            //具体配置，可用链式结构
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new HttpCacheInterceptor());//公共缓存拦截器
            okBuilder.addInterceptor(getInterceptor());//设置拦截器,打印
            okBuilder.addInterceptor(new Interceptor() {//网络请求设附带token
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    String yourToken = "your token from cache";//添加你的token
                    if (yourToken == null) {
                        return chain.proceed(originalRequest);
                    }
                    Request authorised = originalRequest.newBuilder()
                            .header("Authorization", yourToken)//token
                            .build();
                    return chain.proceed(authorised);
                }
            });
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    MLog.d("HttpUtils", "hostname: " + hostname);
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

  /**
     * 04 统一请求头
     *
     *
     * @return
     */
  private OkHttpClient getHeaderOkHttp() {
        try {
            //具体配置，可用链式结构
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new HttpCacheInterceptor());//公共缓存拦截器
            okBuilder.addInterceptor(getInterceptor());//设置拦截器,打印
            OkHttpClient.Builder builder = okBuilder.addInterceptor(new Interceptor() {//设置统一请求头，由后台要求，灵活设置
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder okhttpRequst = chain.request().newBuilder()
                            .addHeader("your key", "your header");//eg示例
                    return chain.proceed(okhttpRequst.build());
                }
            });
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    MLog.d("HttpUtils", "hostname: " + hostname);
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    //================================okhttp-缓存自定义相关======================================

    /**
     * 01 缓存拦截器，需要有缓存文件
     * <p>
     * 离线读取本地缓存，在线获取最新数据
     */

    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            //可添加token验证，oAuth验证，可用链式结构
            Request.Builder builder = request.newBuilder();
            builder.addHeader("Accept", "application/json;versions=1");
            if (CheckNetworkUtils.isNetworkConnected(context)) {//在线
                int maxAge = 60;//缓存时间
                builder.addHeader("Cache-Control", "public, max-age=" + maxAge);//设置请求的缓存时间
            } else {//离线
                int maxStale = 60 * 60 * 24 * 28;// tolerate 4-weeks stale
                builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            // 可添加token
            //            if (listener != null) {
            //                builder.addHeader("token", listener.getToken());
            //            }
            // 如有需要，添加请求头
            //            builder.addHeader("a", HttpHead.getHeader(request.method()));
            return chain.proceed(builder.build());
        }
    }

    /**
     * 02 设置缓存拦截器
     */
    class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            if (CheckNetworkUtils.isNetworkConnected(context)) {//在线缓存
                Response response = chain.proceed(request);
                int maxAge = 60; // 在线缓存在6s内可读取
                String cacheControl = request.cacheControl().toString();
                MLog.e("sjy-cache", "在线缓存在1分钟内可读取" + cacheControl);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {//离线缓存
                MLog.e("sjy-cache", "离线时缓存时间设置");
                request = request.newBuilder()
                        .cacheControl(FORCE_CACHE1)//此处设置了7秒---修改了默认系统方法，不能用默认的CacheControl.FORCE_CACHE--是int型最大值，就相当于断网的情况下，一直不清除缓存
                        .build();

                Response response = chain.proceed(request);
                //下面注释的部分设置也没有效果，因为在上面已经设置了
                return response.newBuilder()
                        //                        .removeHeader("Pragma")
                        //                        .removeHeader("Cache-Control")
                        //                        .header("Cache-Control", "public, only-if-cached, max-stale=50")
                        .build();
            }
        }
    }

    //---修改了系统方法--这是设置在多长时间范围内获取缓存里面
    public static final CacheControl FORCE_CACHE1 = new CacheControl.Builder()
            .onlyIfCached()
            .maxStale(60 * 60 * 24 * 28, TimeUnit.SECONDS)//这里是60 * 60 * 24 * 28s，CacheControl.FORCE_CACHE--是int型最大值
            .build();
    //================================okhttp-log自定义相关======================================

    /**
     * 设置log打印拦截器
     */
    private HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //可以通过 setLevel 改变日志级别,共包含四个级别：NONE、BASIC、HEADER、BODY
        /**
         * NONE 不记录
         * BASIC 请求/响应行
         * HEADERS 请求/响应行 + 头
         * BODY 请求/响应行 + 头 + 体
         */
        if (debug) {
            // 打印okhttp
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // 测试
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE); // 打包
        }
        return interceptor;
    }
//================================okhttp-gson自定义相关======================================

    // 01自定义gson处理
    private Gson getGson() {
        Log.d(TAG, "getGson: HttpUtils走gson转换方法");
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            gson = builder.create();
        }
        return gson;
    }

    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }
}
