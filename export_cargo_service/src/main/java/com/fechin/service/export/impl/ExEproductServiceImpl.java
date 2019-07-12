package com.fechin.service.export.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fechin.dao.export.ExtEproductDao;
import com.fechin.service.export.ExEproductService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ExEproductServiceImpl implements ExEproductService {
    @Autowired
    private ExtEproductDao extEproductDao;
}
