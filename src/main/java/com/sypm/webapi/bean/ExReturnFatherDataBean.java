package com.sypm.webapi.bean;

import java.util.List;

public class ExReturnFatherDataBean<T> {

    // 消息类型
    private String MSGTY;

    // 消息描述
    private String MSGTX;

    // 商品返回信息
    private List<T> DATA;

    public String getMSGTY() {
        return MSGTY;
    }

    public void setMSGTY(String MSGTY) {
        this.MSGTY = MSGTY;
    }

    public String getMSGTX() {
        return MSGTX;
    }

    public void setMSGTX(String MSGTX) {
        this.MSGTX = MSGTX;
    }

    public List<T> getDATA() {
        return DATA;
    }

    public void setDATA(List<T> DATA) {
        this.DATA = DATA;
    }
}
