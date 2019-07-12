package com.fechin.service.cargo;

import com.fechin.domain.cargo.ContractProduct;
import com.fechin.domain.cargo.ContractProductExample;
import com.github.pagehelper.PageInfo;

public interface ContractProductService {
    PageInfo<ContractProduct> findByContractIdOnPages(ContractProductExample example, Integer page, Integer size);

    void save(ContractProduct contractProduct);

    ContractProduct findById(String id);

    void update(ContractProduct contractProduct);

    void delete(String id, String contractId);
}
