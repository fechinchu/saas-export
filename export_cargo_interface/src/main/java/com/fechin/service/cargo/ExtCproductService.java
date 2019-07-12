package com.fechin.service.cargo;

import com.fechin.domain.cargo.ExtCproduct;
import com.fechin.domain.cargo.ExtCproductExample;
import com.github.pagehelper.PageInfo;

public interface ExtCproductService {

    PageInfo<ExtCproduct> findOnPage(ExtCproductExample extCproductExample, Integer page, Integer size);

    void save(ExtCproduct extCproduct);

    ExtCproduct findById(String id);

    void update(ExtCproduct extCproduct);

    void delete(String id, String contractId, String contractProductId);

}
