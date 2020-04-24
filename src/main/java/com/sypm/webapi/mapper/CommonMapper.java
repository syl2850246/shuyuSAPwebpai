package com.sypm.webapi.mapper;

import com.sypm.webapi.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper {

    // 关于商品的相关操作
    // 批量添加商品
    Integer addBatchCommodity(@Param("tcommodityMasterList")List<TcommodityMaster> tcommodityMasterList);

    // 根据商品编码查询商品
    List<TcommodityMaster> findTcommmodityByMatnr(@Param("matnrList")List<String> matnrList);

    // 批量修改商品
    Integer updateBatchCommodity(@Param("tcommodityMasterList")List<TcommodityMaster> tcommodityMasterList);

    // 关于门店的相关操作
    // 批量添加医药门店
    Integer addBatchStores(@Param("drugStoresList")List<DrugStores> drugStoresList);

    // 根据门店编码查询门店
    List<DrugStores> findDrugStoresByWerks(@Param("werksList")List<String> werksList);

    // 批量修改门店
    Integer updateBatchStores(@Param("drugStoresList")List<DrugStores> drugStoresList);

    // 关于客户的相关操作
    // 批量添加客户
    Integer addBatchClientele(@Param("clienteleList")List<Clientele> clienteleList);

    // 根据客户编码查询客户
    List<Clientele> findClienteleByKunnr(@Param("kunnrList")List<String> kunnrList);

    // 批量修改客户
    Integer updateBatchClientele(@Param("clienteleList")List<Clientele> clienteleList);

    // 关于供应商的相关操作
    // 批量添加供应商
    Integer addBatchSupplier(@Param("supplierList")List<Supplier> supplierList);

    // 根据供应商编码查询供应商
    List<Supplier> findSupplierByLifnr(@Param("lifnrList")List<String> lifnrList);

    // 批量修改供应商
    Integer updateBatchSupplier(@Param("supplierList")List<Supplier> supplierList);

    // 关于成本中心的相关操作
    // 批量添加成本中心
    Integer addBatchCostCenter(@Param("costCenterList")List<CostCenter> costCenterList);

    // 根据成本中心编码查询成本中心
    List<CostCenter> findCostCenterByKostl(@Param("kostlList")List<String> kostlList);

    // 批量修改成本中心
    Integer updateBatchCostCenter(@Param("costCenterList")List<CostCenter> costCenterList);

    // 关于组织架构的相关操作
    // 批量添加组织架构
    Integer addBatchOrganization(@Param("organizationList")List<Organization> organizationList);

    // 根据组织架构编码查询组织架构
    List<Organization> findOrganizationByKunnr(@Param("kunnnrList")List<String> kunnnrList);

    // 批量修改组织架构
    Integer updateBatchOrganization(@Param("organizationList")List<Organization> organizationList);

    // 关于利润中心的相关操作
    // 批量添加利润中心
    Integer addBatchProfitCenter(@Param("profitCenterList")List<ProfitCenter> profitCenterList);

    // 根据利润中心编码查询利润中心
    List<ProfitCenter> findProfitCenterByPrctr(@Param("prctrList")List<String> prctrList);

    // 批量修改利润中心
    Integer updateBatchProfitCenter(@Param("profitCenterList")List<ProfitCenter> profitCenterList);
}
