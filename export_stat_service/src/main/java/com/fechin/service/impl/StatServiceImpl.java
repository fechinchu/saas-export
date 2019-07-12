package com.fechin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fechin.dao.stat.StatDao;
import com.fechin.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private StatDao statDao;

    @Override
    public List<Map<String,Object>> findFactoryWithSumCnumber(String companyId) {
        return statDao.findFactoryWithSumCnumber(companyId);
    }

    @Override
    public List<Map<String, Object>> findSell(String companyId) {
        return statDao.findSell(companyId);
    }

    @Override
    public List<Map<String, Object>> findOnline(String companyId) {
        return statDao.findOnline(companyId);
    }
}
