package com.sjy.network.bean.login;

import java.io.Serializable;

/**
 * 登录更新 接口返回
 * 格式 CommonBean2<ProfileBean></>
 */

public class ProfileBean implements Serializable {
    String phone;
    String email;
    long default_folder;
    int emailverify;
    long total_space;
    long used_space;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getDefault_folder() {
        return default_folder;
    }

    public void setDefault_folder(long default_folder) {
        this.default_folder = default_folder;
    }

    public int getEmailverify() {
        return emailverify;
    }

    public void setEmailverify(int emailverify) {
        this.emailverify = emailverify;
    }

    public long getTotal_space() {
        return total_space;
    }

    public void setTotal_space(long total_space) {
        this.total_space = total_space;
    }

    public long getUsed_space() {
        return used_space;
    }

    public void setUsed_space(long used_space) {
        this.used_space = used_space;
    }

    @Override
    public String toString() {
        return "ProfileBean{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", default_folder=" + default_folder +
                ", emailverify=" + emailverify +
                ", total_space=" + total_space +
                ", used_space=" + used_space +
                '}';
    }
}
