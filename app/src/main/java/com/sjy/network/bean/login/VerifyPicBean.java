package com.sjy.network.bean.login;

import com.thinkernote.ThinkerNote.bean.CommonBean;

import java.io.Serializable;

/**
 * 图片验证
 */

public class VerifyPicBean extends CommonBean implements Serializable {
    private String captcha;//图片路径
    private String nonce;
    private String hashkey;

    public VerifyPicBean(int code, String msg) {
        super(code, msg);
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getHashkey() {
        return hashkey;
    }

    public void setHashkey(String hashkey) {
        this.hashkey = hashkey;
    }

    @Override
    public String toString() {
        return "VerifyPicBean{" +
                "captcha='" + captcha + '\'' +
                ", nonce='" + nonce + '\'' +
                ", hashkey='" + hashkey + '\'' +
                '}';
    }
}
