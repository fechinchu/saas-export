package com.fechin.dao.cargo;

import com.fechin.domain.cargo.Contract;
import com.fechin.domain.cargo.ContractExample;
import com.fechin.domain.vo.ContractAndProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractDao {
    int deleteByPrimaryKey(String id);

    int insert(Contract record);

    int insertSelective(Contract record);

    List<Contract> selectByExample(ContractExample example);

    Contract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);

    List<ContractAndProductVo> findByCompanyIdAndTime(@Param("companyId") String companyId,@Param("inputDate") String inputDate);
}