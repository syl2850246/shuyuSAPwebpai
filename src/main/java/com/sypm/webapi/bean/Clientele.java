package com.sypm.webapi.bean;

import com.sypm.webapi.utils.DataLen;

import java.util.Date;
import java.util.Objects;

public class Clientele {

    private String CLIENTID;
    @DataLen(200)
    private String KUNNR;
    @DataLen(200)
    private String NAME2;
    @DataLen(200)
    private String SORT1;
    @DataLen(200)
    private String SORT2;
    @DataLen(200)
    private String STREET;
    @DataLen(200)
    private String ZZKHXS;
    @DataLen(200)
    private String ZZSHDZ;
    @DataLen(200)
    private String TEL_NUMBER;
    @DataLen(200)
    private String ZPICKER_K;
    @DataLen(200)
    private String POST_CODE1;
    @DataLen(200)
    private String CITY1;
    @DataLen(200)
    private String COUNTRY;
    @DataLen(200)
    private String REGION;
    @DataLen(200)
    private String KLIMK;
    @DataLen(200)
    private String ZTERM;
    @DataLen(200)
    private String KTOKD;
    @DataLen(200)
    private String ZFPLX;
    @DataLen(200)
    private String ZZRYW;
    @DataLen(200)
    private String ZZRKP;
    @DataLen(200)
    private String ZZRJL;
    @DataLen(200)
    private String SPERR;
    @DataLen(200)
    private String LOEVM;
    @DataLen(200)
    private String CASSD;
    @DataLen(200)
    private String ZJYFW;
    @DataLen(200)
    private String ZZZSLX;
    @DataLen(200)
    private String ZZZSBH;
    private Date ZZYXQS;
    private Date ZZYXQZ;
    @DataLen(200)
    private String ZZLXR;
    @DataLen(200)
    private String ZZSFZH;
    @DataLen(200)
    private String ZZFZJG;
    @DataLen(200)
    private String ZZLXFS;
    @DataLen(200)
    private String ZZFDDBR;

    private String UPDATETIME;
    private String INSERTTIME;

    public String getCLIENTID() {
        return CLIENTID;
    }

    public void setCLIENTID(String CLIENTID) {
        this.CLIENTID = CLIENTID;
    }

    public String getKUNNR() {
        return KUNNR;
    }

    public void setKUNNR(String KUNNR) {
        this.KUNNR = KUNNR;
    }

    public String getNAME2() {
        return NAME2;
    }

    public void setNAME2(String NAME2) {
        this.NAME2 = NAME2;
    }

    public String getSORT1() {
        return SORT1;
    }

    public void setSORT1(String SORT1) {
        this.SORT1 = SORT1;
    }

    public String getSORT2() {
        return SORT2;
    }

    public void setSORT2(String SORT2) {
        this.SORT2 = SORT2;
    }

    public String getSTREET() {
        return STREET;
    }

    public void setSTREET(String STREET) {
        this.STREET = STREET;
    }

    public String getZZKHXS() {
        return ZZKHXS;
    }

    public void setZZKHXS(String ZZKHXS) {
        this.ZZKHXS = ZZKHXS;
    }

    public String getZZSHDZ() {
        return ZZSHDZ;
    }

    public void setZZSHDZ(String ZZSHDZ) {
        this.ZZSHDZ = ZZSHDZ;
    }

    public String getTEL_NUMBER() {
        return TEL_NUMBER;
    }

    public void setTEL_NUMBER(String TEL_NUMBER) {
        this.TEL_NUMBER = TEL_NUMBER;
    }

    public String getZPICKER_K() {
        return ZPICKER_K;
    }

    public void setZPICKER_K(String ZPICKER_K) {
        this.ZPICKER_K = ZPICKER_K;
    }

    public String getPOST_CODE1() {
        return POST_CODE1;
    }

    public void setPOST_CODE1(String POST_CODE1) {
        this.POST_CODE1 = POST_CODE1;
    }

    public String getCITY1() {
        return CITY1;
    }

    public void setCITY1(String CITY1) {
        this.CITY1 = CITY1;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }

    public String getREGION() {
        return REGION;
    }

    public void setREGION(String REGION) {
        this.REGION = REGION;
    }

    public String getKLIMK() {
        return KLIMK;
    }

    public void setKLIMK(String KLIMK) {
        this.KLIMK = KLIMK;
    }

    public String getZTERM() {
        return ZTERM;
    }

    public void setZTERM(String ZTERM) {
        this.ZTERM = ZTERM;
    }

    public String getKTOKD() {
        return KTOKD;
    }

    public void setKTOKD(String KTOKD) {
        this.KTOKD = KTOKD;
    }

    public String getZFPLX() {
        return ZFPLX;
    }

    public void setZFPLX(String ZFPLX) {
        this.ZFPLX = ZFPLX;
    }

    public String getZZRYW() {
        return ZZRYW;
    }

    public void setZZRYW(String ZZRYW) {
        this.ZZRYW = ZZRYW;
    }

    public String getZZRKP() {
        return ZZRKP;
    }

    public void setZZRKP(String ZZRKP) {
        this.ZZRKP = ZZRKP;
    }

    public String getZZRJL() {
        return ZZRJL;
    }

    public void setZZRJL(String ZZRJL) {
        this.ZZRJL = ZZRJL;
    }

    public String getSPERR() {
        return SPERR;
    }

    public void setSPERR(String SPERR) {
        this.SPERR = SPERR;
    }

    public String getLOEVM() {
        return LOEVM;
    }

    public void setLOEVM(String LOEVM) {
        this.LOEVM = LOEVM;
    }

    public String getCASSD() {
        return CASSD;
    }

    public void setCASSD(String CASSD) {
        this.CASSD = CASSD;
    }

    public String getZJYFW() {
        return ZJYFW;
    }

    public void setZJYFW(String ZJYFW) {
        this.ZJYFW = ZJYFW;
    }

    public String getZZZSLX() {
        return ZZZSLX;
    }

    public void setZZZSLX(String ZZZSLX) {
        this.ZZZSLX = ZZZSLX;
    }

    public String getZZZSBH() {
        return ZZZSBH;
    }

    public void setZZZSBH(String ZZZSBH) {
        this.ZZZSBH = ZZZSBH;
    }

    public Date getZZYXQS() {
        return ZZYXQS;
    }

    public void setZZYXQS(Date ZZYXQS) {
        this.ZZYXQS = ZZYXQS;
    }

    public Date getZZYXQZ() {
        return ZZYXQZ;
    }

    public void setZZYXQZ(Date ZZYXQZ) {
        this.ZZYXQZ = ZZYXQZ;
    }

    public String getZZLXR() {
        return ZZLXR;
    }

    public void setZZLXR(String ZZLXR) {
        this.ZZLXR = ZZLXR;
    }

    public String getZZSFZH() {
        return ZZSFZH;
    }

    public void setZZSFZH(String ZZSFZH) {
        this.ZZSFZH = ZZSFZH;
    }

    public String getZZFZJG() {
        return ZZFZJG;
    }

    public void setZZFZJG(String ZZFZJG) {
        this.ZZFZJG = ZZFZJG;
    }

    public String getZZLXFS() {
        return ZZLXFS;
    }

    public void setZZLXFS(String ZZLXFS) {
        this.ZZLXFS = ZZLXFS;
    }

    public String getZZFDDBR() {
        return ZZFDDBR;
    }

    public void setZZFDDBR(String ZZFDDBR) {
        this.ZZFDDBR = ZZFDDBR;
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
        Clientele clientele = (Clientele) o;
        return Objects.equals(KUNNR, clientele.KUNNR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(KUNNR);
    }
}
