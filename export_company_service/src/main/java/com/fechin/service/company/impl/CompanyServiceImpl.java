package com.fechin.service.company.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fechin.dao.company.CompanyDao;
import com.fechin.domain.PageBean;
import com.fechin.domain.company.Company;
import com.fechin.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    public void edit(Company company) {
        String s = UUID.randomUUID().toString().replace("-", "");
        company.setId(s);
        System.out.println(company);
        companyDao.edit(company);
    }

    @Override
    public Company findByOne(String id) {
        return companyDao.findByOne(id);
    }

    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }

    @Override
    public PageBean findPageNumber(Integer pageCount, Integer pageIndex) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Integer listNumbers = companyDao.findAllListNumbers();
        PageBean<Company> pageBean = new PageBean<>(pageIndex, pageCount, listNumbers);
        List<Company> list = companyDao.findByPage(pageBean);
        pageBean.setList(list);
        return pageBean;
    }
}
