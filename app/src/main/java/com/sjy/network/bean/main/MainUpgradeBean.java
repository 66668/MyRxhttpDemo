package com.sjy.network.bean.main;

import com.thinkernote.ThinkerNote.bean.CommonBean;

import java.io.Serializable;

/**
 *  更新 返回数据的bean sjy 0621
 */

public class MainUpgradeBean extends CommonBean implements Serializable {
    String update_at;
    String version;
    int versionCode;
    int size;
    String content;
    String url;
    String host;

    public MainUpgradeBean(int code, String msg) {
        super(code, msg);
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "MainUpgradeBean{" +
                "update_at='" + update_at + '\'' +
                ", version='" + version + '\'' +
                ", versionCode=" + versionCode +
                ", size=" + size +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
