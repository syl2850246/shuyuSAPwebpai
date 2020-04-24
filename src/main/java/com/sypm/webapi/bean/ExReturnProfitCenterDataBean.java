package com.sypm.webapi.bean;

import java.util.Date;

public class ExReturnProfitCenterDataBean {

    private String PRCTR;
    private Date DATBI;
    private String KOKRS;
    private String MSGTY;
    private String MSGTX;

    public String getPRCTR() {
        return PRCTR;
    }

    public void setPRCTR(String PRCTR) {
        this.PRCTR = PRCTR;
    }

    public Date getDATBI() {
        return DATBI;
    }

    public void setDATBI(Date DATBI) {
        this.DATBI = DATBI;
    }

    public String getKOKRS() {
        return KOKRS;
    }

    public void setKOKRS(String KOKRS) {
        this.KOKRS = KOKRS;
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
