package com.sjy.network.presenter;

import android.content.Context;

import com.sjy.network._interface.m.DemoModuleInterface;
import com.sjy.network._interface.p.IDemoPresenter;
import com.sjy.network._interface.v.OnChangePhotoListener;
import com.sjy.network._interface.v.OnDemoListener;
import com.sjy.network.bean.BannerBean;
import com.sjy.network.module.DemoModuleImpl;

import java.io.File;
import java.util.List;

/**
 * 登录相关 p层实现
 */

public class DemoPresenterImpl implements IDemoPresenter, OnDemoListener ,OnChangePhotoListener{
    Context context;

    //登录
    OnDemoListener loginview;
    OnChangePhotoListener picView;
    DemoModuleInterface modle;


    //登录 初始化
    public DemoPresenterImpl(Context context, OnDemoListener view,OnChangePhotoListener view2) {
        this.context = context;
        this.loginview = view;
        this.picView = view2;
        modle = new DemoModuleImpl();
    }

    /**
     * ======================================================================================================
     * ====================================================调用用M层代码==================================================
     * ======================================================================================================
     */

    @Override
    public void pLoginByPhoneAndPs(String user_phone, String ps) {
        modle.postLogin(this, user_phone, ps);
    }

    @Override
    public void pBanner() {
        modle.postBanner(this);
    }

    @Override
    public void pChangePic(String token, File file) {
        modle.mChangePhoto(token,file,this);
    }

    /**
     * ======================================================================================================
     * ====================================================返回View的回调==================================================
     * ======================================================================================================
     */

    /**
     * 登录
     *
     * @param obj
     */
    @Override
    public void onLogSuccess(Object obj) {
        loginview.onLogSuccess(obj);
    }

    @Override
    public void onLogFailed(int code, String msg, Exception e) {
        loginview.onLogFailed(code, msg, e);
    }

    //banner
    @Override
    public void onLooperImgSuccess(List<BannerBean> list) {
        loginview.onLooperImgSuccess(list);
    }

    @Override
    public void onLooperImgFailed(int code, String msg, Exception e) {
        loginview.onLooperImgFailed(code, msg, e);
    }

    //图片
    @Override
    public void onChangePhoteSuccess(Object obj) {
        picView.onChangePhoteSuccess(obj);
    }

    @Override
    public void onChangePhoteFailed(int code, String msg, Exception e) {
        picView.onChangePhoteFailed(code,msg,e);
    }
}
