package com.sypm.webapi.webService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sypm.webapi.bean.*;
import com.sypm.webapi.mapper.CommonMapper;
import com.sypm.webapi.utils.CommonUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.text.SimpleDateFormat;
import java.util.*;

@WebService(name = "CommonService", targetNamespace = "http://webService.webapi.sypm.com", endpointInterface = "com.sypm.webapi.webService.CommonService")
@Component
@Transactional
public class CommonServiceImpl implements CommonService{

    // 用于记录日志
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CommonServiceImpl.class);

    @Resource
    private CommonMapper commonMapper;

    /**
     * 解析SAP传递过来的参数
     * @param IM_CTRL
     * @param IM_DATA
     * @return
     */
    @Override
    public @WebResult(name =  "Sap2Java_RESULT")String Sap2Java(@WebParam(name = "IM_CTRL") String IM_CTRL, @WebParam(name = "IM_DATA")String IM_DATA) {

        // 解析区分控制参数
        JSONObject jsonIMCTRL = JSONObject.parseObject(IM_CTRL);
        // 解析主数据
        JSONObject jsonIMDATA = JSONObject.parseObject(IM_DATA);

        // 将控制参数解析
        ImDataBean imDataBean = JSON.parseObject(jsonIMCTRL.toJSONString(), ImDataBean.class);
        // 将主数据解析为JSON数组
        JSONArray imDataArray = jsonIMDATA.getJSONObject("DATA1").getJSONArray("DATA");

        if (imDataBean.getZINTR_OO().equals("ZPT01")) {

            // 商品主数据下发
            return CommodityMasterDataAccept(imDataArray);
        } else if (imDataBean.getZINTR_OO().equals("ZPT02")) {

            // 门店主数据下发
            return DrugStoresDataAccept(imDataArray);
        } else if (imDataBean.getZINTR_OO().equals("ZPT03")) {

            // 客户主数据下发
            return ClienteleDataAccept(imDataArray);
        } else if (imDataBean.getZINTR_OO().equals("ZPT04")) {

            // 供应商主数据下发
            return SupplierDataAccept(imDataArray);
        } else if (imDataBean.getZINTR_OO().equals("ZPT06")) {

            // 成本中心主数据下发
            return CostCenterDataAccept(imDataArray);
        } else if (imDataBean.getZINTR_OO().equals("ZPT08")){

            // 组织架构中心主数据下发
            return OrganizationDataAccept(imDataArray);
        } else {

            // 利润中心主数据下发
            return ProfiCenterDataAccept(imDataArray);
        }
    }

    /**
     *  商品主数据下发接口
     * @param imDataArray
     * @return
     */
    private String CommodityMasterDataAccept(JSONArray imDataArray) {

        List<TcommodityMaster> imDataList = JSON.parseObject(imDataArray.toJSONString(), new TypeReference<List<TcommodityMaster>>(){});

        // 判断传递过来的信息是否存在异常
        Map<TcommodityMaster, String> tcommodityMasterStringMap = CommonUtils.recordErrorInfo(imDataList);
        for (Map.Entry<TcommodityMaster, String> entry : tcommodityMasterStringMap.entrySet()) {

            imDataList.remove(entry.getKey());
        }

        // 进行调整修改
        // 用于存放商品编码
        List<String> matnrList = new ArrayList<String>();
        List<TcommodityMaster> nullCommodityList = new ArrayList<>();
        for (TcommodityMaster tcommodityMaster:imDataList) {

            if (!"".equals(tcommodityMaster.getMATNR()) && null != tcommodityMaster.getMATNR()) {

                matnrList.add(tcommodityMaster.getMATNR());
            } else {

                nullCommodityList.add(tcommodityMaster);
            }
        }

        // 进行数据相关的去重及其重复数据记录
        List<String> duplicateMatnrList = CommonUtils.frequencyOfListElements(matnrList);
        matnrList = CommonUtils.removeDuplicateValue(matnrList);
        imDataList = CommonUtils.removeDuplicateValue(imDataList);

        // 删除主键为空的商品
        if(nullCommodityList.size() > 0) {

            for (TcommodityMaster tcommodityMaster : nullCommodityList) {

                imDataList.remove(tcommodityMaster);
            }
        }

        // 记录时间
        long startTime = new Date().getTime();
        // 取出存到数据库的商品信息
        List<TcommodityMaster> tcommodityMasterList = new ArrayList<TcommodityMaster>();
        if (matnrList.size() > 450) {

            // 集合分割，参数过大
            int size = matnrList.size() / 450;
            List<List<String>> splitlists = CommonUtils.averageAssign(matnrList, size);

            for (List<String> list : splitlists) {

                List<TcommodityMaster> splitList = new ArrayList<TcommodityMaster>();
                splitList = commonMapper.findTcommmodityByMatnr(list);
                for (TcommodityMaster tcommodityMaster : splitList) {

                    tcommodityMasterList.add(tcommodityMaster);
                }
            }
        }  else {

            // 成功存到数据库的商品数据数目
            tcommodityMasterList = commonMapper.findTcommmodityByMatnr(matnrList);
        }
        log.info("结束一次下发商品信息查询时间： " + ((new Date().getTime() - startTime)));

        // 用于插入商品数据记录
        List<TcommodityMaster> insertBatchCommodityMasterList = new ArrayList<TcommodityMaster>();
        // 用于修改商品数据记录
        List<TcommodityMaster> updateBatchCommodityMasterList = new ArrayList<TcommodityMaster>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (TcommodityMaster tcommodityMaster : imDataList) {

            tcommodityMaster.setCOMMODITYID(UUID.randomUUID().toString());
            // 传递过来的商品数据中已经存入到数据库
            if (tcommodityMasterList.contains(tcommodityMaster)) {

                String updateTime = sdf.format(new Date());
                tcommodityMaster.setUPDATETIME(updateTime);
                updateBatchCommodityMasterList.add(tcommodityMaster);
            } else {

                // 传递过来的数据未写入到数据库
                String insertTime = sdf.format(new Date());
                tcommodityMaster.setINSERTTIME(insertTime);
                insertBatchCommodityMasterList.add(tcommodityMaster);
            }
        }

        // 成功存入数据库的商品数据数目
        Integer insertRows = 0;
        // 成功更新数据库的商品数据数目
        Integer updateRows = 0;
        // 总操作数据库的商品数据数目
        Integer totalRows = 0;
        int numRows = updateBatchCommodityMasterList.size() + insertBatchCommodityMasterList.size();
        // 如果有需要新插入到数据库的商品数据
        if (insertBatchCommodityMasterList.size() > 0) {

            if (insertBatchCommodityMasterList.size() > 40) {

                int size = insertBatchCommodityMasterList.size() / 20;
                List<List<TcommodityMaster>> splitlists = CommonUtils.averageAssign(insertBatchCommodityMasterList, size);

                for (List<TcommodityMaster> list : splitlists) {

                    // 成功存到数据库的客户数据数目
                    insertRows = commonMapper.addBatchCommodity(list);
                }
            } else {

                // 成功存到数据库的商品数据数目
                insertRows = commonMapper.addBatchCommodity(insertBatchCommodityMasterList);
            }
        }

        // 如果有需要更新数据库的商品数据
        if (updateBatchCommodityMasterList.size() > 0) {

            if(updateBatchCommodityMasterList.size() > 40) {

                int size = updateBatchCommodityMasterList.size() / 20;
                List<List<TcommodityMaster>> splitlists = CommonUtils.averageAssign(updateBatchCommodityMasterList, size);

                for (List<TcommodityMaster> list : splitlists) {

                    // 成功存到数据库的客户数据数目
                    updateRows = commonMapper.updateBatchCommodity(list);
                }
            } else {

                // 成功更新已插入道数据库的商品数据数目
                updateRows = commonMapper.updateBatchCommodity(updateBatchCommodityMasterList);
            }
        }
        // 总行数
        totalRows = insertRows + updateRows;

        if ((totalRows != 0 || insertRows !=0 || updateRows != 0) && numRows == imDataList.size()) {

            System.out.println("已全部完成,总操作商品数(更新+添加):" + numRows);
        } else {

            System.out.println("未全部完成(有遗漏),总操作商品数(更新+添加):" + numRows);
        }

        // 存储返回数据 - 总返回值
        ExDataBean<ExReturnFatherDataBean<ExReturnCommodityDataBean>> exDataBean = new ExDataBean<ExReturnFatherDataBean<ExReturnCommodityDataBean>>();
        // 二级嵌套 - 返回值
        ExReturnFatherDataBean<ExReturnCommodityDataBean> exReturnFatherDataBean = new ExReturnFatherDataBean<ExReturnCommodityDataBean>();
        // 用于存放返回成功的商品信息
        List<ExReturnCommodityDataBean> exReturnSuccessCommodityDataBeanList = new ArrayList<ExReturnCommodityDataBean>();
        // 用于存放返回失败的商品信息
        List<ExReturnCommodityDataBean> exReturnFailedCommodityDataBeanList = new ArrayList<ExReturnCommodityDataBean>();

        // 遍历从SAP传递过来的商品信息
        for (TcommodityMaster tcommodityMaster : imDataList) {

            ExReturnCommodityDataBean exReturnCommodityDataBean = new ExReturnCommodityDataBean();
            exReturnCommodityDataBean.setMATNR(tcommodityMaster.getMATNR());
            // 如果该条商品信息已经存到数据库
            if (insertBatchCommodityMasterList.contains(tcommodityMaster) || updateBatchCommodityMasterList.contains(tcommodityMaster)) {

                // 定义单条返回的商品信息
                exReturnCommodityDataBean.setMSGTY("S");
                exReturnCommodityDataBean.setMSGTX("接收成功");
                exReturnSuccessCommodityDataBeanList.add(exReturnCommodityDataBean);
            } else {

                // 该条商品数据插入失败
                exReturnCommodityDataBean.setMSGTY("E");
                exReturnCommodityDataBean.setMSGTX("接收失败，请检查该商品数据信息...");
                exReturnFailedCommodityDataBeanList.add(exReturnCommodityDataBean);
            }
        }

        // 组装异常信息
        for (Map.Entry<TcommodityMaster, String> entry : tcommodityMasterStringMap.entrySet()) {

            ExReturnCommodityDataBean exReturnCommodityDataBean = new ExReturnCommodityDataBean();
            exReturnCommodityDataBean.setMATNR(entry.getKey().getMATNR());
            // 该条商品数据插入失败
            exReturnCommodityDataBean.setMSGTY("E");
            exReturnCommodityDataBean.setMSGTX(entry.getValue());
            exReturnFailedCommodityDataBeanList.add(exReturnCommodityDataBean);
        }

        // 组装传递重复数据的异常信息
        for (String  matnr : duplicateMatnrList) {

            ExReturnCommodityDataBean exReturnCommodityDataBean = new ExReturnCommodityDataBean();
            exReturnCommodityDataBean.setMATNR(matnr);
            // 该条商品数据插入失败
            exReturnCommodityDataBean.setMSGTY("E");
            exReturnCommodityDataBean.setMSGTX("该条商品存在重复..");
            exReturnFailedCommodityDataBeanList.add(exReturnCommodityDataBean);
        }

        if (exReturnFailedCommodityDataBeanList.size() > 0) {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("E");
            exReturnFatherDataBean.setMSGTX("接收失败");

            // 将成功的也返回
            for (ExReturnCommodityDataBean exReturnCommodityDataBean : exReturnSuccessCommodityDataBeanList) {

                exReturnFailedCommodityDataBeanList.add(exReturnCommodityDataBean);
            }
            exReturnFatherDataBean.setDATA(exReturnFailedCommodityDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        } else {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("S");
            exReturnFatherDataBean.setMSGTX("接收成功");
            exReturnFatherDataBean.setDATA(exReturnSuccessCommodityDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        }
        log.info("返回商品主数据信息:" + JSON.toJSONString(exDataBean));
        log.info("结束一次下发商品信息时间： " + ((new Date().getTime() - startTime)));
        return JSON.toJSONString(exDataBean);
    }

    /**
     *  门店主数据下发接口
     * @param imDataArray
     * @return
     */
    private String DrugStoresDataAccept(JSONArray imDataArray) {

        List<DrugStores> imDataList = JSON.parseObject(imDataArray.toJSONString(), new TypeReference<List<DrugStores>>(){});

        // 判断传递过来的信息是否存在异常
        Map<DrugStores, String> stringMap = CommonUtils.recordErrorInfo(imDataList);
        for (Map.Entry<DrugStores, String> entry : stringMap.entrySet()) {

            imDataList.remove(entry.getKey());
        }

        // 进行调整修改
        // 用于存放门店编码
        List<String> werksList = new ArrayList<String>();
        List<DrugStores> nullDrugStoresList = new ArrayList<>();
        for (DrugStores drugStores:imDataList) {

            if (!"".equals(drugStores.getWERKS()) && null != drugStores.getWERKS()) {

                werksList.add(drugStores.getWERKS());
            } else {

                nullDrugStoresList.add(drugStores);
            }
        }
        // 进行数据相关的去重及其重复数据记录
        List<String> duplicateWerksList = CommonUtils.frequencyOfListElements(werksList);
        werksList = CommonUtils.removeDuplicateValue(werksList);
        imDataList = CommonUtils.removeDuplicateValue(imDataList);

        // 删除主键为空的门店
        if(nullDrugStoresList.size() > 0) {

            for (DrugStores drugStores : nullDrugStoresList) {

                imDataList.remove(drugStores);
            }
        }

        // 记录时间
        long startTime = new Date().getTime();
        // 取出存到数据库的门店信息
        List<DrugStores> drugStoresList = new ArrayList<DrugStores>();
        if (werksList.size() > 450) {

            // 集合分割，参数过大
            int size = werksList.size() / 450;
            List<List<String>> splitlists = CommonUtils.averageAssign(werksList, size);

            for (List<String> list : splitlists) {

                List<DrugStores> splitList = new ArrayList<DrugStores>();
                splitList = commonMapper.findDrugStoresByWerks(list);
                for (DrugStores drugStores : splitList) {

                    drugStoresList.add(drugStores);
                }
            }
        }  else {

            // 成功存到数据库的门店数据数目
            drugStoresList = commonMapper.findDrugStoresByWerks(werksList);
        }
        log.info("结束一次下发门店查询信息时间： " + ((new Date().getTime() - startTime)));

        // 用于插入门店数据记录
        List<DrugStores> insertBatchDrugStoresList = new ArrayList<DrugStores>();
        // 用于修改门店数据记录
        List<DrugStores> updateBatchDrugStoresList = new ArrayList<DrugStores>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (DrugStores drugStores : imDataList) {

            drugStores.setDRUGSTOREID(UUID.randomUUID().toString());
            // 传递过来的门店数据中已经存入到数据库
            if (drugStoresList.contains(drugStores)) {

                String updateTime = sdf.format(new Date());
                drugStores.setUPDATETIME(updateTime);
                updateBatchDrugStoresList.add(drugStores);
            } else {

                // 传递过来的数据未写入到数据库
                String insertTime = sdf.format(new Date());
                drugStores.setINSERTTIME(insertTime);
                insertBatchDrugStoresList.add(drugStores);
            }
        }

        // 成功存入数据库的门店数据数目
        Integer insertRows = 0;
        // 成功更新数据库的门店数据数目
        Integer updateRows = 0;
        // 总操作数据库的门店数据数目
        Integer totalRows = 0;
        int numRows = updateBatchDrugStoresList.size() + insertBatchDrugStoresList.size();
        // 如果有需要新插入到数据库的门店数据
        if (insertBatchDrugStoresList.size() > 0) {

            // 请求参数过多
            if (insertBatchDrugStoresList.size() > 40) {

                int size = insertBatchDrugStoresList.size() / 20;
                List<List<DrugStores>> splitlists = CommonUtils.averageAssign(insertBatchDrugStoresList, size);

                for (List<DrugStores> list : splitlists) {

                    // 成功存到数据库的客户数据数目
                    insertRows = commonMapper.addBatchStores(list);
                }
            } else {

                // 成功存到数据库的门店数据数目
                insertRows = commonMapper.addBatchStores(insertBatchDrugStoresList);
            }
        }

        // 如果有需要更新数据库的门店数据
        if (updateBatchDrugStoresList.size() > 0) {

            if(updateBatchDrugStoresList.size() > 40) {

                int size = updateBatchDrugStoresList.size() / 20;
                List<List<DrugStores>> splitlists = CommonUtils.averageAssign(updateBatchDrugStoresList, size);

                for (List<DrugStores> list : splitlists) {

                    // 成功更新已插入道数据库的门店数据数目
                    updateRows = commonMapper.updateBatchStores(list);
                }
            } else {

                // 成功更新已插入道数据库的门店数据数目
                updateRows = commonMapper.updateBatchStores(updateBatchDrugStoresList);
            }
        }
        // 总行数
        totalRows = insertRows + updateRows;

        if ((totalRows != 0 || insertRows !=0 || updateRows != 0) && numRows == imDataList.size()) {

            System.out.println("已全部完成,总操作门店数(更新+添加):" + numRows);
        } else {

            System.out.println("未全部完成(有遗漏),总操作门店数(更新+添加):" + numRows);
        }

        // 存储返回数据 - 总返回值
        ExDataBean<ExReturnFatherDataBean<ExReturnDrugStoresDataBean>> exDataBean = new ExDataBean<ExReturnFatherDataBean<ExReturnDrugStoresDataBean>>();
        // 二级嵌套 - 返回值
        ExReturnFatherDataBean<ExReturnDrugStoresDataBean> exReturnFatherDataBean = new ExReturnFatherDataBean<ExReturnDrugStoresDataBean>();
        // 用于存放返回成功的商品信息
        List<ExReturnDrugStoresDataBean> exReturnSuccessDrugStoresDataBeanList = new ArrayList<ExReturnDrugStoresDataBean>();
        // 用于存放返回失败的商品信息
        List<ExReturnDrugStoresDataBean> exReturnFailedDrugStoresDataBeanList = new ArrayList<ExReturnDrugStoresDataBean>();

        // 遍历从SAP传递过来的门店信息
        for (DrugStores drugStores : imDataList) {

            ExReturnDrugStoresDataBean exReturnDrugStoresDataBean = new ExReturnDrugStoresDataBean();
            exReturnDrugStoresDataBean.setWERKS(drugStores.getWERKS());
            // 如果该条门店信息已经存到数据库
            if (insertBatchDrugStoresList.contains(drugStores) || updateBatchDrugStoresList.contains(drugStores)) {

                // 定义单条返回的门店信息
                exReturnDrugStoresDataBean.setMSGTY("S");
                exReturnDrugStoresDataBean.setMSGTX("接收成功");
                exReturnSuccessDrugStoresDataBeanList.add(exReturnDrugStoresDataBean);
            } else {

                // 该条门店数据插入失败
                exReturnDrugStoresDataBean.setMSGTY("E");
                exReturnDrugStoresDataBean.setMSGTX("接收失败，请检查该门店数据信息");
                exReturnFailedDrugStoresDataBeanList.add(exReturnDrugStoresDataBean);
            }
        }

        // 组装异常信息
        for (Map.Entry<DrugStores, String> entry : stringMap.entrySet()) {

            ExReturnDrugStoresDataBean exReturnDrugStoresDataBean = new ExReturnDrugStoresDataBean();
            exReturnDrugStoresDataBean.setWERKS(entry.getKey().getWERKS());
            // 该条门店数据插入失败
            exReturnDrugStoresDataBean.setMSGTY("E");
            exReturnDrugStoresDataBean.setMSGTX(entry.getValue());
            exReturnFailedDrugStoresDataBeanList.add(exReturnDrugStoresDataBean);
        }

        // 组装传递重复数据的异常信息
        for (String  werks : duplicateWerksList) {

            ExReturnDrugStoresDataBean exReturnDrugStoresDataBean = new ExReturnDrugStoresDataBean();
            exReturnDrugStoresDataBean.setWERKS(werks);
            exReturnDrugStoresDataBean.setMSGTY("E");
            exReturnDrugStoresDataBean.setMSGTX("该条门店数据重复");
            exReturnFailedDrugStoresDataBeanList.add(exReturnDrugStoresDataBean);
        }

        if(exReturnFailedDrugStoresDataBeanList.size() > 0) {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("E");
            exReturnFatherDataBean.setMSGTX("接收失败");

            // 将成功的也返回
            for (ExReturnDrugStoresDataBean exReturnDrugStoresDataBean : exReturnSuccessDrugStoresDataBeanList) {

                exReturnFailedDrugStoresDataBeanList.add(exReturnDrugStoresDataBean);
            }
            exReturnFatherDataBean.setDATA(exReturnFailedDrugStoresDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        } else {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("S");
            exReturnFatherDataBean.setMSGTX("接收成功");
            exReturnFatherDataBean.setDATA(exReturnSuccessDrugStoresDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        }
        log.info("返回门店主数据信息:" + JSON.toJSONString(exDataBean));
        log.info("结束一次下发门店信息时间： " + ((new Date().getTime() - startTime)));
        return JSON.toJSONString(exDataBean);
    }

    /**
     *  客户主数据下发接口
     * @param imDataArray
     * @return
     */
    private String ClienteleDataAccept(JSONArray imDataArray) {

        List<Clientele> imDataList = JSON.parseObject(imDataArray.toJSONString(), new TypeReference<List<Clientele>>(){});

        // 判断传递过来的信息是否存在异常
        Map<Clientele, String> stringMap = CommonUtils.recordErrorInfo(imDataList);
        for (Map.Entry<Clientele, String> entry : stringMap.entrySet()) {

            imDataList.remove(entry.getKey());
        }

        // 进行调整修改
        // 用于存放客户编码
        List<String> kunnrList = new ArrayList<String>();
        List<Clientele> nullClienteleList = new ArrayList<>();
        for (Clientele clientele:imDataList) {

            if (!"".equals(clientele.getKUNNR()) && null != clientele.getKUNNR()) {

                kunnrList.add(clientele.getKUNNR());
            } else {

                nullClienteleList.add(clientele);
            }
        }
        // 进行数据相关的去重及其重复数据记录
        List<String> duplicateKunnrList = CommonUtils.frequencyOfListElements(kunnrList);
        kunnrList = CommonUtils.removeDuplicateValue(kunnrList);
        imDataList = CommonUtils.removeDuplicateValue(imDataList);

        // 删除主键为空的客户
        if(nullClienteleList.size() > 0) {

            for (Clientele clientele : nullClienteleList) {

                imDataList.remove(clientele);
            }
        }

        // 记录时间
        long startTime = new Date().getTime();
        // 取出存到数据库的客户信息
        List<Clientele> clienteleList = new ArrayList<Clientele>();
        if (kunnrList.size() > 450) {

            // 集合分割，参数过大
            int size = kunnrList.size() / 450;
            List<List<String>> splitlists = CommonUtils.averageAssign(kunnrList, size);

            for (List<String> list : splitlists) {

                List<Clientele> splitList = new ArrayList<Clientele>();
                splitList = commonMapper.findClienteleByKunnr(list);
                for (Clientele clientele : splitList) {

                    clienteleList.add(clientele);
                }
            }
        }  else {

            // 成功存到数据库的客户数据数目
            clienteleList = commonMapper.findClienteleByKunnr(kunnrList);
        }
        log.info("结束一次查询客户信息时间： " + ((new Date().getTime() - startTime)));

        // 用于插入客户数据记录
        List<Clientele> insertBatchClienteleList = new ArrayList<Clientele>();
        // 用于修改客户数据记录
        List<Clientele> updateBatchClienteleList = new ArrayList<Clientele>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Clientele clientele : imDataList) {

            clientele.setCLIENTID(UUID.randomUUID().toString());
            // 传递过来的客户数据中已经存入到数据库
            if (clienteleList.contains(clientele)) {

                String updateTime = sdf.format(new Date());
                clientele.setUPDATETIME(updateTime);
                updateBatchClienteleList.add(clientele);
            } else {

                // 传递过来的数据未写入到数据库
                String insertTime = sdf.format(new Date());
                clientele.setINSERTTIME(insertTime);
                insertBatchClienteleList.add(clientele);
            }
        }

        // 成功存入数据库的客户数据数目
        Integer insertRows = 0;
        // 成功更新数据库的客户数据数目
        Integer updateRows = 0;
        // 总操作数据库的客户数据数目
        Integer totalRows = 0;
        int numRows = updateBatchClienteleList.size() + insertBatchClienteleList.size();

        // 如果有需要新插入到数据库的客户数据
        if (insertBatchClienteleList.size() > 0) {

            // 请求参数过多
            if(insertBatchClienteleList.size() > 40) {

                int size = insertBatchClienteleList.size() / 20;
                List<List<Clientele>> splitlists = CommonUtils.averageAssign(insertBatchClienteleList, size);

                for (List<Clientele> list : splitlists) {

                    // 成功存到数据库的客户数据数目
                    insertRows = commonMapper.addBatchClientele(list);
                }
            } else {

                // 成功存到数据库的客户数据数目
                insertRows = commonMapper.addBatchClientele(insertBatchClienteleList);
            }
        }

        // 如果有需要更新数据库的客户数据
        if (updateBatchClienteleList.size() > 0) {

            // 请求参数过多
            if(updateBatchClienteleList.size() > 40) {

                int size = updateBatchClienteleList.size() / 20;
                List<List<Clientele>> splitlists = CommonUtils.averageAssign(updateBatchClienteleList, size);

                for (List<Clientele> list : splitlists) {

                    // 成功更新已插入道数据库的客户数据数目
                    updateRows = commonMapper.updateBatchClientele(list);
                }
            } else {

                // 成功更新已插入道数据库的客户数据数目
                updateRows = commonMapper.updateBatchClientele(updateBatchClienteleList);
            }
        }
        // 总行数
        totalRows = insertRows + updateRows;

        if ((totalRows != 0 || insertRows !=0 || updateRows != 0) && numRows == imDataList.size()) {

            System.out.println("已全部完成,总操作客户数(更新+添加):" + numRows);
        } else {

            System.out.println("未全部完成(有遗漏),总操作客户数(更新+添加):" + numRows);
        }

        // 存储返回数据 - 总返回值
        ExDataBean<ExReturnFatherDataBean<ExReturnClienteleDataBean>> exDataBean = new ExDataBean<ExReturnFatherDataBean<ExReturnClienteleDataBean>>();
        // 二级嵌套 - 返回值
        ExReturnFatherDataBean<ExReturnClienteleDataBean> exReturnFatherDataBean = new ExReturnFatherDataBean<ExReturnClienteleDataBean>();
        // 用于存放返回成功的客户信息
        List<ExReturnClienteleDataBean> exReturnSuccessClienteleDataBeanList = new ArrayList<ExReturnClienteleDataBean>();
        // 用于存放返回失败的客户信息
        List<ExReturnClienteleDataBean> exReturnFailedClienteleDataBeanList = new ArrayList<ExReturnClienteleDataBean>();

        // 遍历从SAP传递过来的客户信息
        for (Clientele clientele : imDataList) {

            ExReturnClienteleDataBean exReturnClienteleDataBean = new ExReturnClienteleDataBean();
            exReturnClienteleDataBean.setKUNNR(clientele.getKUNNR());
            // 如果该条客户信息已经存到数据库
            if (insertBatchClienteleList.contains(clientele) || updateBatchClienteleList.contains(clientele)) {

                // 定义单条返回的客户信息
                exReturnClienteleDataBean.setMSGTY("S");
                exReturnClienteleDataBean.setMSGTX("接收成功");
                exReturnSuccessClienteleDataBeanList.add(exReturnClienteleDataBean);
            } else {

                // 该条门店数据插入失败
                exReturnClienteleDataBean.setMSGTY("E");
                exReturnClienteleDataBean.setMSGTX("接收失败，请检查该客户数据信息...");
                exReturnFailedClienteleDataBeanList.add(exReturnClienteleDataBean);
            }
        }

        // 组装异常信息
        for (Map.Entry<Clientele, String> entry : stringMap.entrySet()) {

            ExReturnClienteleDataBean exReturnClienteleDataBean = new ExReturnClienteleDataBean();
            exReturnClienteleDataBean.setKUNNR(entry.getKey().getKUNNR());
            // 该条客户数据插入失败
            exReturnClienteleDataBean.setMSGTY("E");
            exReturnClienteleDataBean.setMSGTX(entry.getValue());
            exReturnFailedClienteleDataBeanList.add(exReturnClienteleDataBean);
        }

        // 组装传递重复数据的异常信息
        for (String  kunnr : duplicateKunnrList) {

            ExReturnClienteleDataBean exReturnClienteleDataBean = new ExReturnClienteleDataBean();
            exReturnClienteleDataBean.setKUNNR(kunnr);
            exReturnClienteleDataBean.setMSGTY("E");
            exReturnClienteleDataBean.setMSGTX("该条客户数据重复");
            exReturnFailedClienteleDataBeanList.add(exReturnClienteleDataBean);
        }

        if (exReturnFailedClienteleDataBeanList.size() > 0) {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("E");
            exReturnFatherDataBean.setMSGTX("接收失败");

            // 将成功的也返回
            for (ExReturnClienteleDataBean exReturnClienteleDataBean : exReturnSuccessClienteleDataBeanList) {

                exReturnFailedClienteleDataBeanList.add(exReturnClienteleDataBean);
            }
            exReturnFatherDataBean.setDATA(exReturnFailedClienteleDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        } else {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("S");
            exReturnFatherDataBean.setMSGTX("接收成功");
            exReturnFatherDataBean.setDATA(exReturnSuccessClienteleDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        }
        log.info("返回客户主数据信息:" + JSON.toJSONString(exDataBean));
        log.info("结束一次下发客户信息时间： " + ((new Date().getTime() - startTime)));
        return JSON.toJSONString(exDataBean);
    }

    /**
     *  供应商主数据下发接口
     * @param imDataArray
     * @return
     */
    private String SupplierDataAccept(JSONArray imDataArray) {

        List<Supplier> imDataList = JSON.parseObject(imDataArray.toJSONString(), new TypeReference<List<Supplier>>(){});

        // 判断传递过来的信息是否存在异常
        Map<Supplier, String> stringMap = CommonUtils.recordErrorInfo(imDataList);
        for (Map.Entry<Supplier, String> entry : stringMap.entrySet()) {

            imDataList.remove(entry.getKey());
        }

        // 进行调整修改
        // 用于存放供应商编码
        List<String> lifnrList = new ArrayList<String>();
        List<Supplier> nullSupplierList = new ArrayList<>();
        for (Supplier supplier:imDataList) {

            if (!"".equals(supplier.getLIFNR()) && null != supplier.getLIFNR()) {

                lifnrList.add(supplier.getLIFNR());
            } else {

                nullSupplierList.add(supplier);
            }
        }
        // 进行数据相关的去重及其重复数据记录
        List<String> duplicateLifnrList = CommonUtils.frequencyOfListElements(lifnrList);
        lifnrList = CommonUtils.removeDuplicateValue(lifnrList);
        imDataList = CommonUtils.removeDuplicateValue(imDataList);

        // 删除主键为空的供应商
        if(nullSupplierList.size() > 0) {

            for (Supplier supplier : nullSupplierList) {

                imDataList.remove(supplier);
            }
        }

        // 记录时间
        long startTime = new Date().getTime();
        // 取出存到数据库的供应商信息
        List<Supplier> supplierList = new ArrayList<Supplier>();
        if (lifnrList.size() > 450) {

            // 集合分割，参数过大
            int size = lifnrList.size() / 450;
            List<List<String>> splitlists = CommonUtils.averageAssign(lifnrList, size);

            for (List<String> list : splitlists) {

                List<Supplier> splitList = new ArrayList<Supplier>();
                splitList = commonMapper.findSupplierByLifnr(list);
                for (Supplier supplier : splitList) {

                    supplierList.add(supplier);
                }
            }
        }  else {

            // 成功存到数据库的供应商数据数目
            supplierList = commonMapper.findSupplierByLifnr(lifnrList);
        }
        log.info("结束一次查询供应商信息时间： " + ((new Date().getTime() - startTime)));

        // 用于插入供应商数据记录
        List<Supplier> insertBatchSupplierList = new ArrayList<Supplier>();
        // 用于修改供应商数据记录
        List<Supplier> updateBatchSupplierList = new ArrayList<Supplier>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Supplier supplier : imDataList) {

            supplier.setSUPPLIERID(UUID.randomUUID().toString());
            // 传递过来的供应商数据中已经存入到数据库
            if (supplierList.contains(supplier)) {

                String updateTime = sdf.format(new Date());
                supplier.setUPDATETIME(updateTime);
                updateBatchSupplierList.add(supplier);
            } else {

                // 传递过来的数据未写入到数据库
                String insertTime = sdf.format(new Date());
                supplier.setINSERTTIME(insertTime);
                insertBatchSupplierList.add(supplier);
            }
        }

        // 成功存入数据库的供应商数据数目
        Integer insertRows = 0;
        // 成功更新数据库的供应商数据数目
        Integer updateRows = 0;
        // 总操作数据库的供应商数据数目
        Integer totalRows = 0;
        int numRows = updateBatchSupplierList.size() + insertBatchSupplierList.size();

        // 如果有需要新插入到数据库的客户数据
        if (insertBatchSupplierList.size() > 0) {

            // 请求参数过多
            if(insertBatchSupplierList.size() > 40) {

                int size = insertBatchSupplierList.size() / 20;
                List<List<Supplier>> splitlists = CommonUtils.averageAssign(insertBatchSupplierList, size);

                for (List<Supplier> list : splitlists) {

                    // 成功存到数据库的供应商数据数目
                    insertRows = commonMapper.addBatchSupplier(list);
                }
            } else {

                // 成功存到数据库的供应商数据数目
                insertRows = commonMapper.addBatchSupplier(insertBatchSupplierList);
            }
        }

        // 如果有需要更新数据库的供应商数据
        if (updateBatchSupplierList.size() > 0) {

            // 请求参数过多
            if(updateBatchSupplierList.size() > 40) {

                int size = updateBatchSupplierList.size() / 20;
                List<List<Supplier>> splitlists = CommonUtils.averageAssign(updateBatchSupplierList, size);

                for (List<Supplier> list : splitlists) {

                    // 成功更新已插入道数据库的供应商数据数目
                    updateRows = commonMapper.updateBatchSupplier(list);
                }
            } else {

                // 成功更新已插入道数据库的供应商数据数目
                updateRows = commonMapper.updateBatchSupplier(updateBatchSupplierList);
            }
        }
        // 总行数
        totalRows = insertRows + updateRows;

        if ((totalRows != 0 || insertRows !=0 || updateRows != 0) && numRows == imDataList.size()) {

            System.out.println("已全部完成,总操作供应商数(更新+添加):" + numRows);
        } else {

            System.out.println("未全部完成(有遗漏),总操作供应商数(更新+添加):" + numRows);
        }

        // 存储返回数据 - 总返回值
        ExDataBean<ExReturnFatherDataBean<ExReturnSupplierDataBean>> exDataBean = new ExDataBean<ExReturnFatherDataBean<ExReturnSupplierDataBean>>();
        // 二级嵌套 - 返回值
        ExReturnFatherDataBean<ExReturnSupplierDataBean> exReturnFatherDataBean = new ExReturnFatherDataBean<ExReturnSupplierDataBean>();
        // 用于存放返回成功的供应商信息
        List<ExReturnSupplierDataBean> exReturnSuccessCommodityDataBeanList = new ArrayList<ExReturnSupplierDataBean>();
        // 用于存放返回失败的供应商信息
        List<ExReturnSupplierDataBean> exReturnFailedCommodityDataBeanList = new ArrayList<ExReturnSupplierDataBean>();

        // 遍历从SAP传递过来的供应商信息
        for (Supplier supplier : imDataList) {

            ExReturnSupplierDataBean exReturnSupplierDataBean = new ExReturnSupplierDataBean();
            exReturnSupplierDataBean.setLifnr(supplier.getLIFNR());
            // 如果该条供应商信息已经存到数据库
            if (insertBatchSupplierList.contains(supplier) || updateBatchSupplierList.contains(supplier)) {

                // 定义单条返回的供应商信息
                exReturnSupplierDataBean.setMSGTY("S");
                exReturnSupplierDataBean.setMSGTX("接收成功");
                exReturnSuccessCommodityDataBeanList.add(exReturnSupplierDataBean);
            } else {

                // 该条供应商数据插入失败
                exReturnSupplierDataBean.setMSGTY("E");
                exReturnSupplierDataBean.setMSGTX("接收失败，请检查该供应商数据信息...");
                exReturnFailedCommodityDataBeanList.add(exReturnSupplierDataBean);
            }
        }

        // 组装异常信息
        for (Map.Entry<Supplier, String> entry : stringMap.entrySet()) {

            ExReturnSupplierDataBean exReturnSupplierDataBean = new ExReturnSupplierDataBean();
            exReturnSupplierDataBean.setLifnr(entry.getKey().getLIFNR());
            // 该条供应商数据插入失败
            exReturnSupplierDataBean.setMSGTY("E");
            exReturnSupplierDataBean.setMSGTX(entry.getValue());
            exReturnFailedCommodityDataBeanList.add(exReturnSupplierDataBean);
        }

        // 组装传递重复数据的异常信息
        for (String  lifnr : duplicateLifnrList) {

            ExReturnSupplierDataBean exReturnSupplierDataBean = new ExReturnSupplierDataBean();
            exReturnSupplierDataBean.setLifnr(lifnr);
            exReturnSupplierDataBean.setMSGTY("E");
            exReturnSupplierDataBean.setMSGTX("该条供应商数据重复");
            exReturnFailedCommodityDataBeanList.add(exReturnSupplierDataBean);
        }

        if (exReturnFailedCommodityDataBeanList.size() > 0) {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("E");
            exReturnFatherDataBean.setMSGTX("接收失败");

            // 将成功的也返回
            for (ExReturnSupplierDataBean exReturnClienteleDataBean : exReturnSuccessCommodityDataBeanList) {

                exReturnFailedCommodityDataBeanList.add(exReturnClienteleDataBean);
            }
            exReturnFatherDataBean.setDATA(exReturnFailedCommodityDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        } else {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("S");
            exReturnFatherDataBean.setMSGTX("接收成功");
            exReturnFatherDataBean.setDATA(exReturnSuccessCommodityDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        }
        log.info("返回供应商主数据信息:" + JSON.toJSONString(exDataBean));
        log.info("结束一次下发供应商信息时间： " + ((new Date().getTime() - startTime)));
        return JSON.toJSONString(exDataBean);
    }

    /**
     *  成本中心主数据下发接口
     * @param imDataArray
     * @return
     */
    private String CostCenterDataAccept(JSONArray imDataArray) {

        List<CostCenter> imDataList = JSON.parseObject(imDataArray.toJSONString(), new TypeReference<List<CostCenter>>(){});

        // 判断传递过来的信息是否存在异常
        Map<CostCenter, String> stringMap = CommonUtils.recordErrorInfo(imDataList);
        for (Map.Entry<CostCenter, String> entry : stringMap.entrySet()) {

            imDataList.remove(entry.getKey());
        }

        // 进行调整修改
        // 用于存放成本中心编码
        List<String> kostlList = new ArrayList<String>();
        List<CostCenter> nullCostCenterList = new ArrayList<>();
        for (CostCenter costCenter : imDataList) {

            if (!"".equals(costCenter.getKOSTL()) && null != costCenter.getKOSTL()) {

                kostlList.add(costCenter.getKOSTL());
            } else {

                nullCostCenterList.add(costCenter);
            }
        }
        // 进行数据相关的去重及其重复数据记录
        List<String> duplicateKostList = CommonUtils.frequencyOfListElements(kostlList);
        kostlList = CommonUtils.removeDuplicateValue(kostlList);
        imDataList = CommonUtils.removeDuplicateValue(imDataList);

        // 删除主键为空的成本中心
        if(nullCostCenterList.size() > 0) {

            for (CostCenter costCenter : nullCostCenterList) {

                imDataList.remove(costCenter);
            }
        }

        // 记录时间
        long startTime = new Date().getTime();
        // 取出存到数据库的成本中心信息
        List<CostCenter> costCenterList = new ArrayList<CostCenter>();
        if (kostlList.size() > 450) {

            // 集合分割，参数过大
            int size = kostlList.size() / 450;
            List<List<String>> splitlists = CommonUtils.averageAssign(kostlList, size);

            for (List<String> list : splitlists) {

                List<CostCenter> splitList = new ArrayList<CostCenter>();
                splitList = commonMapper.findCostCenterByKostl(list);
                for (CostCenter costCenter : splitList) {

                    costCenterList.add(costCenter);
                }
            }
        }  else {

            // 成功存到数据库的成本中心数据数目
            costCenterList = commonMapper.findCostCenterByKostl(kostlList);
        }
        log.info("结束一次查询成本中心信息时间： " + ((new Date().getTime() - startTime) / 1000));

        // 用于插入成本中心数据记录
        List<CostCenter> insertBatchCostCenterList = new ArrayList<CostCenter>();
        // 用于修改成本中心数据记录
        List<CostCenter> updateBatchCostCenterList = new ArrayList<CostCenter>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (CostCenter costCenter : imDataList) {

            costCenter.setCOSTCENTERID(UUID.randomUUID().toString());
            // 传递过来的成本中心数据中已经存入到数据库
            if (costCenterList.contains(costCenter)) {

                String updateTime = sdf.format(new Date());
                costCenter.setUPDATETIME(updateTime);
                updateBatchCostCenterList.add(costCenter);
            } else {

                // 传递过来的数据未写入到数据库
                String insertTime = sdf.format(new Date());
                costCenter.setINSERTTIME(insertTime);
                insertBatchCostCenterList.add(costCenter);
            }
        }

        // 成功存入数据库的成本中心数据数目
        Integer insertRows = 0;
        // 成功更新数据库的成本中心数据数目
        Integer updateRows = 0;
        // 总操作数据库的成本中心数据数目
        Integer totalRows = 0;
        int numRows = updateBatchCostCenterList.size() + insertBatchCostCenterList.size();

        // 如果有需要新插入到数据库的成本中心数据
        if (insertBatchCostCenterList.size() > 0) {

            // 请求参数过大
            if(insertBatchCostCenterList.size() > 40) {

                int size = insertBatchCostCenterList.size() / 20;
                List<List<CostCenter>> splitlists = CommonUtils.averageAssign(insertBatchCostCenterList, size);

                for (List<CostCenter> list : splitlists) {

                    // 成功存到数据库的成本中心数据数目
                    insertRows = commonMapper.addBatchCostCenter(list);
                }
            } else {

                // 成功存到数据库的成本中心数据数目
                insertRows = commonMapper.addBatchCostCenter(insertBatchCostCenterList);
            }
        }

        // 如果有需要更新数据库的成本中心数据
        if (updateBatchCostCenterList.size() > 0) {

            // 请求参数过大
            if(updateBatchCostCenterList.size() > 40) {

                int size = updateBatchCostCenterList.size() / 20;
                List<List<CostCenter>> splitlists = CommonUtils.averageAssign(updateBatchCostCenterList, size);

                for (List<CostCenter> list : splitlists) {

                    // 成功更新已插入道数据库的成本中心数据数目
                    updateRows = commonMapper.updateBatchCostCenter(list);
                }
            } else {

                updateRows = commonMapper.updateBatchCostCenter(updateBatchCostCenterList);
            }
        }
        // 总行数
        totalRows = insertRows + updateRows;

        if ((totalRows != 0 || insertRows !=0 || updateRows != 0) && numRows == imDataList.size()) {

            System.out.println("已全部完成,总操作供应商数(更新+添加):" + numRows);
        } else {

            System.out.println("未全部完成(有遗漏),总操作供应商数(更新+添加):" + numRows + "存在数据重复,重复供应商数目：" + duplicateKostList.size());
        }

        // 存储返回数据 - 总返回值
        ExDataBean<ExReturnFatherDataBean<ExReturnCostCenterDataBean>> exDataBean = new ExDataBean<ExReturnFatherDataBean<ExReturnCostCenterDataBean>>();
        // 二级嵌套 - 返回值
        ExReturnFatherDataBean<ExReturnCostCenterDataBean> exReturnFatherDataBean = new ExReturnFatherDataBean<ExReturnCostCenterDataBean>();
        // 用于存放返回成功的成本中心信息
        List<ExReturnCostCenterDataBean> exReturnSuccessCostCenterDataBeanList = new ArrayList<ExReturnCostCenterDataBean>();
        // 用于存放返回失败的成本中心信息
        List<ExReturnCostCenterDataBean> exReturnFailedCostCenterDataBeanList = new ArrayList<ExReturnCostCenterDataBean>();

        // 遍历从SAP传递过来的成本中心信息
        for (CostCenter costCenter : imDataList) {

            ExReturnCostCenterDataBean exReturnCostCenterDataBeans = new ExReturnCostCenterDataBean();
            exReturnCostCenterDataBeans.setKOSTL(costCenter.getKOSTL());
            exReturnCostCenterDataBeans.setDATBI(costCenter.getDATBI());
            exReturnCostCenterDataBeans.setKOKRS(costCenter.getKOKRS());
            // 如果该条成本中心信息已经存到数据库
            if (insertBatchCostCenterList.contains(costCenter) || updateBatchCostCenterList.contains(costCenter)) {

                // 定义单条返回的成本中心信息
                exReturnCostCenterDataBeans.setMSGTY("S");
                exReturnCostCenterDataBeans.setMSGTX("接收成功");
                exReturnSuccessCostCenterDataBeanList.add(exReturnCostCenterDataBeans);
            } else {

                // 该条供应商数据插入失败
                exReturnCostCenterDataBeans.setMSGTY("E");
                exReturnCostCenterDataBeans.setMSGTX("接收失败，请检查该成本中心数据信息");
                exReturnFailedCostCenterDataBeanList.add(exReturnCostCenterDataBeans);
            }
        }

        // 组装异常信息
        for (Map.Entry<CostCenter, String> entry : stringMap.entrySet()) {

            ExReturnCostCenterDataBean exReturnCostCenterDataBeans = new ExReturnCostCenterDataBean();
            exReturnCostCenterDataBeans.setKOSTL(entry.getKey().getKOSTL());
            // 该条成本中心数据插入失败
            exReturnCostCenterDataBeans.setMSGTY("E");
            exReturnCostCenterDataBeans.setMSGTX(entry.getValue());
            exReturnFailedCostCenterDataBeanList.add(exReturnCostCenterDataBeans);
        }

        // 组装传递重复数据的异常信息
        for (String  kostl : duplicateKostList) {

            ExReturnCostCenterDataBean exReturnCostCenterDataBeans = new ExReturnCostCenterDataBean();
            exReturnCostCenterDataBeans.setKOSTL(kostl);
            exReturnCostCenterDataBeans.setMSGTY("E");
            exReturnCostCenterDataBeans.setMSGTX("该条成本中心数据重复");
            exReturnFailedCostCenterDataBeanList.add(exReturnCostCenterDataBeans);
        }

        if (exReturnFailedCostCenterDataBeanList.size() > 0) {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("E");
            exReturnFatherDataBean.setMSGTX("接收失败");

            // 将成功的也返回
            for (ExReturnCostCenterDataBean exReturnCostCenterDataBean : exReturnSuccessCostCenterDataBeanList) {

                exReturnFailedCostCenterDataBeanList.add(exReturnCostCenterDataBean);
            }
            exReturnFatherDataBean.setDATA(exReturnFailedCostCenterDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        } else {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("S");
            exReturnFatherDataBean.setMSGTX("接收成功");
            exReturnFatherDataBean.setDATA(exReturnSuccessCostCenterDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        }
        log.info("返回成本中心主数据信息:" + JSON.toJSONString(exDataBean));
        log.info("结束一次下发成本中心信息时间： " + ((new Date().getTime() - startTime) / 1000));
        return JSON.toJSONString(exDataBean);
    }

    /**
     *  组织架构主数据下发接口
     * @param imDataArray
     * @return
     */
    private String OrganizationDataAccept(JSONArray imDataArray) {

        List<Organization> imDataList = JSON.parseObject(imDataArray.toJSONString(), new TypeReference<List<Organization>>(){});

        // 判断传递过来的信息是否存在异常
        Map<Organization, String> stringMap = CommonUtils.recordErrorInfo(imDataList);
        for (Map.Entry<Organization, String> entry : stringMap.entrySet()) {

            imDataList.remove(entry.getKey());
        }

        // 进行调整修改
        // 用于存放组织架构编码
        List<String> kunnrList = new ArrayList<String>();
        List<Organization> nullOrganizationList = new ArrayList<>();
        for (Organization organization : imDataList) {

            if (!"".equals(organization.getKUNNR()) && null != organization.getKUNNR()) {

                kunnrList.add(organization.getKUNNR());
            } else {

                nullOrganizationList.add(organization);
            }
        }
        // 进行数据相关的去重及其重复数据记录
        List<String> duplicateKunnrList = CommonUtils.frequencyOfListElements(kunnrList);
        kunnrList = CommonUtils.removeDuplicateValue(kunnrList);
        imDataList = CommonUtils.removeDuplicateValue(imDataList);

        // 删除主键为空的组织架构
        if(nullOrganizationList.size() > 0) {

            for (Organization organization : nullOrganizationList) {

                imDataList.remove(organization);
            }
        }

        // 记录时间
        long startTime = new Date().getTime();
        // 取出存到数据库的组织架构信息
        List<Organization> organizationList = new ArrayList<Organization>();
        if (kunnrList.size() > 450) {

            // 集合分割，参数过大
            int size = kunnrList.size() / 450;
            List<List<String>> splitlists = CommonUtils.averageAssign(kunnrList, size);

            for (List<String> list : splitlists) {

                List<Organization> splitList = new ArrayList<Organization>();
                splitList = commonMapper.findOrganizationByKunnr(list);
                for (Organization organization : splitList) {

                    organizationList.add(organization);
                }
            }
        }  else {

            // 成功存到数据库的组织架构数据数目
            organizationList = commonMapper.findOrganizationByKunnr(kunnrList);
        }
        log.info("结束一次查询组织机构信息时间： " + ((new Date().getTime() - startTime)));

        // 用于插入组织机构数据记录
        List<Organization> insertBatchOrganizationList = new ArrayList<Organization>();
        // 用于修改组织机构数据记录
        List<Organization> updateBatchOrganizationList = new ArrayList<Organization>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Organization organization : imDataList) {

            organization.setORGANIZATIONID(UUID.randomUUID().toString());
            // 传递过来的组织机构数据已经存入到数据库
            if (organizationList.contains(organization)) {

                String updateTime = sdf.format(new Date());
                organization.setUPDATETIME(updateTime);
                updateBatchOrganizationList.add(organization);
            } else {

                // 传递过来的数据未写入到数据库
                String insertTime = sdf.format(new Date());
                organization.setINSERTTIME(insertTime);
                insertBatchOrganizationList.add(organization);
            }
        }

        // 成功存入数据库的组织机构数据数目
        Integer insertRows = 0;
        // 成功更新数据库的组织机构数据数目
        Integer updateRows = 0;
        // 总操作数据库的组织机构数据数目
        Integer totalRows = 0;
        int numRows = updateBatchOrganizationList.size() + insertBatchOrganizationList.size();

        // 如果有需要新插入到数据库的成本中心数据
        if (insertBatchOrganizationList.size() > 0) {

            // 请求参数过大
            if(insertBatchOrganizationList.size() > 40) {

                int size = insertBatchOrganizationList.size() / 20;
                List<List<Organization>> splitlists = CommonUtils.averageAssign(insertBatchOrganizationList, size);

                for (List<Organization> list : splitlists) {

                    // 成功存到数据库的成本中心数据数目
                    insertRows = commonMapper.addBatchOrganization(list);
                }
            } else {

                // 成功存到数据库的成本中心数据数目
                insertRows = commonMapper.addBatchOrganization(insertBatchOrganizationList);
            }
        }

        // 如果有需要更新数据库的组织机构数据
        if (updateBatchOrganizationList.size() > 0) {

            // 请求参数过大
            if(updateBatchOrganizationList.size() > 40) {

                int size = updateBatchOrganizationList.size() / 20;
                List<List<Organization>> splitlists = CommonUtils.averageAssign(updateBatchOrganizationList, size);

                for (List<Organization> list : splitlists) {

                    // 成功更新已插入道数据库的组织机构数目
                    updateRows = commonMapper.updateBatchOrganization(list);
                }
            } else {

                updateRows = commonMapper.updateBatchOrganization(updateBatchOrganizationList);
            }
        }
        // 总行数
        totalRows = insertRows + updateRows;

        if ((totalRows != 0 || insertRows !=0 || updateRows != 0) && numRows == imDataList.size()) {

            System.out.println("已全部完成,总操作供应商数(更新+添加):" + numRows);
        } else {

            System.out.println("未全部完成(有遗漏),总操作供应商数(更新+添加):" + numRows + "存在数据重复,重复供应商数目：" + duplicateKunnrList.size());
        }

        // 存储返回数据 - 总返回值
        ExDataBean<ExReturnFatherDataBean<ExReturnOrganiztionDataBean>> exDataBean = new ExDataBean<ExReturnFatherDataBean<ExReturnOrganiztionDataBean>>();
        // 二级嵌套 - 返回值
        ExReturnFatherDataBean<ExReturnOrganiztionDataBean> exReturnFatherDataBean = new ExReturnFatherDataBean<ExReturnOrganiztionDataBean>();
        // 用于存放返回成功的组织机构信息
        List<ExReturnOrganiztionDataBean> exReturnSuccessOrganiztionDataBeanList = new ArrayList<ExReturnOrganiztionDataBean>();
        // 用于存放返回失败的组织机构信息
        List<ExReturnOrganiztionDataBean> exReturnFailedOrganiztionDataBeanList = new ArrayList<ExReturnOrganiztionDataBean>();

        // 遍历从SAP传递过来的组织机构信息
        for (Organization organization : imDataList) {

            ExReturnOrganiztionDataBean exReturnOrganiztionDataBean = new ExReturnOrganiztionDataBean();
            exReturnOrganiztionDataBean.setKUNNR(organization.getKUNNR());
            // 如果该条组织机构信息已经存到数据库
            if (insertBatchOrganizationList.contains(organization) || updateBatchOrganizationList.contains(organization)) {

                // 定义单条返回的组织机构信息
                exReturnOrganiztionDataBean.setMSGTY("S");
                exReturnOrganiztionDataBean.setMSGTX("接收成功");
                exReturnSuccessOrganiztionDataBeanList.add(exReturnOrganiztionDataBean);
            } else {

                // 该条组织机构数据插入失败
                exReturnOrganiztionDataBean.setMSGTY("E");
                exReturnOrganiztionDataBean.setMSGTX("接收失败，请检查该成本中心数据信息");
                exReturnFailedOrganiztionDataBeanList.add(exReturnOrganiztionDataBean);
            }
        }

        // 组装异常信息
        for (Map.Entry<Organization, String> entry : stringMap.entrySet()) {

            ExReturnOrganiztionDataBean exReturnOrganiztionDataBean = new ExReturnOrganiztionDataBean();
            exReturnOrganiztionDataBean.setKUNNR(entry.getKey().getKUNNR());
            // 该条组织机构数据插入失败
            exReturnOrganiztionDataBean.setMSGTY("E");
            exReturnOrganiztionDataBean.setMSGTX(entry.getValue());
            exReturnFailedOrganiztionDataBeanList.add(exReturnOrganiztionDataBean);
        }

        // 组装传递重复数据的异常信息
        for (String  kunnr : duplicateKunnrList) {

            ExReturnOrganiztionDataBean exReturnOrganiztionDataBean = new ExReturnOrganiztionDataBean();
            exReturnOrganiztionDataBean.setKUNNR(kunnr);
            exReturnOrganiztionDataBean.setMSGTY("E");
            exReturnOrganiztionDataBean.setMSGTX("该条组织机构数据重复");
            exReturnFailedOrganiztionDataBeanList.add(exReturnOrganiztionDataBean);
        }

        if (exReturnFailedOrganiztionDataBeanList.size() > 0) {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("E");
            exReturnFatherDataBean.setMSGTX("接收失败");

            // 将成功的也返回
            for (ExReturnOrganiztionDataBean exReturnOrganiztionDataBean : exReturnSuccessOrganiztionDataBeanList) {

                exReturnFailedOrganiztionDataBeanList.add(exReturnOrganiztionDataBean);
            }
            exReturnFatherDataBean.setDATA(exReturnFailedOrganiztionDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        } else {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("S");
            exReturnFatherDataBean.setMSGTX("接收成功");
            exReturnFatherDataBean.setDATA(exReturnSuccessOrganiztionDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        }
        log.info("返回组织机构主数据信息:" + JSON.toJSONString(exDataBean));
        log.info("结束一次下发组织机构信息时间： " + ((new Date().getTime() - startTime)));
        return JSON.toJSONString(exDataBean);
    }

    /**
     *  利润中心主数据下发接口
     * @param imDataArray
     * @return
     */
    private String ProfiCenterDataAccept(JSONArray imDataArray) {

        List<ProfitCenter> imDataList = JSON.parseObject(imDataArray.toJSONString(), new TypeReference<List<ProfitCenter>>(){});

        // 判断传递过来的信息是否存在异常
        Map<ProfitCenter, String> stringMap = CommonUtils.recordErrorInfo(imDataList);
        for (Map.Entry<ProfitCenter, String> entry : stringMap.entrySet()) {

            imDataList.remove(entry.getKey());
        }

        // 进行调整修改
        // 用于存放利润中心编码
        List<String> prctrList = new ArrayList<String>();
        List<ProfitCenter> nullProfitCenterList = new ArrayList<>();
        for (ProfitCenter profitCenter : imDataList) {

            if (!"".equals(profitCenter.getPRCTR()) && null != profitCenter.getPRCTR()) {

                prctrList.add(profitCenter.getPRCTR());
            } else {

                nullProfitCenterList.add(profitCenter);
            }
        }
        // 进行数据相关的去重及其重复数据记录
        List<String> duplicatePrctrList = CommonUtils.frequencyOfListElements(prctrList);
        prctrList = CommonUtils.removeDuplicateValue(prctrList);
        imDataList = CommonUtils.removeDuplicateValue(imDataList);

        // 删除主键为空的利润中心
        if(nullProfitCenterList.size() > 0) {

            for (ProfitCenter profitCenter : nullProfitCenterList) {

                imDataList.remove(profitCenter);
            }
        }

        // 记录时间
        long startTime = new Date().getTime();
        // 取出存到数据库的利润中心信息
        List<ProfitCenter> profitCenterList = new ArrayList<ProfitCenter>();
        if (prctrList.size() > 450) {

            // 集合分割，参数过大
            int size = prctrList.size() / 450;
            List<List<String>> splitlists = CommonUtils.averageAssign(prctrList, size);

            for (List<String> list : splitlists) {

                List<ProfitCenter> splitList = new ArrayList<ProfitCenter>();
                splitList = commonMapper.findProfitCenterByPrctr(list);
                for (ProfitCenter profitCenter : splitList) {

                    profitCenterList.add(profitCenter);
                }
            }
        }  else {

            // 成功存到数据库的利润中心数据数目
            profitCenterList = commonMapper.findProfitCenterByPrctr(prctrList);
        }
        log.info("结束一次查询利润中心信息时间： " + ((new Date().getTime() - startTime)));

        // 用于插入利润中心数据记录
        List<ProfitCenter> insertBatchProfitCenterList = new ArrayList<ProfitCenter>();
        // 用于修改利润中心数据记录
        List<ProfitCenter> updateBatchProfitCenterList = new ArrayList<ProfitCenter>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ProfitCenter profitCenter : imDataList) {

            profitCenter.setPROFITCENTERID(UUID.randomUUID().toString());
            // 传递过来的利润中心数据已经存入到数据库
            if (profitCenterList.contains(profitCenter)) {

                String updateTime = sdf.format(new Date());
                profitCenter.setUPDATETIME(updateTime);
                updateBatchProfitCenterList.add(profitCenter);
            } else {

                // 传递过来的数据未写入到数据库
                String insertTime = sdf.format(new Date());
                profitCenter.setINSERTTIME(insertTime);
                insertBatchProfitCenterList.add(profitCenter);
            }
        }

        // 成功存入数据库的组织机构数据数目
        Integer insertRows = 0;
        // 成功更新数据库的组织机构数据数目
        Integer updateRows = 0;
        // 总操作数据库的组织机构数据数目
        Integer totalRows = 0;
        int numRows = updateBatchProfitCenterList.size() + insertBatchProfitCenterList.size();

        // 如果有需要新插入到数据库的利润中心数据
        if (insertBatchProfitCenterList.size() > 0) {

            // 请求参数过大
            if(insertBatchProfitCenterList.size() > 40) {

                int size = insertBatchProfitCenterList.size() / 20;
                List<List<ProfitCenter>> splitlists = CommonUtils.averageAssign(insertBatchProfitCenterList, size);

                for (List<ProfitCenter> list : splitlists) {

                    // 成功存到数据库的利润中心数据数目
                    insertRows = commonMapper.addBatchProfitCenter(list);
                }
            } else {

                // 成功存到数据库的利润中心数据数目
                insertRows = commonMapper.addBatchProfitCenter(insertBatchProfitCenterList);
            }
        }

        // 如果有需要更新数据库的组织机构数据
        if (updateBatchProfitCenterList.size() > 0) {

            // 请求参数过大
            if(updateBatchProfitCenterList.size() > 40) {

                int size = updateBatchProfitCenterList.size() / 20;
                List<List<ProfitCenter>> splitlists = CommonUtils.averageAssign(updateBatchProfitCenterList, size);

                for (List<ProfitCenter> list : splitlists) {

                    // 成功更新已插入道数据库的利润中心数目
                    updateRows = commonMapper.updateBatchProfitCenter(list);
                }
            } else {

                updateRows = commonMapper.updateBatchProfitCenter(updateBatchProfitCenterList);
            }
        }
        // 总行数
        totalRows = insertRows + updateRows;

        if ((totalRows != 0 || insertRows !=0 || updateRows != 0) && numRows == imDataList.size()) {

            System.out.println("已全部完成,总操作利润中心数(更新+添加):" + numRows);
        } else {

            System.out.println("未全部完成(有遗漏),总操作利润中心数(更新+添加):" + numRows + "存在数据重复,重复利润中心数目：" + duplicatePrctrList.size());
        }

        // 存储返回数据 - 总返回值
        ExDataBean<ExReturnFatherDataBean<ExReturnProfitCenterDataBean>> exDataBean = new ExDataBean<ExReturnFatherDataBean<ExReturnProfitCenterDataBean>>();
        // 二级嵌套 - 返回值
        ExReturnFatherDataBean<ExReturnProfitCenterDataBean> exReturnFatherDataBean = new ExReturnFatherDataBean<ExReturnProfitCenterDataBean>();
        // 用于存放返回成功的利润中心信息
        List<ExReturnProfitCenterDataBean> exReturnSuccessProfitCenterDataBeanList = new ArrayList<ExReturnProfitCenterDataBean>();
        // 用于存放返回失败的利润中心信息
        List<ExReturnProfitCenterDataBean> exReturnFailedProfitCenterDataBeanList = new ArrayList<ExReturnProfitCenterDataBean>();

        // 遍历从SAP传递过来的利润中心信息
        for (ProfitCenter profitCenter : imDataList) {

            ExReturnProfitCenterDataBean exReturnProfitCenterDataBean = new ExReturnProfitCenterDataBean();
            exReturnProfitCenterDataBean.setPRCTR(profitCenter.getPRCTR());
            exReturnProfitCenterDataBean.setDATBI(profitCenter.getDATBI());
            exReturnProfitCenterDataBean.setKOKRS(profitCenter.getKOKRS());
            // 如果该条利润中心信息已经存到数据库
            if (insertBatchProfitCenterList.contains(profitCenter) || updateBatchProfitCenterList.contains(profitCenter)) {

                // 定义单条返回的组织机构信息
                exReturnProfitCenterDataBean.setMSGTY("S");
                exReturnProfitCenterDataBean.setMSGTX("接收成功");
                exReturnSuccessProfitCenterDataBeanList.add(exReturnProfitCenterDataBean);
            } else {

                // 该条组织机构数据插入失败
                exReturnProfitCenterDataBean.setMSGTY("E");
                exReturnProfitCenterDataBean.setMSGTX("接收失败，请检查该成本中心数据信息");
                exReturnFailedProfitCenterDataBeanList.add(exReturnProfitCenterDataBean);
            }
        }

        // 组装异常信息
        for (Map.Entry<ProfitCenter, String> entry : stringMap.entrySet()) {

            ExReturnProfitCenterDataBean exReturnProfitCenterDataBean = new ExReturnProfitCenterDataBean();
            exReturnProfitCenterDataBean.setPRCTR(entry.getKey().getPRCTR());
            exReturnProfitCenterDataBean.setDATBI(entry.getKey().getDATBI());
            exReturnProfitCenterDataBean.setKOKRS(entry.getKey().getKOKRS());
            // 该条组织机构数据插入失败
            exReturnProfitCenterDataBean.setMSGTY("E");
            exReturnProfitCenterDataBean.setMSGTX(entry.getValue());
            exReturnFailedProfitCenterDataBeanList.add(exReturnProfitCenterDataBean);
        }

        // 组装传递重复数据的异常信息
        for (String  prctr : duplicatePrctrList) {

            ExReturnProfitCenterDataBean exReturnProfitCenterDataBean = new ExReturnProfitCenterDataBean();
            exReturnProfitCenterDataBean.setPRCTR(prctr);
            exReturnProfitCenterDataBean.setMSGTY("E");
            exReturnProfitCenterDataBean.setMSGTX("该条利润中心数据重复");
            exReturnFailedProfitCenterDataBeanList.add(exReturnProfitCenterDataBean);
        }

        if (exReturnFailedProfitCenterDataBeanList.size() > 0) {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("E");
            exReturnFatherDataBean.setMSGTX("接收失败");

            // 将成功的也返回
            for (ExReturnProfitCenterDataBean exReturnProfitCenterDataBean : exReturnSuccessProfitCenterDataBeanList) {

                exReturnFailedProfitCenterDataBeanList.add(exReturnProfitCenterDataBean);
            }
            exReturnFatherDataBean.setDATA(exReturnFailedProfitCenterDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        } else {

            // 组装二级嵌套返回信息
            exReturnFatherDataBean.setMSGTY("S");
            exReturnFatherDataBean.setMSGTX("接收成功");
            exReturnFatherDataBean.setDATA(exReturnSuccessProfitCenterDataBeanList);
            exDataBean.setDATA1(exReturnFatherDataBean);
        }
        log.info("返回利润中心主数据信息:" + JSON.toJSONString(exDataBean));
        log.info("结束一次下发利润中心信息时间： " + ((new Date().getTime() - startTime)));
        return JSON.toJSONString(exDataBean);
    }
}
