package com.fechin.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fechin.dao.cargo.FactoryDao;
import com.fechin.domain.cargo.Factory;
import com.fechin.domain.cargo.FactoryExample;
import com.fechin.service.cargo.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class FactoryServiceImpl implements FactoryService {
    @Autowired
    private FactoryDao factoryDao;

    @Override
    public List<Factory> findByCtype(FactoryExample factoryExample) {
        return factoryDao.selectByExample(factoryExample);
    }
}
