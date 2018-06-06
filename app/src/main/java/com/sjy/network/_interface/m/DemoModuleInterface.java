package com.sjy.network._interface.m;

import com.sjy.network._interface.v.OnChangePhotoListener;
import com.sjy.network._interface.v.OnDemoListener;

import java.io.File;

public interface DemoModuleInterface {
    void postLogin(OnDemoListener listener, String userPhone, String ps);
    void postBanner(OnDemoListener listener);
    void mChangePhoto(String token, File file, OnChangePhotoListener listener);
}
