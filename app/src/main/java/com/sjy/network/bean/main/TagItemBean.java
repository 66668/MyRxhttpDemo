package com.sjy.network.bean.main;

import java.io.Serializable;

/**
 * 支付结果返回
 * 格式{code msg data {}}
 */

public class TagItemBean implements Serializable {
    String name;
    int count;
    long id;


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


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
