package com.sypm.webapi.service;

import com.sypm.webapi.bean.TcommodityMaster;

import java.util.List;

public interface CommodityService {

    // 批量添加商品
    Integer addBatchCommodity(List<TcommodityMaster> tcommodityMasterList);
}
