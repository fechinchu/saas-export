package com.fechin.dao.export;

import com.fechin.domain.export.ExtEproduct;
import com.fechin.domain.export.ExtEproductExample;

import java.util.List;

public interface ExtEproductDao {
    int deleteByPrimaryKey(String id);

    int insert(ExtEproduct record);

    int insertSelective(ExtEproduct record);

    List<ExtEproduct> selectByExampleWithBLOBs(ExtEproductExample example);

    List<ExtEproduct> selectByExample(ExtEproductExample example);

    ExtEproduct selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExtEproduct record);

    int updateByPrimaryKeyWithBLOBs(ExtEproduct record);

    int updateByPrimaryKey(ExtEproduct record);
}