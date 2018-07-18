package com.sjy.network.bean.settings;

import com.thinkernote.ThinkerNote.bean.CommonBean;

import java.io.Serializable;

/**
 * 反馈，上传图片
 * {"code":0,"name":"IMG_20180525_132859.jpg","msg":"ok","path":"\/attachment\/28498150","size":904971,"id":28498150,"md5":"0168DEEF1F07313D75567A1FBF54FDCE"}
 */

public class FeedBackBean extends CommonBean implements Serializable {
    long id;
    String name;
    String path;
    long size;
    String md5;

    public FeedBackBean(int code, String msg) {
        super(code, msg);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
