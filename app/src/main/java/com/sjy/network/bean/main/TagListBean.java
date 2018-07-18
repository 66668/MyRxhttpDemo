package com.sjy.network.bean.main;

import java.io.Serializable;
import java.util.List;

/**
 * 该形式是{code:"", msg:"", Profile:{内容}}
 * <p>
 * proFileBean更新使用
 */

public class TagListBean implements Serializable {
    int code;
    String msg;
    List<TagItemBean> tags;

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

    public List<TagItemBean> getTags() {
        return tags;
    }

    public void setTags(List<TagItemBean> tags) {
        this.tags = tags;
    }
}
