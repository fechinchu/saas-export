package com.fechin.service.system.dept;

import com.fechin.domain.system.dept.Dept;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DeptService {

    PageInfo<Dept> findByPage(int page, int size, String companyId);

    Dept findByOne(String id, String companyId);

    List<Dept> findAll(String companyId);

    void update(Dept dept);

    void add(Dept dept);

    void delete(String id, String companyId);


}
