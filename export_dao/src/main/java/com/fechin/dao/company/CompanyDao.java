package com.fechin.dao.company;

import com.fechin.domain.PageBean;
import com.fechin.domain.company.Company;

import java.util.List;

public interface CompanyDao {

    List<Company> findAll();

    void edit(Company company);

    Company findByOne(String id);

    void update(Company company);

    void delete(String id);

    Integer findAllListNumbers();

    List<Company> findByPage(PageBean pageBean);
}
