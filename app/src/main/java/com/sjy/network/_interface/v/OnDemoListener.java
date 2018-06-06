package com.sjy.network._interface.v;

import com.sjy.network.bean.BannerBean;

import java.util.List;

/**
 * 单一接口act的统一数据调用监听
 */

public interface OnDemoListener {

    void onLogSuccess(Object obj);

    void onLogFailed(int code, String msg, Exception e);

    //轮播图
    void onLooperImgSuccess(List<BannerBean> list);

    void onLooperImgFailed(int code, String msg, Exception e);
}
