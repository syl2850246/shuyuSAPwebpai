package com.sypm.webapi.bean;

import java.util.Date;

public class ExReturnCostCenterDataBean {

    // 成本中心编码
    private String KOSTL;

    // 有效截至日期
    private Date DATBI;

    // 控制范围
    private String KOKRS;

    // 消息类型
    private String MSGTY;

    // 消息描述
    private String MSGTX;

    public String getKOSTL() {
        return KOSTL;
    }

    public void setKOSTL(String KOSTL) {
        this.KOSTL = KOSTL;
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
