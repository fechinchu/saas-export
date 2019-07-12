package com.fechin.service.system.dept.impl;

import com.fechin.dao.system.dept.DeptDao;
import com.fechin.domain.system.dept.Dept;
import com.fechin.service.system.dept.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    @Override
    public PageInfo<Dept> findByPage(int page, int size, String companyId) {
        PageHelper.startPage(page,size);
        List<Dept> list = deptDao.findAll(companyId);
        PageInfo<Dept> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public Dept findByOne(String id, String companyId) {
        Dept dept = deptDao.findByIdBeforeUpdate(id,companyId);
        return dept;
    }

    @Override
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }

    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    @Override
    public void add(Dept dept) {
        dept.setDeptId(UUID.randomUUID().toString().replace("-",""));
        deptDao.add(dept);
    }

    @Override
    public void delete(String id, String companyId) {
        deptDao.delete(id,companyId);
    }
}
