package com.sypm.webapi.bean;

public class ExReturnDataBean {

    // 商品编码
    private String MATNR;

    // 消息类型
    private String MSGTY;

    // 消息描述
    private String MSGTX;

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }

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
}
