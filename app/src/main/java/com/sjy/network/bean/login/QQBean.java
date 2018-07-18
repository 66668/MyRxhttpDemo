package com.sjy.network.bean.login;

import java.io.Serializable;

/**
 * 登录更新 接口返回
 * //callback( {"client_id":"101399197","openid":"5B0CB916D4A9BDB5D838A3F66AC0B684","unionid":"UID_CAE5B3A01604A9F7B709D3BF934E7AA4"} );
 */

public class QQBean implements Serializable {
    String client_id;
    String openid;
    String unionid;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnioid() {
        return unionid;
    }

    public void setUnioid(String unioid) {
        this.unionid = unioid;
    }
}
