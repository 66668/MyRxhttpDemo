package com.sjy.network.bean;

import java.io.Serializable;

/**
 * 该形式是{code:"", msg:"", Profile:{内容}}
 *
 * proFileBean更新使用
 */

public class CommonBean2<T> implements Serializable {
    int code;
    String msg;
    T profile;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getProfile() {
        return profile;
    }

    public void setProfile(T profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "CommonBean2{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", profile=" + profile +
                '}';
    }
}
