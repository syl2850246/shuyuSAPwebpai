package com.sypm.webapi.bean;

import com.sypm.webapi.utils.DataLen;

import java.util.Objects;

public class Organization {

    private String ORGANIZATIONID;
    @DataLen(200)
    private String KUNNR;
    @DataLen(200)
    private String ZZKUNNR;
    @DataLen(200)
    private String WERKS;
    @DataLen(200)
    private String VKBUR;
    @DataLen(200)
    private String BEZEI;
    @DataLen(200)
    private String BUKRS;
    @DataLen(200)
    private String BUTXT;
    @DataLen(200)
    private String ZZDQ;
    @DataLen(200)
    private String ZZPQ;
    @DataLen(200)
    private String ZZDQT;
    @DataLen(200)
    private String ZZPQT;
    @DataLen(200)
    private String ZDQBH;
    @DataLen(200)
    private String ZDQMC;

    private String UPDATETIME;
    private String INSERTTIME;

    public String getORGANIZATIONID() {
        return ORGANIZATIONID;
    }

    public void setORGANIZATIONID(String ORGANIZATIONID) {
        this.ORGANIZATIONID = ORGANIZATIONID;
    }

    public String getKUNNR() {
        return KUNNR;
    }

    public void setKUNNR(String KUNNR) {
        this.KUNNR = KUNNR;
    }

    public String getZZKUNNR() {
        return ZZKUNNR;
    }

    public void setZZKUNNR(String ZZKUNNR) {
        this.ZZKUNNR = ZZKUNNR;
    }

    public String getWERKS() {
        return WERKS;
    }

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS;
    }

    public String getVKBUR() {
        return VKBUR;
    }

    public void setVKBUR(String VKBUR) {
        this.VKBUR = VKBUR;
    }

    public String getBEZEI() {
        return BEZEI;
    }

    public void setBEZEI(String BEZEI) {
        this.BEZEI = BEZEI;
    }

    public String getBUKRS() {
        return BUKRS;
    }

    public void setBUKRS(String BUKRS) {
        this.BUKRS = BUKRS;
    }

    public String getBUTXT() {
        return BUTXT;
    }

    public void setBUTXT(String BUTXT) {
        this.BUTXT = BUTXT;
    }

    public String getZZDQ() {
        return ZZDQ;
    }

    public void setZZDQ(String ZZDQ) {
        this.ZZDQ = ZZDQ;
    }

    public String getZZPQ() {
        return ZZPQ;
    }

    public void setZZPQ(String ZZPQ) {
        this.ZZPQ = ZZPQ;
    }

    public String getZZDQT() {
        return ZZDQT;
    }

    public void setZZDQT(String ZZDQT) {
        this.ZZDQT = ZZDQT;
    }

    public String getZZPQT() {
        return ZZPQT;
    }

    public void setZZPQT(String ZZPQT) {
        this.ZZPQT = ZZPQT;
    }

    public String getZDQBH() {
        return ZDQBH;
    }

    public void setZDQBH(String ZDQBH) {
        this.ZDQBH = ZDQBH;
    }

    public String getZDQMC() {
        return ZDQMC;
    }

    public void setZDQMC(String ZDQMC) {
        this.ZDQMC = ZDQMC;
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
        Organization that = (Organization) o;
        return Objects.equals(KUNNR, that.KUNNR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(KUNNR);
    }
}
