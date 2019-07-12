package com.fechin.service.cargo;

import com.fechin.domain.cargo.Contract;
import com.fechin.domain.cargo.ContractExample;
import com.fechin.domain.vo.ContractAndProductVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ContractService {

    PageInfo<Contract> findByCompanyIdOnPages(Integer page, Integer size, ContractExample contractExample);

    void add(Contract contract);

    void update(Contract contract);

    Contract findByCompanyIdAndId(ContractExample contractExample);

    void deleteById(String id);

    void submitById(String id);

    void cancelById(String id);

    List<ContractAndProductVo> findByCompanyIdAndTime(String companyId, String inputDate);
}
