package com.fechin.service.packing;

import com.fechin.domain.cargo.Finance;
import com.github.pagehelper.PageInfo;

public interface FinanceService {

    PageInfo<Finance> findOnPage(Integer page, Integer size);

    Finance findById(String id);

    void save(Finance finance);

    void update(Finance finance);

    void delete(String id);

    void submit(String id);

    void cancel(String id);
}
