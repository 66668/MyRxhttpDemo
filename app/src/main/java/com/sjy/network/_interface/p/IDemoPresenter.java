package com.sjy.network._interface.p;

import java.io.File;

/**
 * p层 封装调用方法，子类去实现
 */

public interface IDemoPresenter {

    void pLoginByPhoneAndPs(String user_phone, String ps);
    void pBanner();
    void pChangePic(String token,File file);

}
