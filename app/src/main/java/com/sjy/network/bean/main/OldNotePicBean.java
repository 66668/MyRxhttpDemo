package com.sjy.network.bean.main;

import com.sjy.network.bean.CommonBean;

import java.io.Serializable;

/**
 *
 * 格式{code msg xx}
 */

public class OldNotePicBean extends CommonBean implements Serializable {
    long id;
    String name;
    String path;
    long size;
    String md5;

    public OldNotePicBean(int code, String msg) {
        super(code, msg);
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OldNotePicBean{" +
                "md5='" + md5 + '\'' +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
