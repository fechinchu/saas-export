package com.fechin.service.company;

import com.fechin.domain.PageBean;
import com.fechin.domain.company.Company;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();

    void edit(Company company);

    Company findByOne(String id);

    void update(Company company);

    void delete(String id);

    PageBean findPageNumber(Integer pageCount, Integer pageIndex);

}
