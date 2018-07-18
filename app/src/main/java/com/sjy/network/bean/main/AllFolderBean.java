package com.sjy.network.bean.main;

import java.io.Serializable;
import java.util.List;

/**
 * 该形式是{code:"", msg:"", Profile:{内容}}
 * <p>
 * proFileBean更新使用
 */

public class AllFolderBean implements Serializable {
    int code;
    String msg;
    List<AllFolderItemBean> folders;

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

    public List<AllFolderItemBean> getFolders() {
        return folders;
    }

    public void setFolders(List<AllFolderItemBean> folders) {
        this.folders = folders;
    }
}
