package com.sypm.webapi.bean;

public class ExReturnSupplierDataBean {

    // 供应商编码
    private String Lifnr;

    // 消息类型
    private String MSGTY;

    // 消息描述
    private String MSGTX;

    public String getLifnr() {
        return Lifnr;
    }

    public void setLifnr(String lifnr) {
        Lifnr = lifnr;
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
