package com.sypm.webapi.bean;

import com.sypm.webapi.utils.DataLen;

import java.util.Date;
import java.util.Objects;

public class ProfitCenter {

    private String PROFITCENTERID;
    @DataLen(200)
    private String PRCTR;
    @DataLen(200)
    private Date DATBI;
    @DataLen(200)
    private String KOKRS;
    @DataLen(200)
    private Date DATAB;
    @DataLen(200)
    private String ABTEI;
    @DataLen(200)
    private Date ERSDA;
    @DataLen(200)
    private String ORT01;
    @DataLen(200)
    private String STRAS;
    @DataLen(200)
    private String BUKRS;
    @DataLen(200)
    private String KTEXT;
    @DataLen(200)
    private String LTEXT;

    private String UPDATETIME;
    private String INSERTTIME;

    public String getPROFITCENTERID() {
        return PROFITCENTERID;
    }

    public void setPROFITCENTERID(String PROFITCENTERID) {
        this.PROFITCENTERID = PROFITCENTERID;
    }

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

    public Date getDATAB() {
        return DATAB;
    }

    public void setDATAB(Date DATAB) {
        this.DATAB = DATAB;
    }

    public String getABTEI() {
        return ABTEI;
    }

    public void setABTEI(String ABTEI) {
        this.ABTEI = ABTEI;
    }

    public Date getERSDA() {
        return ERSDA;
    }

    public void setERSDA(Date ERSDA) {
        this.ERSDA = ERSDA;
    }

    public String getORT01() {
        return ORT01;
    }

    public void setORT01(String ORT01) {
        this.ORT01 = ORT01;
    }

    public String getSTRAS() {
        return STRAS;
    }

    public void setSTRAS(String STRAS) {
        this.STRAS = STRAS;
    }

    public String getBUKRS() {
        return BUKRS;
    }

    public void setBUKRS(String BUKRS) {
        this.BUKRS = BUKRS;
    }

    public String getKTEXT() {
        return KTEXT;
    }

    public void setKTEXT(String KTEXT) {
        this.KTEXT = KTEXT;
    }

    public String getLTEXT() {
        return LTEXT;
    }

    public void setLTEXT(String LTEXT) {
        this.LTEXT = LTEXT;
    }

    public String getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(String UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }

    public String getINSERTTIME() {
        return INSERTTIME;
    }

    public void setINSERTTIME(String INSERTTIME) {
        this.INSERTTIME = INSERTTIME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfitCenter that = (ProfitCenter) o;
        return Objects.equals(PRCTR, that.PRCTR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(PRCTR);
    }
}
