package com.fechin.dao.cargo;

import com.fechin.domain.cargo.ExtCproduct;
import com.fechin.domain.cargo.ExtCproductExample;

import java.util.List;

public interface ExtCproductDao {
    int deleteByPrimaryKey(String id);

    int insert(ExtCproduct record);

    int insertSelective(ExtCproduct record);

    List<ExtCproduct> selectByExampleWithBLOBs(ExtCproductExample example);

    List<ExtCproduct> selectByExample(ExtCproductExample example);

    ExtCproduct selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExtCproduct record);

    int updateByPrimaryKeyWithBLOBs(ExtCproduct record);

    int updateByPrimaryKey(ExtCproduct record);

    List<ExtCproduct> findByContractProductId(String contractProductId);


}