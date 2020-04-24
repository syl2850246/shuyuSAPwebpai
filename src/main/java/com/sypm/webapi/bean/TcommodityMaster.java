package com.sypm.webapi.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sypm.webapi.utils.DataLen;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

// 商品主数据
public class TcommodityMaster {

    private String COMMODITYID;

    // 商品编码 - 主键
    @DataLen(200)
    private String MATNR;

    // 商品描述
    @DataLen(200)
    private String MAKTX;

    // 商品类型
    @DataLen(200)
    private String MTART;

    // 大类
    @DataLen(200)
    private String  MATKL1;

    // 中类
    @DataLen(200)
    private String  MATKL2;

    // 小类
    @DataLen(200)
    private String  MATKL3;

    @DataLen(200)
    private String MATKL;

    @DataLen(200)
    private String ZZGG;

    @DataLen(200)
    private String BISMT;

    @DataLen(200)
    private String ZZGENERALNAME;

    @DataLen(200)
    private String ZZSPZJM;

    @DataLen(200)
    private String ZZPSBCZBZ;

    @DataLen(200)
    private String ZZLB_ID;

    @DataLen(200)
    private String ZZLIFNR_DES;

    @DataLen(200)
    private String LANDX;

    @DataLen(200)
    private String BEZEI;

    @DataLen(200)
    private String ZZJYFW;

    @DataLen(200)
    private String ZZISCF;

    @DataLen(200)
    private String ZZISCFHJ;

    @DataLen(200)
    private String ZZISYBPZ;

    private BigDecimal MHDHB;
    private BigDecimal MHDRZ;

    @DataLen(200)
    private String ZZJX_DESC;

    @DataLen(200)
    private String TAKLV;

    @DataLen(200)
    private String ZZISJEPZ;

    @DataLen(200)
    private String ZZBCXBZ;

    @DataLen(200)
    private String ZZISZYDB;

    @DataLen(200)
    private String ZZYHCS_DESC;

    @DataLen(200)
    private String ZZISYP;

    @DataLen(200)
    private String ZZPZWH;

    @JSONField(format = "yyyyMMdd", serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private Date ZZPZWHQX;

    @DataLen(200)
    private String ZZISDWPZ;
    @DataLen(200)
    private String ZZISZDYH;

    @JSONField(format = "yyyyMMdd", serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private Date ERSDA;
    @JSONField(format = "yyyyMMdd", serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private Date LAEDA;

    @DataLen(200)
    private String ZZZCSB;
    @DataLen(200)
    private String ZZGX;
    @DataLen(200)
    private String ZZXZ;
    @DataLen(200)
    private String ZZYSBZ;
    @DataLen(200)
    private String ZZISDZJGM;
    @DataLen(200)
    private String ZZNPFV;
    @DataLen(200)
    private String ZZNPFW;
    @DataLen(200)
    private String ZZNSAL;
    @DataLen(200)
    private String RAUBE_DESC;
    @DataLen(200)
    private String ZZHMBZ;
    @DataLen(200)
    private String ZZHTBZ;
    @DataLen(200)
    private String ZZJKBZ;
    @DataLen(200)
    private String ZZZYBZ;
    @DataLen(200)
    private String MEINS_DESC;

    private BigDecimal BRGEW;
    private BigDecimal NTGEW;

    @DataLen(200)
    private String GEWEI_DESC;

    private BigDecimal LAENG;
    private BigDecimal BREIT;
    private BigDecimal HOEHE;

    @DataLen(200)
    private String MEABM_DESC;
    @DataLen(200)
    private String MEINS_Z_DESC;

    private BigDecimal BRGEW_Z;
    private BigDecimal NTGEW_Z;

    @DataLen(200)
    private String GEWEI_Z_DESC;

    private BigDecimal LAENG_Z;
    private BigDecimal BREIT_Z;
    private BigDecimal HOEHE_Z;

    @DataLen(200)
    private String MEABM_Z_DESC;

    private BigDecimal UMBSZ_Z;

    @DataLen(200)
    private String MEINS_D_DESC;

    private BigDecimal BRGEW_D;
    private BigDecimal NTGEW_D;

    @DataLen(200)
    private String GEWEI_D_DESC;

    private BigDecimal LAENG_D;
    private BigDecimal BREIT_D;
    private BigDecimal HOEHE_D;

    @DataLen(200)
    private String MEABM_D_DESC;

    private BigDecimal UMBSZ_D;

    @DataLen(200)
    private String ZZJQPM;
    @DataLen(200)
    private String ZZJQGG;
    @DataLen(200)
    private String ZZJQDW_DESC;

    private BigDecimal ZZJQLSJ;
    private BigDecimal ZZGJXJ;
    private BigDecimal ZZGJJYJ;
    private BigDecimal ZZYYLSJ;
    private BigDecimal ZZSGBAJ;
    private BigDecimal ZZZGLSJ;
    private BigDecimal ZZZDLSJ;
    private BigDecimal ZZSPHYJ;
    private BigDecimal ZZSPLSJ;

    @DataLen(200)
    private String MEINS_B;

    // 更新时间
    private String UPDATETIME;
    private String INSERTTIME;

    public String getCOMMODITYID() {
        return COMMODITYID;
    }

    public void setCOMMODITYID(String COMMODITYID) {
        this.COMMODITYID = COMMODITYID;
    }

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }

    public String getMAKTX() {
        return MAKTX;
    }

    public void setMAKTX(String MAKTX) {
        this.MAKTX = MAKTX;
    }

    public String getMTART() {
        return MTART;
    }

    public void setMTART(String MTART) {
        this.MTART = MTART;
    }

    public String getMATKL1() {
        return MATKL1;
    }

    public void setMATKL1(String MATKL1) {
        this.MATKL1 = MATKL1;
    }

    public String getMATKL2() {
        return MATKL2;
    }

    public void setMATKL2(String MATKL2) {
        this.MATKL2 = MATKL2;
    }

    public String getMATKL3() {
        return MATKL3;
    }

    public void setMATKL3(String MATKL3) {
        this.MATKL3 = MATKL3;
    }

    public String getMATKL() {
        return MATKL;
    }

    public void setMATKL(String MATKL) {
        this.MATKL = MATKL;
    }

    public String getZZGG() {
        return ZZGG;
    }

    public void setZZGG(String ZZGG) {
        this.ZZGG = ZZGG;
    }

    public String getBISMT() {
        return BISMT;
    }

    public void setBISMT(String BISMT) {
        this.BISMT = BISMT;
    }

    public String getZZGENERALNAME() {
        return ZZGENERALNAME;
    }

    public void setZZGENERALNAME(String ZZGENERALNAME) {
        this.ZZGENERALNAME = ZZGENERALNAME;
    }

    public String getZZSPZJM() {
        return ZZSPZJM;
    }

    public void setZZSPZJM(String ZZSPZJM) {
        this.ZZSPZJM = ZZSPZJM;
    }

    public String getZZPSBCZBZ() {
        return ZZPSBCZBZ;
    }

    public void setZZPSBCZBZ(String ZZPSBCZBZ) {
        this.ZZPSBCZBZ = ZZPSBCZBZ;
    }

    public String getZZLB_ID() {
        return ZZLB_ID;
    }

    public void setZZLB_ID(String ZZLB_ID) {
        this.ZZLB_ID = ZZLB_ID;
    }

    public String getZZLIFNR_DES() {
        return ZZLIFNR_DES;
    }

    public void setZZLIFNR_DES(String ZZLIFNR_DES) {
        this.ZZLIFNR_DES = ZZLIFNR_DES;
    }

    public String getLANDX() {
        return LANDX;
    }

    public void setLANDX(String LANDX) {
        this.LANDX = LANDX;
    }

    public String getBEZEI() {
        return BEZEI;
    }

    public void setBEZEI(String BEZEI) {
        this.BEZEI = BEZEI;
    }

    public String getZZJYFW() {
        return ZZJYFW;
    }

    public void setZZJYFW(String ZZJYFW) {
        this.ZZJYFW = ZZJYFW;
    }

    public String getZZISCF() {
        return ZZISCF;
    }

    public void setZZISCF(String ZZISCF) {
        this.ZZISCF = ZZISCF;
    }

    public String getZZISCFHJ() {
        return ZZISCFHJ;
    }

    public void setZZISCFHJ(String ZZISCFHJ) {
        this.ZZISCFHJ = ZZISCFHJ;
    }

    public String getZZISYBPZ() {
        return ZZISYBPZ;
    }

    public void setZZISYBPZ(String ZZISYBPZ) {
        this.ZZISYBPZ = ZZISYBPZ;
    }

    public BigDecimal getMHDHB() {
        return MHDHB;
    }

    public void setMHDHB(BigDecimal MHDHB) {
        this.MHDHB = MHDHB;
    }

    public BigDecimal getMHDRZ() {
        return MHDRZ;
    }

    public void setMHDRZ(BigDecimal MHDRZ) {
        this.MHDRZ = MHDRZ;
    }

    public String getZZJX_DESC() {
        return ZZJX_DESC;
    }

    public void setZZJX_DESC(String ZZJX_DESC) {
        this.ZZJX_DESC = ZZJX_DESC;
    }

    public String getTAKLV() {
        return TAKLV;
    }

    public void setTAKLV(String TAKLV) {
        this.TAKLV = TAKLV;
    }

    public String getZZISJEPZ() {
        return ZZISJEPZ;
    }

    public void setZZISJEPZ(String ZZISJEPZ) {
        this.ZZISJEPZ = ZZISJEPZ;
    }

    public String getZZBCXBZ() {
        return ZZBCXBZ;
    }

    public void setZZBCXBZ(String ZZBCXBZ) {
        this.ZZBCXBZ = ZZBCXBZ;
    }

    public String getZZISZYDB() {
        return ZZISZYDB;
    }

    public void setZZISZYDB(String ZZISZYDB) {
        this.ZZISZYDB = ZZISZYDB;
    }

    public String getZZYHCS_DESC() {
        return ZZYHCS_DESC;
    }

    public void setZZYHCS_DESC(String ZZYHCS_DESC) {
        this.ZZYHCS_DESC = ZZYHCS_DESC;
    }

    public String getZZISYP() {
        return ZZISYP;
    }

    public void setZZISYP(String ZZISYP) {
        this.ZZISYP = ZZISYP;
    }

    public String getZZPZWH() {
        return ZZPZWH;
    }

    public void setZZPZWH(String ZZPZWH) {
        this.ZZPZWH = ZZPZWH;
    }

    public Date getZZPZWHQX() {
        return ZZPZWHQX;
    }

    public void setZZPZWHQX(Date ZZPZWHQX) {
        this.ZZPZWHQX = ZZPZWHQX;
    }

    public Date getERSDA() {
        return ERSDA;
    }

    public void setERSDA(Date ERSDA) {
        this.ERSDA = ERSDA;
    }

    public Date getLAEDA() {
        return LAEDA;
    }

    public void setLAEDA(Date LAEDA) {
        this.LAEDA = LAEDA;
    }

    public String getZZISDWPZ() {
        return ZZISDWPZ;
    }

    public void setZZISDWPZ(String ZZISDWPZ) {
        this.ZZISDWPZ = ZZISDWPZ;
    }

    public String getZZISZDYH() {
        return ZZISZDYH;
    }

    public void setZZISZDYH(String ZZISZDYH) {
        this.ZZISZDYH = ZZISZDYH;
    }

    public String getZZZCSB() {
        return ZZZCSB;
    }

    public void setZZZCSB(String ZZZCSB) {
        this.ZZZCSB = ZZZCSB;
    }

    public String getZZGX() {
        return ZZGX;
    }

    public void setZZGX(String ZZGX) {
        this.ZZGX = ZZGX;
    }

    public String getZZXZ() {
        return ZZXZ;
    }

    public void setZZXZ(String ZZXZ) {
        this.ZZXZ = ZZXZ;
    }

    public String getZZYSBZ() {
        return ZZYSBZ;
    }

    public void setZZYSBZ(String ZZYSBZ) {
        this.ZZYSBZ = ZZYSBZ;
    }

    public String getZZISDZJGM() {
        return ZZISDZJGM;
    }

    public void setZZISDZJGM(String ZZISDZJGM) {
        this.ZZISDZJGM = ZZISDZJGM;
    }

    public String getZZNPFV() {
        return ZZNPFV;
    }

    public void setZZNPFV(String ZZNPFV) {
        this.ZZNPFV = ZZNPFV;
    }

    public String getZZNPFW() {
        return ZZNPFW;
    }

    public void setZZNPFW(String ZZNPFW) {
        this.ZZNPFW = ZZNPFW;
    }

    public String getZZNSAL() {
        return ZZNSAL;
    }

    public void setZZNSAL(String ZZNSAL) {
        this.ZZNSAL = ZZNSAL;
    }

    public String getRAUBE_DESC() {
        return RAUBE_DESC;
    }

    public void setRAUBE_DESC(String RAUBE_DESC) {
        this.RAUBE_DESC = RAUBE_DESC;
    }

    public String getZZHMBZ() {
        return ZZHMBZ;
    }

    public void setZZHMBZ(String ZZHMBZ) {
        this.ZZHMBZ = ZZHMBZ;
    }

    public String getZZHTBZ() {
        return ZZHTBZ;
    }

    public void setZZHTBZ(String ZZHTBZ) {
        this.ZZHTBZ = ZZHTBZ;
    }

    public String getZZJKBZ() {
        return ZZJKBZ;
    }

    public void setZZJKBZ(String ZZJKBZ) {
        this.ZZJKBZ = ZZJKBZ;
    }

    public String getZZZYBZ() {
        return ZZZYBZ;
    }

    public void setZZZYBZ(String ZZZYBZ) {
        this.ZZZYBZ = ZZZYBZ;
    }

    public String getMEINS_DESC() {
        return MEINS_DESC;
    }

    public void setMEINS_DESC(String MEINS_DESC) {
        this.MEINS_DESC = MEINS_DESC;
    }

    public BigDecimal getBRGEW() {
        return BRGEW;
    }

    public void setBRGEW(BigDecimal BRGEW) {
        this.BRGEW = BRGEW;
    }

    public BigDecimal getNTGEW() {
        return NTGEW;
    }

    public void setNTGEW(BigDecimal NTGEW) {
        this.NTGEW = NTGEW;
    }

    public String getGEWEI_DESC() {
        return GEWEI_DESC;
    }

    public void setGEWEI_DESC(String GEWEI_DESC) {
        this.GEWEI_DESC = GEWEI_DESC;
    }

    public BigDecimal getLAENG() {
        return LAENG;
    }

    public void setLAENG(BigDecimal LAENG) {
        this.LAENG = LAENG;
    }

    public BigDecimal getBREIT() {
        return BREIT;
    }

    public void setBREIT(BigDecimal BREIT) {
        this.BREIT = BREIT;
    }

    public BigDecimal getHOEHE() {
        return HOEHE;
    }

    public void setHOEHE(BigDecimal HOEHE) {
        this.HOEHE = HOEHE;
    }

    public String getMEABM_DESC() {
        return MEABM_DESC;
    }

    public void setMEABM_DESC(String MEABM_DESC) {
        this.MEABM_DESC = MEABM_DESC;
    }

    public String getMEINS_Z_DESC() {
        return MEINS_Z_DESC;
    }

    public void setMEINS_Z_DESC(String MEINS_Z_DESC) {
        this.MEINS_Z_DESC = MEINS_Z_DESC;
    }

    public BigDecimal getBRGEW_Z() {
        return BRGEW_Z;
    }

    public void setBRGEW_Z(BigDecimal BRGEW_Z) {
        this.BRGEW_Z = BRGEW_Z;
    }

    public BigDecimal getNTGEW_Z() {
        return NTGEW_Z;
    }

    public void setNTGEW_Z(BigDecimal NTGEW_Z) {
        this.NTGEW_Z = NTGEW_Z;
    }

    public String getGEWEI_Z_DESC() {
        return GEWEI_Z_DESC;
    }

    public void setGEWEI_Z_DESC(String GEWEI_Z_DESC) {
        this.GEWEI_Z_DESC = GEWEI_Z_DESC;
    }

    public BigDecimal getLAENG_Z() {
        return LAENG_Z;
    }

    public void setLAENG_Z(BigDecimal LAENG_Z) {
        this.LAENG_Z = LAENG_Z;
    }

    public BigDecimal getBREIT_Z() {
        return BREIT_Z;
    }

    public void setBREIT_Z(BigDecimal BREIT_Z) {
        this.BREIT_Z = BREIT_Z;
    }

    public BigDecimal getHOEHE_Z() {
        return HOEHE_Z;
    }

    public void setHOEHE_Z(BigDecimal HOEHE_Z) {
        this.HOEHE_Z = HOEHE_Z;
    }

    public String getMEABM_Z_DESC() {
        return MEABM_Z_DESC;
    }

    public void setMEABM_Z_DESC(String MEABM_Z_DESC) {
        this.MEABM_Z_DESC = MEABM_Z_DESC;
    }

    public BigDecimal getUMBSZ_Z() {
        return UMBSZ_Z;
    }

    public void setUMBSZ_Z(BigDecimal UMBSZ_Z) {
        this.UMBSZ_Z = UMBSZ_Z;
    }

    public String getMEINS_D_DESC() {
        return MEINS_D_DESC;
    }

    public void setMEINS_D_DESC(String MEINS_D_DESC) {
        this.MEINS_D_DESC = MEINS_D_DESC;
    }

    public BigDecimal getBRGEW_D() {
        return BRGEW_D;
    }

    public void setBRGEW_D(BigDecimal BRGEW_D) {
        this.BRGEW_D = BRGEW_D;
    }

    public BigDecimal getNTGEW_D() {
        return NTGEW_D;
    }

    public void setNTGEW_D(BigDecimal NTGEW_D) {
        this.NTGEW_D = NTGEW_D;
    }

    public String getGEWEI_D_DESC() {
        return GEWEI_D_DESC;
    }

    public void setGEWEI_D_DESC(String GEWEI_D_DESC) {
        this.GEWEI_D_DESC = GEWEI_D_DESC;
    }

    public BigDecimal getLAENG_D() {
        return LAENG_D;
    }

    public void setLAENG_D(BigDecimal LAENG_D) {
        this.LAENG_D = LAENG_D;
    }

    public BigDecimal getBREIT_D() {
        return BREIT_D;
    }

    public void setBREIT_D(BigDecimal BREIT_D) {
        this.BREIT_D = BREIT_D;
    }

    public BigDecimal getHOEHE_D() {
        return HOEHE_D;
    }

    public void setHOEHE_D(BigDecimal HOEHE_D) {
        this.HOEHE_D = HOEHE_D;
    }

    public String getMEABM_D_DESC() {
        return MEABM_D_DESC;
    }

    public void setMEABM_D_DESC(String MEABM_D_DESC) {
        this.MEABM_D_DESC = MEABM_D_DESC;
    }

    public BigDecimal getUMBSZ_D() {
        return UMBSZ_D;
    }

    public void setUMBSZ_D(BigDecimal UMBSZ_D) {
        this.UMBSZ_D = UMBSZ_D;
    }

    public String getZZJQPM() {
        return ZZJQPM;
    }

    public void setZZJQPM(String ZZJQPM) {
        this.ZZJQPM = ZZJQPM;
    }

    public String getZZJQGG() {
        return ZZJQGG;
    }

    public void setZZJQGG(String ZZJQGG) {
        this.ZZJQGG = ZZJQGG;
    }

    public String getZZJQDW_DESC() {
        return ZZJQDW_DESC;
    }

    public void setZZJQDW_DESC(String ZZJQDW_DESC) {
        this.ZZJQDW_DESC = ZZJQDW_DESC;
    }

    public BigDecimal getZZJQLSJ() {
        return ZZJQLSJ;
    }

    public void setZZJQLSJ(BigDecimal ZZJQLSJ) {
        this.ZZJQLSJ = ZZJQLSJ;
    }

    public BigDecimal getZZGJXJ() {
        return ZZGJXJ;
    }

    public void setZZGJXJ(BigDecimal ZZGJXJ) {
        this.ZZGJXJ = ZZGJXJ;
    }

    public BigDecimal getZZGJJYJ() {
        return ZZGJJYJ;
    }

    public void setZZGJJYJ(BigDecimal ZZGJJYJ) {
        this.ZZGJJYJ = ZZGJJYJ;
    }

    public BigDecimal getZZYYLSJ() {
        return ZZYYLSJ;
    }

    public void setZZYYLSJ(BigDecimal ZZYYLSJ) {
        this.ZZYYLSJ = ZZYYLSJ;
    }

    public BigDecimal getZZSGBAJ() {
        return ZZSGBAJ;
    }

    public void setZZSGBAJ(BigDecimal ZZSGBAJ) {
        this.ZZSGBAJ = ZZSGBAJ;
    }

    public BigDecimal getZZZGLSJ() {
        return ZZZGLSJ;
    }

    public void setZZZGLSJ(BigDecimal ZZZGLSJ) {
        this.ZZZGLSJ = ZZZGLSJ;
    }

    public BigDecimal getZZZDLSJ() {
        return ZZZDLSJ;
    }

    public void setZZZDLSJ(BigDecimal ZZZDLSJ) {
        this.ZZZDLSJ = ZZZDLSJ;
    }

    public BigDecimal getZZSPHYJ() {
        return ZZSPHYJ;
    }

    public void setZZSPHYJ(BigDecimal ZZSPHYJ) {
        this.ZZSPHYJ = ZZSPHYJ;
    }

    public BigDecimal getZZSPLSJ() {
        return ZZSPLSJ;
    }

    public void setZZSPLSJ(BigDecimal ZZSPLSJ) {
        this.ZZSPLSJ = ZZSPLSJ;
    }

    public String getMEINS_B() {
        return MEINS_B;
    }

    public void setMEINS_B(String MEINS_B) {
        this.MEINS_B = MEINS_B;
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
        TcommodityMaster that = (TcommodityMaster) o;
        return Objects.equals(MATNR, that.MATNR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MATNR);
    }
}
