package com.fechin.dao.cargo;

import com.fechin.domain.cargo.Finance;
import com.fechin.domain.cargo.FinanceExample;

import java.util.List;

public interface FinanceDao {
    int deleteByPrimaryKey(String financeId);

    int insert(Finance record);

    int insertSelective(Finance record);

    List<Finance> selectByExample(FinanceExample example);

    Finance selectByPrimaryKey(String financeId);

    int updateByPrimaryKeySelective(Finance record);

    int updateByPrimaryKey(Finance record);
}