package com.sjy.network.module;

import com.sjy.network._interface.m.DemoModuleInterface;
import com.sjy.network._interface.v.OnChangePhotoListener;
import com.sjy.network._interface.v.OnDemoListener;
import com.sjy.network.bean.BannerBean;
import com.sjy.network.bean.CommonBean;
import com.sjy.network.bean.CommonListBean;
import com.sjy.network.bean.LoginBean;
import com.sjy.network.bean.PhotoBean;
import com.sjy.network.http.MyHttpService;
import com.sjy.network.util.MLog;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 标准Module的写法，封装调用的方法，子类去实现
 */
public class DemoModuleImpl implements DemoModuleInterface {
    public static final String TAG = "SJY";
    @Override
    public void postLogin(final OnDemoListener listener, String userPhone, String ps) {

        MyHttpService.Builder.getHttpServer()//固定样式，可自定义其他网络
                .postLogin(userPhone, ps)//接口方法
                .subscribeOn(Schedulers.io())//固定样式
                .unsubscribeOn(Schedulers.io())//固定样式
                .observeOn(AndroidSchedulers.mainThread())//固定样式
                .subscribe(new Observer<CommonBean<LoginBean>>() {//固定样式，可自定义其他处理
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "登录--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "登录--登录失败异常onError:" + e.toString());
                        listener.onLogFailed(-1, "登录失败异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<LoginBean> bean) {
                        MLog.d(TAG, "登录-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onLogSuccess(bean.getResult());
                        } else if (bean.getCode() == 500) {
                            listener.onLogFailed(bean.getCode(), bean.getMessage(), new Exception("登录失败！"));
                        } else if (bean.getCode() == 400) {
                            listener.onLogFailed(bean.getCode(), bean.getMessage(), new Exception("登录失败！"));
                        }
                    }

                });
    }

    @Override
    public void postBanner(final OnDemoListener listener) {
        MyHttpService.Builder.getHttpServer().getBanner("北京", 2)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<BannerBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "HomeModleImpl--onCompleted 获取主页轮播图数据成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "HomeModleImpl--获取主页轮播图数据异常onError:" + e.toString());
                        listener.onLooperImgFailed(-1, "获取主页轮播图数据异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<BannerBean> bean) {
                        MLog.d(TAG, "HomeModleImpl--轮播图--" + bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onLooperImgSuccess(bean.getResult());
                            MLog.d("轮播图数据长度：" + bean.getResult().size());
                        } else {
                            listener.onLooperImgFailed(bean.getCode(), bean.getMessage(), new Exception("dasgfd"));
                        }
                    }

                });
    }

    /**
     * 修改 头像
     *
     * @param token    该参数需要加载到RequestBody中
     * @param file
     * @param listener
     */
    @Override
    public void mChangePhoto(String token, File file, final OnChangePhotoListener listener) {
        //需要对file进行封装

        //token
        // 需要加入到MultipartBody中，而不是作为参数传递
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .addFormDataPart("token", token)
                .setType(MultipartBody.FORM);

        //file
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("avatar", file.getName(), photoRequestBody);

        List<MultipartBody.Part> parts = builder.build().parts();

        MyHttpService.Builder.getHttpServer().changePhotoPost(parts)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<PhotoBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "修改头像--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "修改头像异常--onError:" + e.toString());
                        listener.onChangePhoteFailed(-1, "修改头像异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<PhotoBean> bean) {
                        MLog.d(TAG, "修改头像-onNext");

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onChangePhoteSuccess(bean.getResult());
                        } else {
                            listener.onChangePhoteFailed(bean.getCode(), bean.getMessage(), new Exception("dagdagah"));
                        }
                    }

                });
    }

}
