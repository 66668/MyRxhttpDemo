package com.sjy.network.bean;

import java.io.Serializable;

/**
 * 该形式是{code:"", msg:"", Profile:{内容}}
 * <p>
 * GetNoteByNoteId使用
 */

public class CommonBean3<T> implements Serializable {
    int code;
    String msg;
    T note;

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

    public T getNote() {
        return note;
    }

    public void setNote(T note) {
        this.note = note;
    }

}

