package com.sypm.webapi.bean;

import com.sypm.webapi.utils.DataLen;

import java.util.Objects;

public class Supplier {

    private String SUPPLIERID;
    @DataLen(200)
    private String LIFNR;
    @DataLen(200)
    private String NAME2;
    @DataLen(200)
    private String SORT1;
    @DataLen(200)
    private String SORT2;
    @DataLen(200)
    private String STREET;
    @DataLen(200)
    private String STCD5;
    @DataLen(200)
    private String TEL_NUMBER;
    @DataLen(200)
    private String POST_CODE1;
    @DataLen(200)
    private String CITY1;
    @DataLen(200)
    private String COUNTRY;
    @DataLen(200)
    private String REGION;
    @DataLen(200)
    private String ZZJYFS;
    @DataLen(200)
    private String ZZJJXZ;
    @DataLen(200)
    private String ZZCKDZ;
    @DataLen(200)
    private String ZZGYSJG;
    @DataLen(200)
    private String ZZGYSTY;
    @DataLen(200)
    private String ZZSHTXBA;
    @DataLen(200)
    private String ZZFRDB;
    @DataLen(200)
    private String ZZQYFZR;
    @DataLen(200)
    private String ZZSFYP;
    @DataLen(200)
    private String ZZGYSXZ;
    @DataLen(200)
    private String ZZHZKSRQ;
    @DataLen(200)
    private String ZTERM;
    @DataLen(200)
    private String KTOKK;
    @DataLen(200)
    private String SPERR;
    @DataLen(200)
    private String SPERM;
    @DataLen(200)
    private String LOEVM;

    private String UPDATETIME;
    private String INSERTTIME;

    public String getSUPPLIERID() {
        return SUPPLIERID;
    }

    public void setSUPPLIERID(String SUPPLIERID) {
        this.SUPPLIERID = SUPPLIERID;
    }

    public String getLIFNR() {
        return LIFNR;
    }

    public void setLIFNR(String LIFNR) {
        this.LIFNR = LIFNR;
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

    public String getSTCD5() {
        return STCD5;
    }

    public void setSTCD5(String STCD5) {
        this.STCD5 = STCD5;
    }

    public String getTEL_NUMBER() {
        return TEL_NUMBER;
    }

    public void setTEL_NUMBER(String TEL_NUMBER) {
        this.TEL_NUMBER = TEL_NUMBER;
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

    public String getZZJYFS() {
        return ZZJYFS;
    }

    public void setZZJYFS(String ZZJYFS) {
        this.ZZJYFS = ZZJYFS;
    }

    public String getZZJJXZ() {
        return ZZJJXZ;
    }

    public void setZZJJXZ(String ZZJJXZ) {
        this.ZZJJXZ = ZZJJXZ;
    }

    public String getZZCKDZ() {
        return ZZCKDZ;
    }

    public void setZZCKDZ(String ZZCKDZ) {
        this.ZZCKDZ = ZZCKDZ;
    }

    public String getZZGYSJG() {
        return ZZGYSJG;
    }

    public void setZZGYSJG(String ZZGYSJG) {
        this.ZZGYSJG = ZZGYSJG;
    }

    public String getZZGYSTY() {
        return ZZGYSTY;
    }

    public void setZZGYSTY(String ZZGYSTY) {
        this.ZZGYSTY = ZZGYSTY;
    }

    public String getZZSHTXBA() {
        return ZZSHTXBA;
    }

    public void setZZSHTXBA(String ZZSHTXBA) {
        this.ZZSHTXBA = ZZSHTXBA;
    }

    public String getZZFRDB() {
        return ZZFRDB;
    }

    public void setZZFRDB(String ZZFRDB) {
        this.ZZFRDB = ZZFRDB;
    }

    public String getZZQYFZR() {
        return ZZQYFZR;
    }

    public void setZZQYFZR(String ZZQYFZR) {
        this.ZZQYFZR = ZZQYFZR;
    }

    public String getZZSFYP() {
        return ZZSFYP;
    }

    public void setZZSFYP(String ZZSFYP) {
        this.ZZSFYP = ZZSFYP;
    }

    public String getZZGYSXZ() {
        return ZZGYSXZ;
    }

    public void setZZGYSXZ(String ZZGYSXZ) {
        this.ZZGYSXZ = ZZGYSXZ;
    }

    public String getZZHZKSRQ() {
        return ZZHZKSRQ;
    }

    public void setZZHZKSRQ(String ZZHZKSRQ) {
        this.ZZHZKSRQ = ZZHZKSRQ;
    }

    public String getZTERM() {
        return ZTERM;
    }

    public void setZTERM(String ZTERM) {
        this.ZTERM = ZTERM;
    }

    public String getKTOKK() {
        return KTOKK;
    }

    public void setKTOKK(String KTOKK) {
        this.KTOKK = KTOKK;
    }

    public String getSPERR() {
        return SPERR;
    }

    public void setSPERR(String SPERR) {
        this.SPERR = SPERR;
    }

    public String getSPERM() {
        return SPERM;
    }

    public void setSPERM(String SPERM) {
        this.SPERM = SPERM;
    }

    public String getLOEVM() {
        return LOEVM;
    }

    public void setLOEVM(String LOEVM) {
        this.LOEVM = LOEVM;
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
        Supplier supplier = (Supplier) o;
        return Objects.equals(LIFNR, supplier.LIFNR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(LIFNR);
    }
}
